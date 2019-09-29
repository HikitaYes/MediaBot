package main;
import java.util.Scanner;
import static main.Logic.*;

public class Dialog
{
    private static Scanner in = new Scanner(System.in);

    public static void write(String text)
    {
        System.out.println(text);
    }

    private static String read()
    {
        return in.next();
    }

    public static void dialog()
    {
        write(hello());
        while (true)
        {
            var userText = read();
            var botReply = answerProcessing(userText);
            write(botReply);
        }
    }
}
