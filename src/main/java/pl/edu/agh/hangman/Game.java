package pl.edu.agh.hangman;

import java.util.HashSet;
import java.util.Set;


/**
 * Klasa reprezentuje logikę gry w Hangmana.
 * Przechowuje słowo do odgadnięcia, listę trafionych i nietrafionych liter
 * oraz udostępnia metody do wykonywania ruchów i sprawdzania stanu gry.
 */

public class Game {

    /**
     * Tworzy nową grę z podanym słowem i maksymalną liczbą błędów.
     * word - słowo do odgadnięcia (najlepiej wielkimi literami)
     * maxErrors - maksymalna liczba dopuszczalnych pomyłek
     * hits - trafione
     * misses - nietrafione
     */

    private final String word;
    private final int maxErrors;
    private final Set<Character> hits;
    private final Set<Character> misses;

    public Game(String word, int maxErrors) {
        this.word = word;
        this.maxErrors = maxErrors;
        this.hits = new HashSet<>();
        this.misses = new HashSet<>();
    }


    /**
     * Wykonuje próbę odgadnięcia litery.
     *
     * @param letter litera podana przez gracza
     * @return true jeśli litera występuje w słowie
     * <p>
     * Jeśli litera była już wcześniej zgadywana, nie zwiększa liczby błędów.
     */

    public boolean guess(char letter) {
        letter = Character.toUpperCase(letter);

        if (hits.contains(letter) || misses.contains(letter)) {
            return word.indexOf(letter) >= 0;
        }

        if (word.indexOf(letter) >= 0) {
            hits.add(letter);
            return true;
        } else {
            misses.add(letter);
            return false;
        }
    }

    public boolean isWon() {
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c) && !hits.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean isLost() {
        return misses.size() >= maxErrors;
    }

    public boolean isFinished() {
        return isWon() || isLost();
    }


    /**
     * Zwraca zamaskowaną wersję słowa.
     * Nieodgadnięte litery zastępowane są znakiem '_'.
     *
     * @return zamaskowane słowo gotowe do wyświetlenia
     */


    public String getMaskedWord() {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c)) {
                sb.append(c).append(' ');
            } else if (hits.contains(c)) {
                sb.append(c).append(' ');
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }

    public Set<Character> getMisses() {
        return misses;
    }

    public String getWord() {
        return word;
    }
}
