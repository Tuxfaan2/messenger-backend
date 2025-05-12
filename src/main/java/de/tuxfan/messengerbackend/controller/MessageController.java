package de.tuxfan.messengerbackend.controller;

import de.tuxfan.messengerbackend.api.MessageApi;
import de.tuxfan.messengerbackend.model.AddMessageRequest;
import de.tuxfan.messengerbackend.model.MessageDto;
import de.tuxfan.messengerbackend.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MessageController implements MessageApi {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public ResponseEntity<MessageDto> addNewMessage(AddMessageRequest addMessageRequest) {
        return ResponseEntity.ok(messageService.addNewMessage(addMessageRequest));
    }

    @Override
    public ResponseEntity<List<MessageDto>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }
}
