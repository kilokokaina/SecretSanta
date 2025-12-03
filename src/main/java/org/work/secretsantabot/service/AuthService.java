package org.work.secretsantabot.service;

import org.work.secretsantabot.dto.TelegramUserResponse;

import java.util.Map;

public interface AuthService {

    Map<String, TelegramUserResponse> auth(String initData);
    boolean checkToken(String token);

}
