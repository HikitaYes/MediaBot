package main;

public class Logic
{
    public static String hello()
    {
        return "Привет! Я бот, который поможет тебе подобрать фильм по настроению. Для начала укажи свой любимый жанр";
    }
    public static String answerProcessing(String text)
    {
        switch (text)
        {
            case ("/help"):
                return "Я бот, который поможет тебе подобрать фильм по настроению.";

            default:
                return "Я не понял. Ответь по-другому";
        }
    }
}
