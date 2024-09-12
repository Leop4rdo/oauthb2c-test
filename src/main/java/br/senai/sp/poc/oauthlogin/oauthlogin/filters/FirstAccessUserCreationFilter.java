package br.senai.sp.poc.oauthlogin.oauthlogin.filters;

import br.senai.sp.poc.oauthlogin.oauthlogin.entities.User;
import br.senai.sp.poc.oauthlogin.oauthlogin.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FirstAccessUserCreationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getJwt();

        if (Objects.nonNull(token) && userRepository.findByEmail(token.getClaimAsStringList("emails").get(0)).isEmpty())
            createUser(token);


        filterChain.doFilter(request, response);
    }

    private Jwt getJwt() {
        var token = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return (token instanceof Jwt) ?
                (Jwt) token
                : null;
    }


    private void createUser(Jwt token) {
        var user = new User(
                UUID.randomUUID(),
                token.getClaimAsString("name"),
                token.getClaimAsStringList("emails").get(0),
                UUID.fromString(token.getClaimAsString("oid")),
                LocalDateTime.now()
        );

        userRepository.save(user);
    }
}