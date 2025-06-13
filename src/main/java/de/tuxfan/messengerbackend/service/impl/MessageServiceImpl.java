package de.tuxfan.messengerbackend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuxfan.messengerbackend.model.AddMessageRequest;
import de.tuxfan.messengerbackend.model.Message;
import de.tuxfan.messengerbackend.model.MessageDto;
import de.tuxfan.messengerbackend.repository.MessageRepository;
import de.tuxfan.messengerbackend.service.MessageService;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final ObjectMapper objectMapper;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(ObjectMapper objectMapper,
                              MessageRepository messageRepository) {
        this.objectMapper = objectMapper;
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageDto addNewMessage(AddMessageRequest addMessageRequest) {
        Message message = new Message();
        message.setChatId(addMessageRequest.getChatId().orElse(null));
        message.setContent(addMessageRequest.getContent());
        message.setSenderMail(getUserClaims().get("email").toString());
        message.setReceiverMail(addMessageRequest.getReceiverId());
        message.setTimestamp(OffsetDateTime.now());
        Message save = messageRepository.save(message);
        return save.toDto();
    }

    @Override
    public List<MessageDto> getAllMessages() {
        List<Message> messages =
                Streamable.of(messageRepository.findAll()).toList();
        return messages.stream().map(Message::toDto).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getMessagesByChatId(UUID chatId) {
        List<Message> messages =
                messageRepository.findAllByChatId(chatId);
        return messages.stream().map(Message::toDto).collect(Collectors.toList());
    }

    private HashMap getUserClaims() {
        SecurityContext securityContext =
                SecurityContextHolder.getContext();
        HashMap credentials =
                objectMapper.convertValue(securityContext.getAuthentication().getCredentials(), HashMap.class);
        return objectMapper.convertValue(credentials.get("claims"),
                                         new TypeReference<>() {
        });
    }
}
