package de.tuxfan.messengerbackend.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Message {
    private String content;
    private String senderMail;
    private String receiverMail;
    private UUID chatId;
    private OffsetDateTime timestamp;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getReceiverMail() {
        return receiverMail;
    }

    public void setReceiverMail(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
            "message_seq")
    @SequenceGenerator(name = "message_seq", sequenceName =
            "MESSAGE_SEQ", initialValue = 1, allocationSize = 1)
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
        messageDto.setSenderId(senderMail);
        messageDto.setReceiverId(receiverMail);
        messageDto.setTimestamp(timestamp);
        messageDto.setChatId(chatId);
        return messageDto;
    }
}
