package main;
import java.util.ArrayList;
import java.util.List;

import static main.Bot.write;

class Logic
{
    private Data data;
    private UserData userData;

    public Logic()
    {
        data = new Data();
        userData = new UserData();
    }

    protected String hello()
    {
        return "Привет! Я бот, который поможет тебе подобрать фильм по настроению. Для начала укажи свой любимый жанр";
    }

    private String userDataProcessing()
    {
        List<String> filmsByGenre;
        List<String> filmsByActors;
        filmsByGenre = data.getGenres().get(userData.genre);
        filmsByActors = data.getActors().get(userData.actor);
        filmsByGenre.retainAll(filmsByActors);
        userData.actor = "";
        userData.genre = "";
        var result = "";
        for (String film : filmsByGenre)
            result += film + " ,";
        return result;
    }

    protected String answerProcessing(String text)
    {
        if (text.equals("/help"))
            return "Я бот, который поможет тебе подобрать фильм по настроению.";
        if (userData.genre.equals(""))
        {
            if (!data.getGenres().containsKey(userData.genre))
                return "Такого жанра нет в моем списке :(";
            userData.genre = text;
            return "Теперь укажи любимого актера";
        }
        if (userData.actor.equals(""))
        {
            userData.actor = text;

            if (!data.getActors().containsKey(userData.actor))
                return "Такого актера нет в моем списке :(";
            return "Тебе должны понравится эти фильмы: \n" + userDataProcessing();
        }
        return "Я не понял. Ответь по-другому";
    }
}
