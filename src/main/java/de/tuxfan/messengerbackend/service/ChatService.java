package de.tuxfan.messengerbackend.service;

import de.tuxfan.messengerbackend.model.ChatDto;
import de.tuxfan.messengerbackend.model.CreateChatRequest;

import java.util.List;

public interface ChatService {
    ChatDto createNewChat(CreateChatRequest createChatRequest);

    List<ChatDto> getAllChatsForCurrentUser();
}
