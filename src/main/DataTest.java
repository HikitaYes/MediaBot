package main;
import org.junit.Test;
import org.junit.Assert;

public class DataTest
{
    private Data data = new Data();

    @Test
    public void Test1()
    {
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
}