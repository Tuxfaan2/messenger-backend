package de.tuxfan.messengerbackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuxfan.messengerbackend.configuration.MessengerConfigurationProperties;
import de.tuxfan.messengerbackend.model.TokenResponse;
import de.tuxfan.messengerbackend.service.LoginService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginServiceImpl implements LoginService {

    private final ObjectMapper objectMapper;
    private final MessengerConfigurationProperties messengerConfigurationProperties;

    public LoginServiceImpl(ObjectMapper objectMapper,
                            MessengerConfigurationProperties messengerConfigurationProperties) {
        this.objectMapper = objectMapper;
        this.messengerConfigurationProperties =
                messengerConfigurationProperties;
    }

    @Override
    public TokenResponse loginWithCredentials(String password,
                                              String username) {

        return login(username, password);
    }

    @Override
    public TokenResponse loginWithRefreshToken(String refreshToken) {

        return login(refreshToken);
    }


    private TokenResponse login(String refreshToken) {
        String url =
                messengerConfigurationProperties.getKeycloakConfiguration().getAuthServerUrl() + "/realms/" + messengerConfigurationProperties.getKeycloakConfiguration().getRealm() + "/protocol/openid-connect/token";
        String clientId =
                messengerConfigurationProperties.getKeycloakConfiguration().getClientId();
        String clientSecret =
                messengerConfigurationProperties.getKeycloakConfiguration().getClientSecret();
        try (CloseableHttpClient httpClient =
                     HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/x-www-form" +
                    "-urlencoded");
            String body = "client_id=" + clientId + "&grant_type" +
                    "=refresh_token&refresh_token=" + refreshToken + "&client_secret=" + clientSecret;
            return getResponse(httpClient, post, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TokenResponse login(String username, String password) {
        String url =
                messengerConfigurationProperties.getKeycloakConfiguration().getAuthServerUrl() + "/realms/" + messengerConfigurationProperties.getKeycloakConfiguration().getRealm() + "/protocol/openid-connect/token";
        String clientId =
                messengerConfigurationProperties.getKeycloakConfiguration().getClientId();
        String clientSecret =
                messengerConfigurationProperties.getKeycloakConfiguration().getClientSecret();
        try (CloseableHttpClient httpClient =
                     HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/x-www-form" +
                    "-urlencoded");
            String body = "client_id=" + clientId + "&grant_type" +
                    "=password&username=" + username + "&password=" + password + "&client_secret=" + clientSecret;
            return getResponse(httpClient, post, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TokenResponse getResponse(CloseableHttpClient httpClient, HttpPost post, String body) throws IOException {
        post.setEntity(new StringEntity(body));
        HttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == 400) {
            throw new BadCredentialsException("Your token expired");
        }
        if (response.getStatusLine().getStatusCode() == 401) {
            throw new BadCredentialsException("Wrong credentials");
        }
        String entity = EntityUtils.toString(response.getEntity());
        return objectMapper.readValue(entity, TokenResponse.class);
    }
}
