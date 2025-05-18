package de.tuxfan.messengerbackend.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Message {
    private String content;
    private UUID senderId;
    private UUID receiverId;
    private OffsetDateTime timestamp;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @SequenceGenerator(name = "message_seq", sequenceName = "MESSAGE_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public MessageDto toDto() {
        MessageDto messageDto = new MessageDto();
        messageDto.setContent(content);
        messageDto.setSenderId(senderId);
        messageDto.setReceiverId(receiverId);
        messageDto.setTimestamp(timestamp);
        return messageDto;
    }
}
