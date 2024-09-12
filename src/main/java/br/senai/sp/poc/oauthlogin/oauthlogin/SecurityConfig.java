package br.senai.sp.poc.oauthlogin.oauthlogin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                    var config = new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            var config = new CorsConfiguration();

                            config.setAllowedOrigins(Collections.singletonList("*"));
                            config.setExposedHeaders(Collections.singletonList("*"));
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setAllowedMethods(Collections.singletonList("*"));

                            return config;
                        }
                    };
                    cors.configurationSource(config);
                })
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorization) ->
                    authorization
                            .requestMatchers("/api/v1/public").permitAll()
                            .anyRequest().authenticated()
            ).oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));

        return http.build();
    }
}
