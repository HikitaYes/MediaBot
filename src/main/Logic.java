package main;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.*;

class Logic
{
    public Data data; // must be public
    public UserData userData; // must be public
    public String[] inlineKeyboardData;

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
        var result = "";

        if (films.size() != 0) //stream
        {
            result = films.get(0);
            for (var i = 1; i < films.size(); i++)
                result += ", " + films.get(i);
        }
        return result;
    }

    protected String answerProcessing(String text)
    {
        System.out.println("text: " + text);
        if (text.equals("/start")) {
            inlineKeyboardData = data.getGenres().keySet().toArray(String[]::new);
            return "Привет! Я бот, который поможет тебе подобрать фильм по настроению. Тебе нужно выбрать свой любимый жанр и актера";
        }
        else if (text.equals("/help")) {
            inlineKeyboardData = data.getGenres().keySet().toArray(String[]::new);
            return "Я бот, который поможет тебе подобрать фильм по настроению. Тебе нужно выбрать свой любимый жанр и актера";
        }
        if (userData.genre.equals(""))
        {
            if (data.getGenres().containsKey(text)) {
                userData.genre = text;
                inlineKeyboardData = data.getActorsInGenre().get(text).toArray(String[]::new);
            }
            else
                return "Такого жанра в моем списке нет :(";
            return "Теперь выбери любимого актера";
        }
        if (userData.actor.equals(""))
        {
            inlineKeyboardData = new String[0];
            if (data.getActors().containsKey(text)) {
                userData.actor = text;
            }
            else
                return "Такого актера в моем списке нет :(";
            var films = userDataProcessing();
            if (films.equals(""))
            {
                return "Таких фильмов в моем списке нет :(";
            }
            else{
                return "Тебе должны понравится эти фильмы:\n" + films;
            }
        }
        return "Я не понял. Ответь по-другому";
    }
}
