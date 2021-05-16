package minesweeper;

import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import controller.GameController;

public class GameSaver {
    public static String save(GameController controller, String filePath, String fileName) {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath + "\\" + fileName));
            oos.writeObject(controller);
            oos.close();
            JOptionPane.showMessageDialog(null, "存档成功！");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "存档失败。");
        }
        return "Succeed!";
    }

    public static void load(File file) {
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            GameController temp = (GameController) ois.readObject();
            ois.close();
            SwingUtilities.invokeLater(() -> {
                MainFrame frame = new MainFrame(temp);
                frame.setVisible(true);
                if(MainFrame.frameCount==1) {
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                }
            });
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Failed to load.");
        }
    }
}
