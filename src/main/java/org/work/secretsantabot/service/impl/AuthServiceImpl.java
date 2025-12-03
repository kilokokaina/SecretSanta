package org.work.secretsantabot.service.impl;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        log.info("Is hash equals: {}", bodyParam.get("hash").equals(new HmacUtils("HmacSHA256", secretKey).hmacHex(dataCheckString)));

        var tgUserModel = new ObjectMapper().readValue(bodyParam.get("user"), TelegramUserModel.class);
        log.info("User firstname = {}", tgUserModel.getFirst_name());
        log.info("User lastname = {}", tgUserModel.getLast_name());
        log.info("User username = {}", tgUserModel.getUsername());

        var user = userService.findByUsername(tgUserModel.getUsername());

        if (!user.getTelegramFirstName().equals(tgUserModel.getFirst_name())) {
            user.setTelegramFirstName(tgUserModel.getFirst_name());
        }
        if (!user.getTelegramSecondName().equals(tgUserModel.getLast_name())) {
            user.setTelegramSecondName(tgUserModel.getLast_name());
        }

        String authToken = UUID.randomUUID().toString();
        user.setAuthToken(authToken);

        var userResponse = new TelegramUserResponse(
                tgUserModel.getFirst_name(),
                tgUserModel.getLast_name(),
                tgUserModel.getUsername()
        );

        userService.save(user);

        return Map.of(authToken, userResponse);
    }

    @Override
    public boolean checkToken(String token) {
        return false;
    }

}
