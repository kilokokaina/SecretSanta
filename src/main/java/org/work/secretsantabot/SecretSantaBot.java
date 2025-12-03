package org.work.secretsantabot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.work.secretsantabot.model.User;
import org.work.secretsantabot.repository.UserRepository;
import org.work.secretsantabot.service.impl.UserServiceImpl;

@Slf4j
@Component
public class SecretSantaBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Autowired
    public SecretSantaBot(UserServiceImpl userService, UserRepository userRepository) {
        telegramClient = new OkHttpTelegramClient(getBotToken());
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public String getBotToken() {
        return "7788698179:AAEPrRzXhUt5onDNFauZcLTepOZzpBdvz88";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String userName = update.getMessage().getChat().getUserName();
            String userFirstname = update.getMessage().getUserShared().getFirstName();
            String userLastname = update.getMessage().getUserShared().getLastName();
            long chatId = update.getMessage().getChatId();

            log.info("Username: {}, chat Id: {}, text: {}", userName, chatId, messageText);

            if (userService.findByUsername(userName) == null) {
                var user = new User();

                user.setTelegramFirstname(userFirstname);
                user.setTelegramLastname(userLastname);
                user.setTelegramUsername(userName);
                user.setTelegramChatId(chatId);
                userRepository.save(user);

                log.info("User was created: {}", userName);
            }

            var message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(messageText)
                    .build();
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }

}
