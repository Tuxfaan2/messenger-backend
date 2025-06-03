package de.tuxfan.messengerbackend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuxfan.messengerbackend.model.UserResponse;
import de.tuxfan.messengerbackend.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ObjectMapper objectMapper;

    public UserServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public UserResponse getCurrentUser() {
        HashMap userClaims = getUserClaims();
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(Optional.of(userClaims.get("sid").toString()));
        userResponse.setEmail(Optional.of(userClaims.get("email").toString()));
        userResponse.setFirstname(Optional.of(userClaims.get("given_name").toString()));
        userResponse.setLastname(Optional.of(userClaims.get("family_name").toString()));
        userResponse.setUsername(Optional.of(userClaims.get("preferred_username").toString()));
        return userResponse;
    }

    private HashMap getUserClaims() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        HashMap credentials =
                objectMapper.convertValue(securityContext.getAuthentication().getCredentials(),
                        HashMap.class);
        return objectMapper.convertValue(credentials.get("claims"), new TypeReference<>() {
        });
    }
}
