package pl.edu.agh.hangman;

import java.util.Scanner;

/**
 * Odpowiada za komunikację z użytkownikiem poprzez konsolę.
 * Wyświetla stan gry, pobiera litery i prezentuje wynik.
 */

public class ConsoleView {

    private final HangmanArt art;

    private final Scanner scanner;

    public ConsoleView(HangmanArt art, Scanner scanner) {
        this.art = art;
        this.scanner = scanner;
    }

    public ConsoleView(HangmanArt art) {
        this(art, new Scanner(System.in));
    }

    /**
     * Wyświetla aktualną klatkę animacji wisielca
     * na podstawie liczby błędów.
     *
     * @param mistakes liczba popełnionych błędów
     */

    public void showHangman(int mistakes) {
        String[] frames = art.getFrames();
        int index = Math.min(mistakes, frames.length - 1);
        System.out.println(frames[index]);
    }

    public void showWord(String maskedWord) {
        System.out.println("Słowo: " + maskedWord);
    }

    public void showMisses(Iterable<Character> misses) {
        System.out.print("Błędne litery: ");
        for (char c : misses) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public char askForLetter() {
        System.out.print("Podaj literę: ");
        String input = scanner.nextLine().trim().toUpperCase();

        while (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            System.out.print("Wpisz JEDNĄ literę: ");
            input = scanner.nextLine().trim().toUpperCase();
        }
        return input.charAt(0);
    }

    public void showWin(String word) {
        System.out.println("Odgadłeś słowo: " + word);
        System.out.println();
        System.out.println(" \\( °ヮ°)/ ");
        GifWindow.showGif("img/success.gif");
    }

    public void showLoss(String word) {
        System.out.println("Przegrałeś! Słowo to: " + word);
        System.out.println();
        System.out.println("¯\\_(°ᐱ°)_/¯");
        GifWindow.showGif("img/fail.gif");
    }

    public int askForNumber(String message) {
        System.out.println(message);

        while (!scanner.hasNextInt()) {
            System.out.println("Podaj liczbę:");
            scanner.next();
        }
        int number = scanner.nextInt();
        scanner.nextLine();

        return number;
    }

    public boolean askYesNo(String message) {
        System.out.println(message + " (t/n)");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("t")) return true;
            if (input.equals("n")) return false;
            System.out.println("Wpisz 't' lub 'n'.");
        }
    }
}
