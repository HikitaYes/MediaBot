package main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static main.Logic.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
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
        if (update.hasMessage()) {
            var message = update.getMessage();
            if (message != null && message.hasText()) {
                System.out.println(message.getText());
                var text = logic.answerProcessing(message.getText());
                sendInlineKeyBoardMessage(message.getChatId().toString(), text);
            }
        } else if (update.hasCallbackQuery())
        {
            var data = update.getCallbackQuery().getData();
            var text = logic.answerProcessing(data);
            System.out.println(logic.userData.actor);
            sendInlineKeyBoardMessage(update.getCallbackQuery().getMessage().getChatId().toString(), text);

        }
    }

    public void sendMsg(String chatId, String str)
    {
        SendMessage send = new SendMessage();
        send.enableMarkdown(true);
        send.setChatId(chatId);
        send.setText(str);
        try {
            execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendInlineKeyBoardMessage(String chatId, String str)
    {
        SendMessage send = new SendMessage();
        send.enableMarkdown(true);
        send.setChatId(chatId);
        send.setText(str);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        System.out.println(logic.inlineKeyboardData);
        for (var data : logic.inlineKeyboardData)
        {
            List<InlineKeyboardButton> row = new ArrayList<>();
            var button = new InlineKeyboardButton();
            button.setText(data);
            button.setCallbackData(data);
            row.add(button);
            rowList.add(row);
//            System.out.println(button);
        }

        inlineKeyboard.setKeyboard(rowList);

        send.setReplyMarkup(inlineKeyboard);

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
