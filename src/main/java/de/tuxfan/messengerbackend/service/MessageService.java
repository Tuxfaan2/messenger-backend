package de.tuxfan.messengerbackend.service;

import de.tuxfan.messengerbackend.model.AddMessageRequest;
import de.tuxfan.messengerbackend.model.MessageDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MessageService {
    MessageDto addNewMessage(AddMessageRequest addMessageRequest);

    List<MessageDto> getAllMessages();
}
