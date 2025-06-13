package de.tuxfan.messengerbackend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuxfan.messengerbackend.configuration.MessengerConfigurationProperties;
import de.tuxfan.messengerbackend.model.User;
import de.tuxfan.messengerbackend.model.UserResponse;
import de.tuxfan.messengerbackend.service.UserService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ObjectMapper objectMapper;
    private final MessengerConfigurationProperties messengerConfigurationProperties;

    public UserServiceImpl(ObjectMapper objectMapper,
                           MessengerConfigurationProperties messengerConfigurationProperties) {
        this.objectMapper = objectMapper;
        this.messengerConfigurationProperties =
                messengerConfigurationProperties;
    }

    @Override
    public UserResponse getCurrentUser() {
        HashMap userClaims = getUserClaims();
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(Optional.of(userClaims.get("sid").toString()));
        userResponse.setEmail(Optional.of(userClaims.get("email").toString()));
        userResponse.setFirstname(Optional.of(userClaims.get(
                "given_name").toString()));
        userResponse.setLastname(Optional.of(userClaims.get(
                "family_name").toString()));
        userResponse.setUsername(Optional.of(userClaims.get(
                "preferred_username").toString()));
        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return getUserResponse().stream().map(this::toUserResponse).collect(Collectors.toList());
    }

    private UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setLastname(Optional.ofNullable(user.getLastName()));
        userResponse.setFirstname(Optional.ofNullable(user.getFirstName()));
        userResponse.setEmail(Optional.ofNullable(user.getEmail()));
        userResponse.setUsername(Optional.ofNullable(user.getUsername()));
        userResponse.setUserId(Optional.ofNullable(String.valueOf(user.getUserId())));
        return userResponse;
    }

    private List<User> getUserResponse() {
        String url =
                messengerConfigurationProperties.getKeycloakConfiguration().getAuthServerUrl() + "/admin/realms/" + messengerConfigurationProperties.getKeycloakConfiguration().getRealm() + "/users";
        try (CloseableHttpClient httpClient =
                     HttpClients.createDefault()) {
            HttpGet post = new HttpGet(url);
            post.setHeader("Content-Type",
                           "application/x-www-form" + "-urlencoded");
            post.setHeader("Authorization",
                           "Bearer " + getAccessToken());

            return getResponse(httpClient, post);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<User> getResponse(CloseableHttpClient httpClient,
                                   HttpGet get) throws IOException {
        HttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == 400) {
            throw new BadCredentialsException("Your token expired");
        }
        if (response.getStatusLine().getStatusCode() == 401) {
            throw new BadCredentialsException("Wrong credentials");
        }
        String entity = EntityUtils.toString(response.getEntity());
        return objectMapper.readValue(entity,
                                      new TypeReference<List<User>>() {
        });
    }


    private HashMap getUserClaims() {
        SecurityContext securityContext =
                SecurityContextHolder.getContext();
        HashMap credentials =
                objectMapper.convertValue(securityContext.getAuthentication().getCredentials(), HashMap.class);
        return objectMapper.convertValue(credentials.get("claims"),
                                         new TypeReference<>() {
        });
    }

    private String getAccessToken() {
        SecurityContext securityContext =
                SecurityContextHolder.getContext();
        HashMap authentication =
                objectMapper.convertValue(securityContext.getAuthentication(), HashMap.class);
        HashMap token =
                objectMapper.convertValue(authentication.get("token"
                ), HashMap.class);
        return token.get("tokenValue").toString();
    }
}
