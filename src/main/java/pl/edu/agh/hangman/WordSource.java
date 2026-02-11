package pl.edu.agh.hangman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * Odpowiada za wczytywanie listy słów z pliku txt
 * i losowanie jednego z nich.
 */

public class WordSource implements WordProvider {

    private final List<String> words;
    private final Random random = new Random();

    public WordSource(String filePath, int minLength, int maxLength) {
        try {
            this.words = Files.readAllLines(Paths.get(filePath))
                    .stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(String::toUpperCase)
                    .filter(w -> w.length() >= minLength && w.length() <= maxLength)
                    .toList();

            if (words.isEmpty()) {
                throw new IllegalArgumentException("Nie ma słów w podanym zakresie długości!");
            }

        } catch (IOException e) {
            throw new RuntimeException("Plik nie wczytał się: " + filePath, e);
        }
    }

    @Override
    public String drawWord() {
        String word = words.get(random.nextInt(words.size()));
        return word.toUpperCase();
    }
}
