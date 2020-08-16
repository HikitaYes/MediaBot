import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;

public class DataTest extends Logic { // наследование для тестировани
    private Data data = new Data();

    @Test
    public void TestData() {
        data.parssingLine("Побег из Шоушенка;д ;Морган Фриман");
        var films = new ArrayList<String>() {{ add("Побег из Шоушенка"); }};
        var actorsReal = new ArrayList<String>() {{ add("Морган Фриман"); }};
        var actors = data.getActors();
        var genres = data.getGenres();
        var actorsInGenre = data.getActorsInGenre();

        Assert.assertEquals(1, actors.size());
        Assert.assertEquals(1, actors.get("Морган Фриман").size());
        Assert.assertEquals(films.get(0), actors.get("Морган Фриман").get(0));

        Assert.assertEquals(1, genres.size());
        Assert.assertEquals(1, genres.get("Драма").size());
        Assert.assertEquals(films.get(0), genres.get("Драма").get(0));

        Assert.assertEquals(1, actorsInGenre.size());
        Assert.assertEquals(1, actorsInGenre.get("Драма").size());
        Assert.assertEquals(actorsReal.get(0), actorsInGenre.get("Драма").get(0));

        data.parssingLine("Бойцовский клуб;т д кр;Эдвард Нортон,Брэд Питт,Хелена Бонем Картер");
        films.add("Бойцовский клуб");

        genres = data.getGenres();
        actorsInGenre = data.getActorsInGenre();

        Assert.assertEquals(Arrays.asList("Морган Фриман", "Эдвард Нортон", "Брэд Питт", "Хелена Бонем Картер"), actorsInGenre.get("Драма"));
        Assert.assertEquals(Arrays.asList("Эдвард Нортон", "Брэд Питт", "Хелена Бонем Картер"), actorsInGenre.get("Триллер"));

        Assert.assertEquals(films, genres.get("Драма"));
    }

    @Test
    public void TestLogic()
    {
        var userData = new UserData();
        userData.genre = "Драма";
        userData.actor = "Морган Фриман";
        var l = new Logic(userData);
        var result = l.userDataProcessing();
        Assert.assertEquals("\uFEFFПобег из Шоушенка, Брюс Всемогущий, Бен-Гур, Темный рыцарь, Семь", result);
    }
}