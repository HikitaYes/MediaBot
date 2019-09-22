package main;
import static main.Console.write;
import static main.Console.read;

public class Dialog
{
    private static String answerProcessing(String text)
    {
        switch (text)
        {
            case ("/help"):
                return "Я бот, который поможет тебе подобрать фильм по настроению.";

            default:
                return "Я не понял. Ответь по-другому";
        }
    }

    public static void dialog()
    {
        write("Привет! Я бот, который поможет тебе подобрать фильм по настроению. Для начала укажи свой любимый жанр");
        while (true)
        {
            var userText = read();
            var botReply = answerProcessing(userText);
            write(botReply);
        }
    }
}
