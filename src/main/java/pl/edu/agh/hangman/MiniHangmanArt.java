package pl.edu.agh.hangman;

/**
 * Mini wersja animacji.
 * Zawiera mniej klatek, mniejsza liczba prób w grze.
 */

public class MiniHangmanArt implements HangmanArt {

    private static final String[] FRAMES = new String[]{
            "X",
            "X O",
            "X O-",
            "X O-<",
            "X O-<-"
    };

    @Override
    public String[] getFrames() {
        return FRAMES;
    }
}
