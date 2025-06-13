package de.tuxfan.messengerbackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuxfan.messengerbackend.model.Chat;
import de.tuxfan.messengerbackend.model.ChatDto;
import de.tuxfan.messengerbackend.model.CreateChatRequest;
import de.tuxfan.messengerbackend.repository.ChatRepository;
import de.tuxfan.messengerbackend.service.ChatService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;

    public ChatServiceImpl(ChatRepository chatRepository,
                           ObjectMapper objectMapper) {
        this.chatRepository = chatRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ChatDto createNewChat(CreateChatRequest createChatRequest) {
        Chat chatRequest = new Chat(createChatRequest.getUserIds());
        Chat chat = chatRepository.save(chatRequest);
        return chat.toDto();
    }

    @Override
    public List<ChatDto> getAllChatsForCurrentUser() {
        List<Chat> chats = chatRepository.findAll();

        return chats.stream().filter(chat -> chat.getUserIds().contains(getUserMail())).map(Chat::toDto).toList();
    }

    private String getUserMail() {
        SecurityContext securityContext =
                SecurityContextHolder.getContext();
        HashMap credentials =
                objectMapper.convertValue(securityContext.getAuthentication().getCredentials(), HashMap.class);
        HashMap claims = objectMapper.convertValue(credentials.get(
                "claims"), HashMap.class);
        return (String) claims.get("email");
    }
}
