package de.tuxfan.messengerbackend.service;

import de.tuxfan.messengerbackend.model.TokenResponse;

public interface LoginService {
    TokenResponse loginWithCredentials(String password, String username);

    TokenResponse loginWithRefreshToken(String refreshToken);
}
