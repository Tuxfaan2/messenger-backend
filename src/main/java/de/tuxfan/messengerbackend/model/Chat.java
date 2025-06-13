package de.tuxfan.messengerbackend.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Chat {
    public Chat(List<String> userIdsToInitializeWith) {
        chatId = UUID.randomUUID();
        userIds = userIdsToInitializeWith;
    }

    private UUID chatId;
    private List<String> userIds;

    public Chat() {

    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
            "chat_seq")
    @SequenceGenerator(name = "chat_seq", sequenceName = "CHAT_SEQ"
            , allocationSize = 1)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public ChatDto toDto() {
        return new ChatDto(chatId, userIds);
    }
}
