package com.telegramBot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class PupsBot extends TelegramWebhookBot {
    @Value("${bot.name}")
    @Getter
    private String botUsername;

    @Value("${bot.token}")
    @Getter
    private String botToken;

    @Value("${bot.webHookPath}")
    @Getter
    private String botPath;

    @Autowired
    private PupsBotServices pupsBotServices;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return pupsBotServices.initMain(update);
    }

}
