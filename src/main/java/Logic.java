import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.stream.*;

class Logic
{
    public Data data;
    public UserData userData;
    public String[] inlineKeyboardData;
    private boolean isChoosingProcess = false;

    public Logic(UserData userData)
    {
        data = new Data();
        data.readDate();
        this.userData = userData;
    }

    public Logic()
    {
        this(new UserData());
    }

    protected String userDataProcessing()
    {
        List<String> filmsByGenre = data.getGenres().get(userData.genre);
        List<String> filmsByActors = data.getActors().get(userData.actor);
        // вместо filmsByGenre.retainAll(filmsByActors); используем stream
        List<String> films = filmsByGenre.stream()
                .filter(filmsByActors::contains)
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
        String helpMessage = "Я бот, который поможет тебе подобрать фильм по настроению. Тебе нужно выбрать свой любимый жанр и актера";
        switch (text)
        {
            case "/start":
                return String.format("Привет! %s", helpMessage);
            case "Помощь":
                return helpMessage;
            case "Подобрать фильм":
                isChoosingProcess = true;
                inlineKeyboardData = data.getGenres().keySet().toArray(String[]::new);
                userData.genre = "";
                userData.actor = "";
                return "Выбери свой любимый жанр";
            default:
                if (isChoosingProcess) {
                    if (userData.genre.equals("")) {
                        userData.genre = text;
                        inlineKeyboardData = data.getActorsInGenre().get(text).toArray(String[]::new);
                        return "Теперь выбери своего любимого актера";
                    } else { // обработка актеров
                        isChoosingProcess = false;
                        inlineKeyboardData = null;
                        userData.actor = text;

                        var films = userDataProcessing();
                        if (films.split(",").length == 1)
//                            return "Тебе должен понравится фильм \"" + films + "\"";
                            return String.format("Тебе должен понравится фильм \"%s\"", films);
                        else
//                            return "Тебе должны понравится эти фильмы:\n" + films;
                            return String.format("Тебе должны понравится эти фильмы:%n%s", films);
                    }
                }
                else return "Чтобы подобрать фильм, нажми \"Подобрать фильм\"";
        }
    }
}
