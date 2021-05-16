import minesweeper.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            MainFrame mainFrame = new MainFrame("StarterFrame");
            mainFrame.setVisible(true);
        });
    }
}
