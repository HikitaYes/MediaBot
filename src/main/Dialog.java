package main;
import java.util.Scanner;
import static main.Logic.answerProcessing;
import static main.Logic.hello;

public class Dialog
{
    private static Scanner in = new Scanner(System.in);

    private static void write(String text)
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
