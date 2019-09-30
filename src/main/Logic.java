package main;
import static main.Bot.write;

class Logic
{
    private Data data;

    public Logic()
    {
        data = new Data();
    }

    protected String hello()
    {
        return "Привет! Я бот, который поможет тебе подобрать фильм по настроению. Для начала укажи свой любимый жанр";
    }

    protected String answerProcessing(String text)
    {
        if (text.equals("/help"))
            return "Я бот, который поможет тебе подобрать фильм по настроению.";
        if (data.getGenres().containsKey(text))
            return data.getGenres().get(text).get(0);
        if (data.getActors().containsKey(text)){
            return data.getActors().get(text).get(0);}

        return "Я не понял. Ответь по-другому";
    }
}
