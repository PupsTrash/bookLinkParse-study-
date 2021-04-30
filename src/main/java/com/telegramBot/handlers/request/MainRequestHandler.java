package com.telegramBot.handlers.request;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@AllArgsConstructor
public class MainRequestHandler implements IRequestHandler {

    List<IRequestHandler> requestHandlerList;

    @Override
    public boolean canProcessing(Update update) {
        return requestHandlerList.stream().anyMatch(k -> k.canProcessing(update));
    }

    @Override
    public BotApiMethod<?> messageToUser(Update update) {
        return requestHandlerList.stream().filter(k -> k.canProcessing(update)).findFirst()
                .get().messageToUser(update);
    }

}

