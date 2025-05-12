package de.tuxfan.messengerbackend.service.impl;

import de.tuxfan.messengerbackend.model.AddMessageRequest;
import de.tuxfan.messengerbackend.model.MessageDto;
import de.tuxfan.messengerbackend.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public MessageDto addNewMessage(AddMessageRequest addMessageRequest) {
        return null;
    }

    @Override
    public List<MessageDto> getAllMessages() {
        return List.of();
    }
}
