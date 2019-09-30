package main;
import org.junit.Test;
import org.junit.Assert;
import static main.Bot.write;
//import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {
    private Data data = new Data();

    @Test
    public void Test1()
    {
//        var film = data.getGenres().get("драма").get(0);
//        System.out.println(film.equals("Побег из Шоушенка"));
//        System.out.println(film.getClass());
//        System.out.println("Побег из Шоушенка".getClass());
        Assert.assertEquals("Побег из Шоушенка", data.getGenres().get("драма").get(0));
    }
}