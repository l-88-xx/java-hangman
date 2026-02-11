package pl.edu.agh.hangman;

import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Scanner;
import java.util.Set;

import static org.junit.Assert.*;

public class HangmanTest {


    /**
     * GAME TESTS
     */

    @Test
    public void shouldRevealCorrectLetters() {
        Game game = new Game("TEST", 5);
        game.guess('T');
        assertEquals("T _ _ T", game.getMaskedWord());
    }

    @Test
    public void shouldNotAddMistakeWhenGuessingSameLetterTwice() {
        Game game = new Game("TEST", 5);
        game.guess('A');
        game.guess('A');
        assertEquals(1, game.getMisses().size());
    }

    @Test
    public void shouldWinWhenAllLettersGuessed() {
        Game game = new Game("AB", 5);
        game.guess('A');
        game.guess('B');
        assertTrue(game.isWon());
        assertTrue(game.isFinished());
    }

    @Test
    public void shouldLoseAfterTooManyMistakes() {
        Game game = new Game("A", 2);
        game.guess('X');
        game.guess('Y');
        assertTrue(game.isLost());
        assertTrue(game.isFinished());
    }

    @Test
    public void shouldShowOnlyGuessedLetters() {
        Game game = new Game("JAVA", 5);
        game.guess('A');
        assertEquals("_ A _ A", game.getMaskedWord());
    }

    @Test
    public void shouldTreatGuessAsCaseInsensitive() {
        Game game = new Game("JAVA", 5);
        game.guess('j');
        assertEquals("J _ _ _", game.getMaskedWord());
    }

    @Test
    public void shouldAddHitOnCorrectGuess() {
        Game game = new Game("TEST", 5);
        boolean result = game.guess('T');
        assertTrue(result);
        assertTrue(game.getMisses().isEmpty());
        assertEquals("T _ _ T", game.getMaskedWord());
    }

    @Test
    public void shouldAddMissOnIncorrectGuess() {
        Game game = new Game("TEST", 5);
        boolean result = game.guess('A');
        assertFalse(result);
        assertEquals(1, game.getMisses().size());
        assertTrue(game.getMisses().contains('A'));
    }

    @Test
    public void shouldNotDuplicateHitWhenGuessingSameCorrectLetterTwice() {
        Game game = new Game("TEST", 5);
        game.guess('T');
        game.guess('T');
        assertEquals("T _ _ T", game.getMaskedWord());
    }

    @Test
    public void shouldNotBeFinishedAtStart() {
        Game game = new Game("TEST", 5);
        assertFalse(game.isFinished());
    }

    @Test
    public void shouldShowNonLettersAsIs() {
        Game game = new Game("A-B", 5);
        game.guess('A');
        assertEquals("A - _", game.getMaskedWord());
    }

    @Test
    public void shouldReturnOriginalWord() {
        Game game = new Game("HELLO", 5);
        assertEquals("HELLO", game.getWord());
    }

    @Test
    public void shouldReturnMissesSetCorrectly() {
        Game game = new Game("HELLO", 5);
        game.guess('X');
        game.guess('Y');
        Set<Character> misses = game.getMisses();
        assertEquals(2, misses.size());
        assertTrue(misses.contains('X'));
        assertTrue(misses.contains('Y'));
    }

    /**
     * WORDSOURCE TESTS
     */

    @Test
    public void shouldLoadNonEmptyWordList() {
        WordSource source = new WordSource("src/main/resources/slowa.txt", 1, 5);
        String word = source.drawWord();
        assertNotNull(word);
        assertFalse(word.isBlank());
    }

    @Test
    public void shouldReturnUppercaseWords() {
        WordSource source = new WordSource("src/main/resources/slowa.txt", 4, 8);
        String word = source.drawWord();
        assertEquals(word, word.toUpperCase());
    }

    @Test
    public void shouldRespectLengthLimits() {
        WordSource source = new WordSource("src/main/resources/slowa.txt", 3, 3);
        String word = source.drawWord();
        assertEquals(3, word.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenNoWordsInRange() {
        new WordSource("src/main/resources/slowa.txt", 1000, 2000);
    }


    /**
     * CONSOLEVIEW TESTS
     */

    @Test
    public void shouldAcceptValidLetter() {
        String input = "a\n";
        ConsoleView view = new ConsoleView(new ClassicHangmanArt(), new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertEquals('A', view.askForLetter());
    }

    @Test
    public void shouldRejectInvalidLetterUntilValid() {
        String input = "12\n%\nA\n";
        ConsoleView view = new ConsoleView(new ClassicHangmanArt(), new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertEquals('A', view.askForLetter());
    }

    @Test
    public void shouldAcceptYesNoInput() {
        String input = "x\nt\n";
        ConsoleView view = new ConsoleView(new ClassicHangmanArt(), new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertTrue(view.askYesNo("Czy?"));
    }

    @Test
    public void shouldRejectInvalidNumberUntilValid() {
        String input = "k\n%\n10\n";
        ConsoleView view = new ConsoleView(new ClassicHangmanArt(), new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertEquals(10, view.askForNumber("Podaj liczbę:"));
    }

    @Test
    public void shouldNotThrowWhenShowingWord() {
        ConsoleView view = new ConsoleView(new ClassicHangmanArt());
        view.showWord("A _ A");
    }

    @Test
    public void shouldNotThrowWhenShowingMisses() {
        ConsoleView view = new ConsoleView(new ClassicHangmanArt());
        view.showMisses(java.util.List.of('X', 'Z'));
    }

    @Test
    public void shouldNotThrowWhenShowingHangman() {
        ConsoleView view = new ConsoleView(new ClassicHangmanArt());
        view.showHangman(0);
    }

    @Test
    public void shouldNotThrowWhenShowingWin() {
        ConsoleView view = new ConsoleView(new ClassicHangmanArt());
        view.showWin("TEST");
    }

    @Test
    public void shouldNotThrowWhenShowingLoss() {
        ConsoleView view = new ConsoleView(new ClassicHangmanArt());
        view.showLoss("TEST");
    }

    @Test
    public void shouldNotThrowWhenShowingWordMini() {
        ConsoleView view = new ConsoleView(new MiniHangmanArt());
        view.showWord("A _ A");
    }

    @Test
    public void shouldNotThrowWhenShowingMissesMini() {
        ConsoleView view = new ConsoleView(new MiniHangmanArt());
        view.showMisses(java.util.List.of('X', 'Z'));
    }

    @Test
    public void shouldNotThrowWhenShowingHangmanMini() {
        ConsoleView view = new ConsoleView(new MiniHangmanArt());
        view.showHangman(0);
    }

    @Test
    public void shouldNotThrowWhenShowingWinMini() {
        ConsoleView view = new ConsoleView(new MiniHangmanArt());
        view.showWin("TEST");
    }

    @Test
    public void shouldNotThrowWhenShowingLossMini() {
        ConsoleView view = new ConsoleView(new MiniHangmanArt());
        view.showLoss("TEST");
    }


    /**
     * GIFWINDOW TESTS
     */

    @Test
    public void shouldFindGifFile() {
        File file = new File("img/success.gif");
        assertTrue("Plik success.gif nie istnieje!", file.exists());
    }

    @Test
    public void shouldLoadGifIntoImageIcon() {
        ImageIcon icon = new ImageIcon("img/success.gif");
        assertNotNull(icon);
        assertTrue(icon.getIconWidth() > 0);
    }

    @Test
    public void shouldNotThrowWhenShowingGif() {
        try {
            GifWindow.showGif("img/success.gif");
        } catch (Exception e) {
            fail("showGif() nie powinno rzucać wyjątku: " + e.getMessage());
        }
    }


    /**
     * REMOTEWORDPROVIDER TESTS
     */

    @Test
    public void shouldConstructRemoteWordProvider() {
        RemoteWordProvider provider = new RemoteWordProvider(5, 10);
        assertNotNull(provider);
    }

    @Test
    @Ignore("Test zależy od API Wordnik i nie jest stabilny")
    public void shouldReturnNonEmptyUppercaseWordWhenApiWorks() {
        RemoteWordProvider provider = new RemoteWordProvider(5, 10);

        String word = provider.drawWord();
        assertNotNull(word);
        assertFalse(word.isBlank());
        assertEquals(word, word.toUpperCase());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowWhenRemoteProviderFails() {
        RemoteWordProvider provider = new RemoteWordProvider(1, 5) {
            @Override
            public String drawWord() {
                throw new RuntimeException("Nie udało się pobrać słowa.");
            }
        };
        provider.drawWord();
    }
}