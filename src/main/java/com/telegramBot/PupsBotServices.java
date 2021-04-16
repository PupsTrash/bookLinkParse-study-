package com.telegramBot;

import com.flibusta.InitialParseImpl;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@NoArgsConstructor
public class PupsBotServices {

    @Autowired
    private InitialParseImpl initialParse;

    public SendMessage initMain(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            return choseAction(message);
        }
        return null;
    }

    private SendMessage choseAction(Message message) {
        String inputMessage = message.getText();
        Long userId = message.getFrom().getId();


        if (inputMessage.startsWith("http://flibustahezeous3.onion")){


            //do Smthg
        }else if (inputMessage.length()>=3){


            //initalparse
            try {
                Map<String, String> test = initialParse.getLinkMap(inputMessage);
                if (test == null){
                    return new SendMessage(userId.toString(), "OOPS, i can`t find this!");
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(initialParse.getTitleLinkMap());
                stringBuffer.append("\n");
                int i = 1;
                for (String a : test.values()) {
                    stringBuffer.append(i);
                    stringBuffer.append(". ");
                    stringBuffer.append(a);
                    stringBuffer.append("\n");
                    i++;
                }
                return new SendMessage(userId.toString(), stringBuffer.toString() );
            } catch (IOException e) {
                e.printStackTrace();
            }




        }else {
            return new SendMessage(userId.toString(), "lets try again");
        }


        return new SendMessage(userId.toString(), "oops, Something went wrong");

    }
}
