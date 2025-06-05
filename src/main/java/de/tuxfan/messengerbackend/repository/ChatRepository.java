package de.tuxfan.messengerbackend.repository;

import de.tuxfan.messengerbackend.model.Chat;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    @NotNull List<Chat> findAll();
}
