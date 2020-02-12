package main;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.inject.internal.asm.$Type;
import org.junit.Test;
import org.junit.Assert;

import static main.Logic.*;
import java.util.List;
import java.util.stream.*;

public class DataTest extends Logic { // наследование для тестировани
    private Data data = new Data();

    @Test
    public void TestData() {
        data.parssingLine("Побег из Шоушенка;д ;Морган Фриман");
        var film = "Побег из Шоушенка";
        var actors = data.getActors();
        var genres = data.getGenres();

        Assert.assertEquals(1, actors.size());
        Assert.assertEquals(1, actors.get("Морган Фриман").size());
        Assert.assertEquals(film, actors.get("Морган Фриман").get(0));

        Assert.assertEquals(1, genres.size());
        Assert.assertEquals(1, genres.get("драма").size());
        Assert.assertEquals(film, genres.get("драма").get(0));
    }

    @Test
    public void TestLogic()
    {
        var userData = new UserData();
        userData.genre = "драма";
        userData.actor = "Морган Фриман";
        var l = new Logic(userData);
        var result = l.userDataProcessing();
        Assert.assertEquals("\uFEFFПобег из Шоушенка, Брюс Всемогущий, Бен-Гур", result);
    }
}