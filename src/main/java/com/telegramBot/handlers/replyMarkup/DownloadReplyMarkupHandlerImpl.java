package com.telegramBot.handlers.replyMarkup;

import com.flibusta.IDownloadFile;
import com.telegramBot.PupsBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DownloadReplyMarkupHandlerImpl implements IReplyMarkup {

    private final PupsBot pupsBot;
    private final IDownloadFile iDownloadFile;

    @Override
    public boolean canProcessing(Update update) {
        return update.getCallbackQuery().getData().startsWith("/b") && !update.hasMessage();
    }

    @Override
    public BotApiMethod<?> messageToUser(Update update) {
        try {
            File test = iDownloadFile.downloadFileTo("http://flibustahezeous3.onion" + update.getCallbackQuery().getData());
            InputFile inputFile = new InputFile(test);
            pupsBot.sendDocument(update.getCallbackQuery().getFrom().getId(),
                    update.getCallbackQuery().getData(),
                    inputFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new SendMessage(update.getCallbackQuery().getFrom().getId().toString(), "error download");
        }
        return new SendMessage();
    }
}
