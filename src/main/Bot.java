package main;
import java.util.Scanner;

import static main.Logic.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.*;

class Bot extends TelegramLongPollingBot
{
    Logic logic;

    public Bot()
    {
        logic = new Logic();
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        var message = update.getMessage();
        if (message != null && message.hasText())
        {
            var text = logic.answerProcessing(message.getText());
            sendMsg(message, text);
        }
    }

    public void sendMsg(Message message, String str)
    {
        SendMessage send = new SendMessage();
        send.enableMarkdown(true);
        send.setChatId(message.getChatId().toString());
        send.setText(str);
        System.out.println(message.getText());
        try {
            execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return "FilmChooser";
    }

    @Override
    public String getBotToken() {
        return "911382208:AAHSdDkeEMfOWum5QAecrzP1mhGfV67lG1Y";
    }
}
