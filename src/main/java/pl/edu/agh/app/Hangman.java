package pl.edu.agh.app;

import pl.edu.agh.hangman.*;

public class Hangman {

    public static void main(String[] args) {

        ConsoleView view = new ConsoleView(new ClassicHangmanArt());

        int minLength;
        int maxLength;

        while (true) {
            minLength = view.askForNumber("Podaj minimalną długość słowa:");
            maxLength = view.askForNumber("Podaj maksymalną długość słowa:");

            if (minLength <= 0 || maxLength <= 0) {
                System.out.println("Długości muszą być dodatnie!");
                continue;
            }
            if (minLength > maxLength) {
                System.out.println("Minimalna długość nie może być większa niż maksymalna!");
                continue;
            }
            break;
        }

        System.out.println("Wybierz animację:");
        System.out.println("1 - Klasyczna");
        System.out.println("2 - Mini");

        int choice = view.askForNumber("Twój wybór:");

        HangmanArt art = switch (choice) {
            case 2 -> new MiniHangmanArt();
            default -> new ClassicHangmanArt();
        };

        view = new ConsoleView(art);

        WordProvider provider;
        if (view.askYesNo("Czy użyć słów z Wordnik?")) {
            provider = new RemoteWordProvider(minLength, maxLength);
        } else {
            provider = new WordSource("src/main/resources/slowa.txt", minLength, maxLength);
        }

        String word = provider.drawWord();
        Game game = new Game(word, art.getFrames().length - 1);

        while (!game.isFinished()) {
            view.showHangman(game.getMisses().size());
            view.showWord(game.getMaskedWord());
            view.showMisses(game.getMisses());

            char letter = view.askForLetter();
            game.guess(letter);
        }

        view.showHangman(game.getMisses().size());

        if (game.isWon()) {
            view.showWin(game.getWord());
        } else {
            view.showLoss(game.getWord());
        }
    }
}
