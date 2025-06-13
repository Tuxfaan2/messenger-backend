package de.tuxfan.messengerbackend.service;

import de.tuxfan.messengerbackend.model.AddMessageRequest;
import de.tuxfan.messengerbackend.model.MessageDto;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageDto addNewMessage(AddMessageRequest addMessageRequest);

    List<MessageDto> getAllMessages();

    List<MessageDto> getMessagesByChatId(UUID chatId);
}
