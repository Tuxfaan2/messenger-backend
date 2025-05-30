package de.tuxfan.messengerbackend;

import de.tuxfan.messengerbackend.configuration.MessengerConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MessengerConfigurationProperties.class)
public class MessengerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerBackendApplication.class, args);
    }

}
