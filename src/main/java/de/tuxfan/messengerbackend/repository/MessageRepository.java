package de.tuxfan.messengerbackend.repository;

import de.tuxfan.messengerbackend.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
