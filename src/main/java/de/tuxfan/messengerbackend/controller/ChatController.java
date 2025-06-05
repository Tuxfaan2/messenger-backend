package de.tuxfan.messengerbackend.controller;

import de.tuxfan.messengerbackend.api.ChatApi;
import de.tuxfan.messengerbackend.model.ChatDto;
import de.tuxfan.messengerbackend.model.CreateChatRequest;
import de.tuxfan.messengerbackend.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController implements ChatApi {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public ResponseEntity<ChatDto> createNewChat(CreateChatRequest createChatRequest) {
        return ResponseEntity.ok(chatService.createNewChat(createChatRequest));
    }

    @Override
    public ResponseEntity<List<ChatDto>> getAllChatsForCurrentUser() {
        return ResponseEntity.ok(chatService.getAllChatsForCurrentUser());
    }
}
