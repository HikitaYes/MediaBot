package main;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

class Logic
{
    private Data data;
    private UserData userData;

    public Logic(UserData userData)
    {
        data = new Data();
        data.readDate();
        this.userData = userData;
    }

    public Logic()
    {
        data = new Data();
        data.readDate();
        userData = new UserData();
    }

    protected String userDataProcessing()
    {
        List<String> filmsByGenre;
        List<String> filmsByActors;
        filmsByGenre = data.getGenres().get(userData.genre);
        filmsByActors = data.getActors().get(userData.actor);
        // вместо filmsByGenre.retainAll(filmsByActors); используем stream
        List<String> films = filmsByGenre.stream()
                .filter(filmsByActors::contains) // = (film -> filmsByActors.contains(film)
                .collect(Collectors.toList());
        userData.actor = "";
        userData.genre = "";
        var result = "Таких фильмов в моем списке нет :(";

        if (films.size() != 0)//stream
        {
            result = films.get(0);
            for (var i = 1; i < films.size(); i++)
                result += ", " + films.get(i);
        }
        return result;
    }

    protected String answerProcessing(String text)
    {
        if (text.equals("/start"))
            return "Привет! Я бот, который поможет тебе подобрать фильм по настроению. Для начала укажи свой любимый жанр";
        else
            if (text.equals("/help"))
                return "Я бот, который поможет тебе подобрать фильм по настроению. Тебе нужно указать свой любимый жанр и актера";
        if (userData.genre.equals(""))
        {
            if (data.getGenres().containsKey(text))
                userData.genre = text;
            else
                return "Такого жанра в моем списке нет :(";
            return "Теперь укажи любимого актера";
        }
        if (userData.actor.equals(""))
        {
            if (data.getActors().containsKey(text))
                userData.actor = text;
            else
                return "Такого актера в моем списке нет :(";
            return "Тебе должны понравится эти фильмы:\n" + userDataProcessing();
        }
        return "Я не понял. Ответь по-другому";
    }
}
