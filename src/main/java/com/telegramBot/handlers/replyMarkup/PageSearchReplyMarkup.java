package com.telegramBot.handlers.replyMarkup;

import com.flibusta.InitialParse;
import com.telegramBot.handlers.request.TextRequestHandlerImpl;
import com.telegramBot.keyboards.InlineKeyboards;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PageSearchReplyMarkup implements IReplyMarkup {
    private final InlineKeyboards inlineKeyboards;
    private final TextRequestHandlerImpl textRequestHandler;
    private final InitialParse initialParse;

    @Override
    public boolean canProcessing(Update update) {
        return update.getCallbackQuery().getData().startsWith("/page") && !update.hasMessage();
    }

    @Override
    public BotApiMethod<?> messageToUser(Update update) {
        final String searchRequest = update.getCallbackQuery().getMessage().getReplyToMessage().getText();
        final String data = update.getCallbackQuery().getData().replace("/page", "");
        EditMessageText editMessageText = new EditMessageText();
        try {
            editMessageText.setText("check edit message \n" +
                    textRequestHandler.formatTextBuilder(initialParse
                            .getLinkMap(searchRequest + "&page=" + data)));
        } catch (IOException e) {
            e.printStackTrace();
            return new SendMessage(update.getCallbackQuery().getFrom().getId().toString(),
                    "exception pagers choice");
        }
        editMessageText.setReplyMarkup(inlineKeyboards.getKeyboardToPage(initialParse.getPageButton()));
        editMessageText.setParseMode(ParseMode.HTML);
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setChatId(update.getCallbackQuery().getFrom().getId().toString());
        return editMessageText;
    }
}
