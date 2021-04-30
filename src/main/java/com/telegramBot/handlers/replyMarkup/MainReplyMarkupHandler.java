package com.telegramBot.handlers.replyMarkup;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@AllArgsConstructor
public class MainReplyMarkupHandler implements IReplyMarkup {

    private final List<IReplyMarkup> iReplyMarkupList;

    @Override
    public boolean canProcessing(Update update) {
        return iReplyMarkupList.stream().anyMatch(o -> o.canProcessing(update));
    }

    @Override
    public BotApiMethod<?> messageToUser(Update update) {
        return iReplyMarkupList.stream().filter(k -> k.canProcessing(update)).findFirst()
                .get().messageToUser(update);
    }
}
