package de.tuxfan.messengerbackend.repository;

import de.tuxfan.messengerbackend.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message,
        Long> {
    List<Message> findAllByChatId(UUID chatId);
}
