package org.work.secretsantabot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class SecretSantaBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final Logger log = LoggerFactory.getLogger(SecretSantaBot.class);
    private final TelegramClient telegramClient;

    public SecretSantaBot() {
        telegramClient = new OkHttpTelegramClient(getBotToken());
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
            long chatId = update.getMessage().getChatId();

            log.info("Username: {}, text: {}", userName, messageText);

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
