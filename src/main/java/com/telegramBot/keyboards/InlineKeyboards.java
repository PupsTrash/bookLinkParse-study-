package com.telegramBot.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class InlineKeyboards {
    public InlineKeyboardMarkup getKeyboardToDownload(Map<String, String> keyboardMap) {
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        for (String kName : keyboardMap.keySet()) {
            buttonList.add(InlineKeyboardButton.builder()
                    .text(kName)
                    .callbackData(keyboardMap.get(kName))
                    .build());
        }
        List<List<InlineKeyboardButton>> finalListButton = new ArrayList<>();
        finalListButton.add(buttonList);
        replyKeyboardMarkup.setKeyboard(finalListButton);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getKeyboardToPage(List<String> pageList) {
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        for (String kName : pageList) {
            buttonList.add(InlineKeyboardButton.builder()
                    .text(kName)
                    .callbackData("/page" + (Integer.parseInt(kName) - 1))
                    .build());
        }
        List<List<InlineKeyboardButton>> finalListButton = new ArrayList<>();
        finalListButton.add(buttonList);
        replyKeyboardMarkup.setKeyboard(finalListButton);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getTestKeyboard() {
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttonList1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonList2 = new ArrayList<>();

        final InlineKeyboardButton b1 = new InlineKeyboardButton("1");
        b1.setCallbackData("test");
        final InlineKeyboardButton b2 = new InlineKeyboardButton("2");
        b2.setCallbackData("test");
        final InlineKeyboardButton b3 = new InlineKeyboardButton("3");
        b3.setCallbackData("test");
        final InlineKeyboardButton b4 = new InlineKeyboardButton("4");
        b4.setCallbackData("test");

        buttonList1.add(b1);
        buttonList1.add(b2);

        buttonList2.add(b3);
        buttonList2.add(b4);

        List<List<InlineKeyboardButton>> finalListButton = new ArrayList<>();
        finalListButton.add(buttonList1);
        finalListButton.add(buttonList2);
        replyKeyboardMarkup.setKeyboard(finalListButton);
        return replyKeyboardMarkup;
    }

}
