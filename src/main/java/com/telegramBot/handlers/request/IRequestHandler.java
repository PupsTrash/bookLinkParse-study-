package com.telegramBot.handlers.request;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface IRequestHandler {
    boolean canProcessing(Update update);

    BotApiMethod<?> messageToUser(Update update);
}
