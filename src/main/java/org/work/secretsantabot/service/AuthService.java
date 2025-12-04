package org.work.secretsantabot.service;

import jakarta.servlet.http.Cookie;
import org.work.secretsantabot.dto.TelegramUserResponse;

import java.util.Map;

public interface AuthService {

    Map<String, TelegramUserResponse> auth(String initData);
    boolean checkAuth(String token);
    TelegramUserResponse getUser(String token);
    String getCookieValue(Cookie[] cookies, String name);

}
