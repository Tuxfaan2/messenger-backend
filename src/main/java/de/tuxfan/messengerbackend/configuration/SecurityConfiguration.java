package de.tuxfan.messengerbackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, JwtAuthenticationConverter authenticationConverter) throws Exception {
        http.oauth2ResourceServer(resourceServer -> {
            resourceServer.jwt(jwtDecoder -> {
                jwtDecoder.jwtAuthenticationConverter(authenticationConverter);
            });
        });

        http.sessionManagement(sessions -> {
            sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }).csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(requests -> {
            requests.anyRequest().authenticated();
        });
        return http.build();
    }


    @Bean
    JwtAuthenticationConverter authenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
