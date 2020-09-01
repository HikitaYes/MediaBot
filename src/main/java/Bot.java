import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

class Bot extends TelegramLongPollingBot
{
    Logic logic;

    public Bot() { logic = new Logic(); }

    @Override
    public void onUpdateReceived(Update update)
    {
        if (update.hasMessage()) {
            var message = update.getMessage();
            if (message != null && message.hasText()) {
                var answer = logic.getAnswer(message.getText());
                System.out.println(message.getFrom().getFirstName() + ": " + message.getText());
                sendMessage(message.getChatId().toString(), answer);
            }

        } else if (update.hasCallbackQuery())
        {
            var data = update.getCallbackQuery().getData();
            System.out.println(update.getCallbackQuery().getFrom().getFirstName() + ": " + data);
            var answer = logic.getAnswer(data);
            sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), answer);
        }
    }

    public void sendMessage(String chatId, AnswerData answer)
    {
        var send = new SendMessage();
        send.enableMarkdown(true);
        send.setChatId(chatId);
        send.setText(answer.getText());
        var buttons = answer.getButtons();
        if (buttons == null)
            setKeyboardButtons(send);
        else setInlineKeyboard(send, buttons);
        try {
            execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("Bot: " + answer.getText());
    }

    public void setInlineKeyboard(SendMessage send, Collection<String> buttons)
    {
        var inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (var data : buttons)
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
        var replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setSelective(true);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        var row = new KeyboardRow();
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
        var properties = new Properties();
        String tokenBot = null;
        try (InputStream is = this.getClass().getResourceAsStream("config.properties")) {
            properties.load(is);
            tokenBot = properties.getProperty("tokenBot");
        }
        catch (IOException e)
        {
            System.out.println("Ошибка: не существует файла config.properties");
            e.printStackTrace();
        }
        return tokenBot;
    }
}
