package br.senai.sp.poc.oauthlogin.oauthlogin;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @GetMapping("/public")
    Map<String, String> publicEndpoint() {
        return Map.of("message", "welcome to my app, pls login :)");
    }

    @GetMapping("/private")
    Map<String, String> privateEndpoint(@AuthenticationPrincipal Jwt authPrincipal) {
        var auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Map.of("message", "Welcome authenticated user! :]");
    }
}
