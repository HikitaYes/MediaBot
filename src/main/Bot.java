package main;
import java.util.Scanner;
import static main.Logic.*;

class Bot
{
    private static Scanner in = new Scanner(System.in);

    public static void write(String text)
    {
        System.out.println(text);
    }

    private static String read()
    {
        return in.nextLine();
    }

    public void dialog()
    {
        var logic = new Logic();
        write(logic.hello());
        while (true)
        {
            var userText = read();
            var botReply = logic.answerProcessing(userText);
            write(botReply);
        }
    }
}
