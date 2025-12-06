package org.work.secretsantabot.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("check_auth")
    public ResponseEntity<Boolean> checkAuth(HttpServletRequest request) {
        if (request.getCookies() != null) {
            String authCookie = authService.getCookieValue(request.getCookies(), "session_token");
            return ResponseEntity.ok(authService.checkAuth(authCookie));
        }

        return ResponseEntity.ok(false);
    }

    @GetMapping("get_user")
    public ResponseEntity<TelegramUserResponse> getUser(HttpServletRequest request) {
        String authCookie = authService.getCookieValue(request.getCookies(), "session_token");

        var response = authService.getUser(authCookie);
        if (response == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(response);
    }

    @PostMapping("auth")
    public @ResponseBody ResponseEntity<TelegramUserResponse> auth(HttpServletResponse response, @RequestBody String body) {
        var authData = authService.auth(body);

        if (authData == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var authToken = authData.keySet().stream().toList().get(0);
        var authCookie = new Cookie("session_token", authToken);
        authCookie.setPath("/");
        authCookie.setMaxAge(86400);
        response.addCookie(authCookie);

        return new ResponseEntity<>(authData.get(authToken), HttpStatus.OK);
    }

}
