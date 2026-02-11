package pl.edu.agh.hangman;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Dostarcza słowa z zewnętrznego serwisu Wordnik.
 */

public class RemoteWordProvider implements WordProvider {

    private final int minLength;
    private final int maxLength;

    public RemoteWordProvider(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public String drawWord() {
        try {
            String url = "https://api.wordnik.com/v4/words.json/randomWords"
                    + "?hasDictionaryDef=true"
                    + "&minCorpusCount=0"
                    + "&minLength=" + minLength
                    + "&maxLength=" + maxLength
                    + "&limit=1"
                    + "&api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5";

            InputStream stream = new URL(url).openStream();
            String json = new Scanner(stream).useDelimiter("\\A").next();

            String word = json.split("\"word\":\"")[1].split("\"")[0];

            return word.toUpperCase();

        } catch (Exception e) {
            throw new RuntimeException("Nie udało się pobrać słowa.", e);
        }
    }
}
