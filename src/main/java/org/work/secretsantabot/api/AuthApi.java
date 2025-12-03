package org.work.secretsantabot.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.work.secretsantabot.dto.TelegramUserResponse;
import org.work.secretsantabot.service.AuthService;

@Slf4j
@RestController
public class AuthApi {

    private final AuthService authService;

    @Autowired
    public AuthApi(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("auth")
    public @ResponseBody ResponseEntity<TelegramUserResponse> auth(HttpServletResponse response, @RequestBody String body) {
        var authData = authService.auth(body);
        var authToken = authData.keySet().stream().toList().get(0);

        var authCookie = new Cookie("session_token", authToken);
        authCookie.setPath("/");
        authCookie.setMaxAge(86400);

        response.addCookie(authCookie);

        return new ResponseEntity<>(authData.get(authToken), HttpStatus.OK);
    }

}
