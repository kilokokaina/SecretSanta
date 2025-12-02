package org.work.secretsantabot;

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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
        log.info("Auth request received: {}", decodeBody);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
