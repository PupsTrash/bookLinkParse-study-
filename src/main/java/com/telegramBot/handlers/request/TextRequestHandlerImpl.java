package com.telegramBot.handlers.request;

import com.flibusta.InitialParse;
import com.telegramBot.keyboards.InlineKeyboards;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TextRequestHandlerImpl implements IRequestHandler {

    private final InitialParse initialParse;
    private final InlineKeyboards inlineKeyboards;


    @Override
    public boolean canProcessing(Update update) {
        return update.hasMessage() &&
                update.getMessage().getText().length() > 3 &&
                !update.getMessage().getText().startsWith("/");
    }

    @Override
    public BotApiMethod<?> messageToUser(Update update) {
        String userId = update.getMessage().getFrom().getId().toString();
        try {
            SendMessage message = new SendMessage();

            Map<String, String> test = initialParse.getLinkMap(update.getMessage().getText());
            if (test.isEmpty()) {
                return new SendMessage(update.getMessage().getFrom().getId().toString(), "OOPS, i can`t find this!");
            }

            String stringBuilder = formatTextBuilder(test);

            message.setParseMode(ParseMode.HTML);
            message.setReplyMarkup(inlineKeyboards.getKeyboardToPage(initialParse.getPageButton()));
            message.setText(stringBuilder);
            message.setChatId(userId);
            message.setReplyToMessageId(update.getMessage().getMessageId());
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SendMessage(update.getMessage().getFrom().getId().toString(), "OOPS, failed parse");
    }

    public String formatTextBuilder(Map<String, String> mapLinkToTitleBook) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>").append(initialParse.getTitleLinkMap()).append("</b>\n");
        for (String a : mapLinkToTitleBook.keySet()) {
            stringBuilder.append("<a href=\"").append(a).append("\">")
                    .append("/d").append(a).append("</a>")
                    .append("<b> ").append(mapLinkToTitleBook.get(a)).append("</b>").append("\n");
        }
        return stringBuilder.toString();
    }
}
