package main;
import org.junit.Test;
import org.junit.Assert;
//import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {

    @Test
    public void Test1()
    {
        var film = Logic.parssingLine("Побег из Шоушенка;д ;Морган Фриман");
        Assert.assertEquals("Побег из Шоушенка", film);
    }

    public void parssingLine() {
    }
}