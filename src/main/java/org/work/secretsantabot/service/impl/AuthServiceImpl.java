package org.work.secretsantabot.service.impl;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.work.secretsantabot.dto.TelegramUserModel;
import org.work.secretsantabot.dto.TelegramUserResponse;
import org.work.secretsantabot.service.AuthService;
import tools.jackson.databind.ObjectMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;

    @Autowired
    public AuthServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public Map<String, TelegramUserResponse> auth(String initData) {
        String decodeBody = URLDecoder.decode(initData, StandardCharsets.UTF_8);

        var bodyParam = new HashMap<String, String>();
        Arrays.stream(decodeBody.split("&")).forEach(param ->
                bodyParam.put(param.split("=")[0], param.split("=")[1])
        );

        var dataString = new StringBuilder();
        bodyParam.keySet().stream().sorted().filter(key -> !key.equals("hash")).forEach(key ->
                dataString.append(key).append("=").append(bodyParam.get(key)).append("\n")
        );

        String dataCheckString = dataString.substring(0, dataString.length() - 1);
        byte[] secretKey = new HmacUtils("HmacSHA256", "WebAppData").hmac("7788698179:AAEPrRzXhUt5onDNFauZcLTepOZzpBdvz88");

        if (bodyParam.get("hash").equals(new HmacUtils("HmacSHA256", secretKey).hmacHex(dataCheckString))) {

            var tgUserModel = new ObjectMapper().readValue(bodyParam.get("user"), TelegramUserModel.class);
            var user = userService.findByUsername(tgUserModel.getUsername());

            String authToken = UUID.randomUUID().toString();
            var tokenExpireDate = System.currentTimeMillis() + 86400;

            user.setAuthToken(authToken);
            user.setAuthTokenExpireDate(tokenExpireDate);
            userService.save(user);

            var userResponse = new TelegramUserResponse(
                    tgUserModel.getFirst_name(),
                    tgUserModel.getLast_name(),
                    tgUserModel.getUsername()
            );

            return Map.of(authToken, userResponse);
        } else return null;
    }

    @Override
    public boolean checkAuth(String token) {
        var user = userService.findByAuthToken(token);

        if (user == null) return false;
        return user.getAuthTokenExpireDate() >= System.currentTimeMillis();
    }

    @Override
    public TelegramUserResponse getUser(String token) {
        var user = userService.findByAuthToken(token);

        if (user == null) return null;

        return new TelegramUserResponse(
                user.getTelegramFirstname(),
                user.getTelegramLastname(),
                user.getTelegramUsername()
        );
    }

    public String getCookieValue(Cookie[] cookies, String name) {
        String result = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                result = cookie.getValue();
            }
        }

        return result;
    }

}
