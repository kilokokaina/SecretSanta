package org.work.secretsantabot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
public class SecretSantaBotApplication {

    private final static Logger log = LoggerFactory.getLogger(SecretSantaBotApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SecretSantaBotApplication.class, args);
        log.info("Telegram-bot has started");
    }

}
