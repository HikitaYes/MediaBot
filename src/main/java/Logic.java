import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.*;

class Logic
{
    private Data data;
    private HashMap<Long, UserData> chatIdData = new HashMap<>();

    public Logic()
    {
        data = new Data();
        data.readDate();
    }

    protected List<String> getFilms(UserData userData)
    {
        List<String> filmsByGenre = data.getGenres().get(userData.getGenre());
        List<String> filmsByActors = data.getActors().get(userData.getActor());
        return filmsByGenre.stream()
                .filter(filmsByActors::contains)
                .collect(Collectors.toList());
    }

    protected AnswerData getAnswer(String text, Long id)
    {
        var helpMessage = "Я бот, который поможет тебе подобрать фильм. Тебе нужно выбрать свой любимый жанр и актера";
        var sorryMessage = "Чтобы подобрать фильм, нажми \"Подобрать фильм\"";
        switch (text)
        {
            case "/start":
                return new AnswerData(String.format("Привет! %s", helpMessage));
            case "Помощь":
                return new AnswerData(helpMessage);
            case "Подобрать фильм":
                Collection<String> buttons = data.getGenres().keySet();
                chatIdData.put(id, new UserData());
                return new AnswerData("Выбери свой любимый жанр", buttons);
            default:
                if (!chatIdData.containsKey(id)) {
                    return new AnswerData(sorryMessage);
                }
                var userData = chatIdData.get(id);
                var genre = userData.getGenre();
                var actor = userData.getActor();

                if (genre.isEmpty()) {
                    if (data.getGenres().containsKey(text)) {
                        userData.setGenre(text);

                        buttons = data.getActorsInGenre(text).stream().sorted().collect(Collectors.toList());
                        return new AnswerData("Теперь выбери своего любимого актера", buttons);
                    } else {
                        return new AnswerData("Такого жанра в моем списке нет");
                    }
                } else if (actor.isEmpty()) {
                    if (data.getActors().containsKey(text)) {
                        userData.setActor(text);

                        var films = getFilms(userData);
                        if (films.size() == 1) {
                            return new AnswerData(String.format("Тебе должен понравится фильм \"%s\"", films));
                        } else {
                            return new AnswerData(String.format(
                                    "Тебе должны понравится эти фильмы:%n%s",
                                    String.join(", ", films)));
                        }
                    } else {
                        return new AnswerData("Такого актера в моем списке нет");
                    }
                } else return new AnswerData(sorryMessage);
        }
    }
}
