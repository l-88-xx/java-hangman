package pl.edu.agh.hangman;

import javax.swing.*;

public class GifWindow {

    public static void showGif(String path) {
        JFrame frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon icon = new ImageIcon(path);
        JLabel label = new JLabel(icon);

        frame.add(label);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
