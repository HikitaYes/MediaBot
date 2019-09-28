package main;

import java.util.Scanner;

public class Console
{
    public static void write(String text)
    {
        System.out.println(text);
    }

    public static String read()
    {
        var in = new Scanner(System.in);
        var text = in.next();
//        in.close();
        return text;
    }
}
