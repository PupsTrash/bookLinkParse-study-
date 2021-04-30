package com.telegramBot;

import com.telegramBot.handlers.replyMarkup.IReplyMarkup;
import com.telegramBot.handlers.request.IRequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class PupsBotServices {


    private final IRequestHandler mainRequestHandler;
    private final IReplyMarkup mainReplyMarkupHandler;

    public BotApiMethod<?> initMain(Update update) {

        if (!update.hasCallbackQuery() && update.getMessage().hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getChatId(),
                    update.getMessage().getText());

            if (mainRequestHandler.canProcessing(update)) {
                return mainRequestHandler.messageToUser(update);
            }

            return new SendMessage(update.getMessage().getFrom().getId().toString(),
                    "oops, invalid request... Don't worry & Try again");
        }

        if (update.hasCallbackQuery()) {
            log.info("New callbackQuery from User:{}, chatId: {},  with data: {}",
                    update.getCallbackQuery().getFrom().getUserName(),
                    update.getCallbackQuery().getFrom().getId(),
                    update.getCallbackQuery().getData());

            if (mainReplyMarkupHandler.canProcessing(update)) {
                return mainReplyMarkupHandler.messageToUser(update);
            }

            return new SendMessage(update.getCallbackQuery().getFrom().getId().toString(),
                    "oops, invalid request... Don't worry & Try again");
        }


        return new SendMessage(update.getMessage().getFrom().getId().toString(), "error processing message. see PupsBotServices");
    }
}
