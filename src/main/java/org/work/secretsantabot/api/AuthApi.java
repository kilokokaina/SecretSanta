package org.work.secretsantabot.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.work.secretsantabot.dto.TelegramUserModel;
import org.work.secretsantabot.dto.TelegramUserResponse;
import tools.jackson.databind.ObjectMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class AuthApi {

    private final static Logger log = LoggerFactory.getLogger(AuthApi.class);

    @PostMapping("auth")
    public @ResponseBody ResponseEntity<TelegramUserResponse> auth(HttpServletResponse response, @RequestBody String body) {
        String decodeBody = URLDecoder.decode(body, StandardCharsets.UTF_8);
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

        var userResponse = new TelegramUserResponse(
                tgUserModel.getFirst_name(),
                tgUserModel.getLast_name(),
                tgUserModel.getUsername()
        );

        var authCookie = new Cookie("session_token", UUID.randomUUID().toString());
        authCookie.setPath("/");
        authCookie.setMaxAge(86400);

        response.addCookie(authCookie);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
