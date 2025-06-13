package de.tuxfan.messengerbackend.service;

import de.tuxfan.messengerbackend.model.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getCurrentUser();

    List<UserResponse> getAllUsers();
}
