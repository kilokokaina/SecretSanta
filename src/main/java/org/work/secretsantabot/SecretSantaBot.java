package org.work.secretsantabot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static java.lang.Math.toIntExact;

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

            switch (messageText) {
                case "/manage_session" -> {
                    var message = SendMessage
                            .builder()
                            .chatId(chatId)
                            .text(messageText)
                            .replyMarkup(
                                    InlineKeyboardMarkup.builder().keyboardRow(
                                            new InlineKeyboardRow(InlineKeyboardButton
                                                    .builder()
                                                    .text("Закрыть регистрацию")
                                                    .callbackData("update_msg_text")
                                                    .build()
                                            )
                                    )
                                    .build()
                            )
                            .build();
                    try {
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        log.error(e.getMessage());
                    }
                }
                default -> {
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
        } else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if (call_data.equals("update_msg_text")) {
                String answer = "Updated message text";
                EditMessageText new_message = EditMessageText.builder()
                        .chatId(chat_id)
                        .messageId(toIntExact(message_id))
                        .text(answer)
                        .build();
                try {
                    telegramClient.execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
