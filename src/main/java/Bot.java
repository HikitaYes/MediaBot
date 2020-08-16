import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
                var text = logic.answerProcessing(message.getText());
                sendMsg(message.getChatId().toString(), text);
            }
        } else if (update.hasCallbackQuery())
        {
            var data = update.getCallbackQuery().getData();
            var text = logic.answerProcessing(data);
            sendMsg(update.getCallbackQuery().getMessage().getChatId().toString(), text);
        }
    }

    public void sendMsg(String chatId, String str)
    {
        SendMessage send = new SendMessage();
        send.enableMarkdown(true);
        send.setChatId(chatId);
        send.setText(str);
        if (logic.inlineKeyboardData == null)
            setKeyboardButtons(send);
        else setInlineKeyboard(send);
        try {
            execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setInlineKeyboard(SendMessage send)
    {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (var data : logic.inlineKeyboardData)
        {
            List<InlineKeyboardButton> row = new ArrayList<>();
            var button = new InlineKeyboardButton();
            button.setText(data);
            button.setCallbackData(data);
            row.add(button);
            rowList.add(row);
        }
        inlineKeyboard.setKeyboard(rowList);
        send.setReplyMarkup(inlineKeyboard);
    }

    public void setKeyboardButtons(SendMessage send)
    {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(true);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Помощь"));
        row.add(new KeyboardButton("Подобрать фильм"));
        keyboard.add(row);

        replyKeyboard.setKeyboard(keyboard);
        send.setReplyMarkup(replyKeyboard);
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
