package de.tuxfan.messengerbackend.controller;

import de.tuxfan.messengerbackend.api.AuthorizationApi;
import de.tuxfan.messengerbackend.model.TokenResponse;
import de.tuxfan.messengerbackend.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController implements AuthorizationApi {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public ResponseEntity<TokenResponse> loginWithCredentials(String password, String username) {
        return ResponseEntity.ok(loginService.loginWithCredentials(password, username));
    }

    @Override
    public ResponseEntity<TokenResponse> loginWithRefreshToken(String refreshToken) {
        return ResponseEntity.ok(loginService.loginWithRefreshToken(refreshToken));
    }
}
