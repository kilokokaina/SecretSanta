package org.work.secretsantabot;

import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tools.jackson.databind.ObjectMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

class TelegramUserModel {

    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String language_code;
    private boolean is_premium;
    private boolean allows_write_to_pm;
    private String photo_url;

    public TelegramUserModel() {}

    public String getFirst_name() {
        return this.first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public String getUsername() {
        return this.username;
    }
}

@Controller
@SpringBootApplication
public class SecretSantaBotApplication {

    private final static Logger log = LoggerFactory.getLogger(SecretSantaBotApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SecretSantaBotApplication.class, args);
        log.info("Telegram-bot has started");
    }

    @GetMapping
    public String hello() {
        return "index";
    }

    @PostMapping("auth")
    public @ResponseBody ResponseEntity<HttpStatus> auth(@RequestBody String body) {
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
        log.info("user = {}", tgUserModel);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
