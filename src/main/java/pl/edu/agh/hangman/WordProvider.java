package pl.edu.agh.hangman;

/**
 * Dostarcza słowo do gry.
 * Interfejs umożliwia podmianę źródła słów
 * bez modyfikacji logiki gry.
 * - WordSource (slowa.txt)
 * - RemoteWordProvider (Wordnik API)
 */

public interface WordProvider {

    String drawWord();
}
