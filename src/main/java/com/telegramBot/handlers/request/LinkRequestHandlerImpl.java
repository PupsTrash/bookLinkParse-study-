package com.telegramBot.handlers.request;

import com.flibusta.BookPageParse;
import com.telegramBot.keyboards.InlineKeyboards;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LinkRequestHandlerImpl implements IRequestHandler {

    private final BookPageParse bookPageParse;
    private final InlineKeyboards inlineKeyboards;

    @Override
    public boolean canProcessing(Update update) {
        return update.hasMessage() &&
                update.getMessage().getText().startsWith("/d");
    }

    @Override
    public BotApiMethod<?> messageToUser(Update update) {
        SendMessage messageToUser = new SendMessage();
        messageToUser.setParseMode(ParseMode.HTML);
        final String request = update.getMessage().getText().replace("/d", "");

        try {
            messageToUser.setText(bookPageParse.getBookInfo(request));
            messageToUser.setReplyMarkup(inlineKeyboards.getKeyboardToDownload(bookPageParse.getBookFormat(request)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException a) {
            a.printStackTrace();
            messageToUser.setText("failed get info about book");
        }
        messageToUser.setChatId(update.getMessage().getFrom().getId().toString());
        return messageToUser;
    }
}


