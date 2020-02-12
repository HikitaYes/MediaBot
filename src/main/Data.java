package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Data
{
    private HashMap<String, ArrayList<String>> genresType = new HashMap<>();
    private HashMap<String, ArrayList<String>> actorsName = new HashMap<>();
    private Map<String, String> genresFullName = Map.of(
            "д", "драма",
            "к", "комедия",
            "фэ", "фэнтези",
            "фа", "фантастика",
            "и", "историческое кино"
    );

    public HashMap<String, ArrayList<String>> getGenres() { return genresType; }

    public HashMap<String, ArrayList<String>> getActors() {
        return actorsName;
    }

    public void parssingLine(String line)
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
                genresType.put(genreFullName, new ArrayList<String>(){{ add(film); }});
        }
        var actors = split[2];
        for (var actor : actors.split(","))
        {
            if (actorsName.containsKey(actor))
                actorsName.get(actor).add(film);
            else
                actorsName.put(actor, new ArrayList<String>(){{ add(film); }});
        }
    }

    protected void readDate()
    {
        try {
            var fr = new FileReader("data.txt");
            var scan = new Scanner(fr);
            while (scan.hasNextLine())
            {
                var line = scan.nextLine();
                parssingLine(line);
            }
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("Файл не найден");
        }
    }
}
