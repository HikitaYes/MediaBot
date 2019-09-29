package main;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import static main.Dialog.*;

public class Logic
{
    public static HashMap<String, ArrayList<String>> genresType = new HashMap<>();
    static HashMap<String, ArrayList<String>> actorsName = new HashMap<>();
    static Map<String, String> genresFullName = Map.of(
            "д", "драма",
            "к", "комедия",
            "фэ", "фэнтези",
            "фа", "фантастика"
            );

    public static HashMap<String, ArrayList<String>> parssingLine(String line)
    {
        var split = line.split(";");
        var film = split[0];
        var genres = split[1];
        for (var genre : genres.split(" "))
        {
            var genreFullName = genresFullName.get(genre);
            if (genresType.containsKey(genreFullName))
                genresType.get(genreFullName).add(film);
            else
                genresType.put(genreFullName, new ArrayList<String>(){{add(film);}});
        }
        var actors = split[2];
        for (var actor : actors.split(","))
        {
            if (actorsName.containsKey(actor))
                actorsName.get(actor).add(film);
            else
                actorsName.put(actor, new ArrayList<String>(){{add(film);}});
        }

        return genresType;
    }

    public static void readDate()
    {
        try {
            var fr = new FileReader("data.txt");
            var scan = new Scanner(fr);
            while (scan.hasNextLine())
            {
                var line = scan.nextLine();
                parssingLine(line);
                write(line);
            }
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e)
        {
            write("Файл не найден");
        }
//        write(genresType.toString());
//        write(actorsName.toString());
    }

    public static String hello()
    {
        return "Привет! Я бот, который поможет тебе подобрать фильм по настроению. Для начала укажи свой любимый жанр";
    }

    public static String answerProcessing(String text)
    {
        switch (text)
        {
            case ("/help"):
                return "Я бот, который поможет тебе подобрать фильм по настроению.";

            default:
                return "Я не понял. Ответь по-другому";
        }
    }
}
