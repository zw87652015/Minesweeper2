package minesweeper;

import components.GridComponent;
import controller.GameController;
import entity.Player;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

public class MainFrame extends JFrame {
    public static GameController controller;
    private int xCount;
    private int yCount;
    private int mineCount;
    private int numberOfPlayers;

    private JPanel jp;
    private JTextField selectLevel, selectNumberOfPlayers;
    private JTextField customizedX, customizedY, customizedMineCount, promptX, promptY, promtMineCount, acquiredData, promptNumberOfPlayers, customizedNumberOfPlayers;
    private ButtonGroup levelGroup, playersGroup;
    private JRadioButton basic, intermediate, advanced, customized, twoPlayers, customizedPlayers;
    private JButton startButton, loadButton, OK;

    private String id="";
    private int level=1;//1, 2, 3, 4 for basic, intermediate, advanced and customized, respectively.

    private class RadioButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource() == basic) {
                customizedX.setEditable(false);
                customizedY.setEditable(false);
                customizedMineCount.setEditable(false);
                startButton.setVisible(false);
                xCount=9;yCount=9;mineCount=10;level=1;
            }
            if(ae.getSource() == intermediate) {
                customizedX.setEditable(false);
                customizedY.setEditable(false);
                customizedMineCount.setEditable(false);
                startButton.setVisible(false);
                xCount=16;yCount=16;mineCount=40;level=2;
            }
            if(ae.getSource() == advanced) {
                customizedX.setEditable(false);
                customizedY.setEditable(false);
                customizedMineCount.setEditable(false);
                startButton.setVisible(false);
                xCount=16;yCount=30;mineCount=99;level=3;
            }
            if(ae.getSource() == customized) {
                customizedX.setEditable(true);
                customizedY.setEditable(true);
                customizedMineCount.setEditable(true);
                startButton.setVisible(false);
            }
            if(ae.getSource() == twoPlayers) {
                customizedNumberOfPlayers.setEditable(false);
                startButton.setVisible(false);
                numberOfPlayers=2;
            }
            if(ae.getSource() == customizedPlayers) {
                customizedNumberOfPlayers.setEditable(true);
                startButton.setVisible(false);
            }
            if(ae.getSource() == OK) {
                try {
                    if(customized.isSelected()) {
                        xCount=Integer.parseInt(customizedX.getText());
                        yCount=Integer.parseInt(customizedY.getText());
                        mineCount=Integer.parseInt(customizedMineCount.getText());
                    }
                    if(customizedPlayers.isSelected()) {
                        numberOfPlayers=Integer.parseInt(customizedNumberOfPlayers.getText());
                    }
                    if(xCount>24 || yCount>30 || mineCount>(xCount*yCount/2)) {
                        startButton.setVisible(false);
                        acquiredData.setText("The board is too large or mines are too many!");
                    } else {
                        startButton.setVisible(true);
                        acquiredData.setText("X:"+String.valueOf(xCount)+" Y:" + String.valueOf(yCount) + " Mine:" + String.valueOf(mineCount) + " Players:" + String.valueOf(numberOfPlayers));
                    }
                } catch (NumberFormatException e) {
                    acquiredData.setText("Please input numbers only!");
                    startButton.setVisible(false);
                }
                level=4;
            }

            if(ae.getSource() == startButton) {
                SwingUtilities.invokeLater(() -> {
                    dispose();

                    setTitle("Mine Sweeper");
                    setLayout(null);
                    setSize(yCount * GridComponent.gridSize + 20, xCount * GridComponent.gridSize + 200);
                    setLocationRelativeTo(null);

                    Player p1 = new Player();
                    Player p2 = new Player();

                    GameController controller = new GameController(p1, p2);
                    GamePanel gamePanel = new GamePanel(xCount, yCount, mineCount);
                    controller.setGamePanel(gamePanel);
                    ScoreBoard scoreBoard = new ScoreBoard(p1, p2, xCount, yCount);
                    controller.setScoreBoard(scoreBoard);

                    MainFrame frame = new MainFrame(xCount, yCount, mineCount);
                    frame.add(gamePanel);
                    frame.add(scoreBoard);


                    JButton clickBtn = new JButton("Click");
                    clickBtn.setSize(80, 20);
                    clickBtn.setLocation(5, gamePanel.getHeight() + scoreBoard.getHeight());
                    frame.add(clickBtn);
                    clickBtn.addActionListener(e -> {
                        String fileName = JOptionPane.showInputDialog(this, "input here");
                        System.out.println("fileName :"+fileName);

                        frame.setVisible(true);
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                        //            controller.readFileData(fileName);
                        //            controller.writeDataToFile(fileName);
                    });

                    setVisible(false);
                    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                });
            }
            if(ae.getSource() == loadButton) {
                /*
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showOpenDialog(new JLabel());
                File file = jfc.getSelectedFile();
                DuDang.load(file);
                */
            }
        }
    }

    public MainFrame(String frameName) {
        super("Mine Sweeper");

        this.xCount=9;
        this.yCount=9;
        this.mineCount=10;
        this.numberOfPlayers=2;

        RadioButtonListener listener = new RadioButtonListener();
        setSize(300,500);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        selectLevel = new JTextField("Please select game difficulty");
        selectLevel.setEditable(false);
        selectLevel.setHorizontalAlignment(JTextField.CENTER);
        selectLevel.setBounds(0,0,10,5);
        levelGroup = new ButtonGroup();
        basic = new JRadioButton("Basic", true);
        basic.setBounds(10,0,10,5);
        intermediate = new JRadioButton("Intermediate");
        intermediate.setBounds(10,10,10,5);
        advanced = new JRadioButton("Advances");
        advanced.setBounds(10,20,10,5);
        customized = new JRadioButton("Customized");
        customized.setBounds(10,30,10,5);
        promptX = new JTextField("Pleasu enter the length (number of blocks)");
        promptX.setEditable(false);
        promptX.setBounds(10,40,5,5);
        customizedX = new JTextField("9");
        customizedX.setEditable(false);
        customizedX.setBounds(20,40,10,5);
        promptY = new JTextField("Pleasu enter the height (number of blocks)");
        promptY.setEditable(false);
        promptY.setBounds(10,50,5,5);
        customizedY = new JTextField("9");
        customizedY.setEditable(false);
        customizedY.setBounds(20,50,10,5);
        promtMineCount= new JTextField("Pleasu enter the number of mines");
        promtMineCount.setEditable(false);
        promtMineCount.setBounds(10,60,5,5);
        customizedMineCount = new JTextField("10");
        customizedMineCount.setEditable(false);
        customizedMineCount.setBounds(20,60,10,5);
        promptNumberOfPlayers= new JTextField("Pleasu enter the number of players");
        promptNumberOfPlayers.setEditable(false);
        promptNumberOfPlayers.setBounds(10,70,5,5);

        selectNumberOfPlayers = new JTextField("Please select number of players");
        selectNumberOfPlayers.setEditable(false);
        selectNumberOfPlayers.setHorizontalAlignment(JTextField.CENTER);
        selectNumberOfPlayers.setBounds(0,80,10,5);
        playersGroup = new ButtonGroup();
        twoPlayers = new JRadioButton("2 Players", true);
        customizedPlayers = new JRadioButton("Customized number of players");
        customizedNumberOfPlayers = new JTextField("2");
        customizedNumberOfPlayers.setEditable(false);
        customizedNumberOfPlayers.setBounds(10,90,10,5);

        acquiredData = new JTextField("X:9 Y:9 Mine:10 Players:2");
        acquiredData.setEditable(false);
        acquiredData.setBounds(10,100,30,5);

        playersGroup.add(twoPlayers);
        playersGroup.add(customizedPlayers);
        levelGroup.add(basic);
        levelGroup.add(intermediate);
        levelGroup.add(advanced);
        levelGroup.add(customized);

        jp = new JPanel();
        jp.setLayout(new GridLayout(0,1));
        jp.add(selectLevel);
        jp.add(basic);
        jp.add(intermediate);
        jp.add(advanced);
        jp.add(customized);
        jp.add(promptX);
        jp.add(customizedX);
        jp.add(promptY);
        jp.add(customizedY);
        jp.add(promtMineCount);
        jp.add(customizedMineCount);
        jp.add(selectNumberOfPlayers);
        jp.add(twoPlayers);
        jp.add(customizedPlayers);
        jp.add(customizedNumberOfPlayers);
        jp.add(acquiredData);

        basic.addActionListener(listener);
        intermediate.addActionListener(listener);
        advanced.addActionListener(listener);
        customized.addActionListener(listener);
        twoPlayers.addActionListener(listener);
        customizedPlayers.addActionListener(listener);

        OK = new JButton("OK");
        jp.add(OK);
        OK.addActionListener(listener);
        startButton = new JButton("Start Game!");
        jp.add(startButton);
        startButton.addActionListener(listener);

        add(jp);
    }

    public MainFrame(int xCount, int yCount, int mineCount) {

        this.setTitle("2021 CS102A Project Demo 2");
        this.setLayout(null);
        this.setSize(yCount * GridComponent.gridSize + 20, xCount * GridComponent.gridSize + 200);
        this.setLocationRelativeTo(null);

        Player p1 = new Player();
        Player p2 = new Player();

        controller = new GameController(p1, p2);
        GamePanel gamePanel = new GamePanel(xCount, yCount, mineCount);
        controller.setGamePanel(gamePanel);
        ScoreBoard scoreBoard = new ScoreBoard(p1, p2, xCount, yCount);
        controller.setScoreBoard(scoreBoard);

        this.add(gamePanel);
        this.add(scoreBoard);


        JButton clickBtn = new JButton("Click");
        clickBtn.setSize(80, 20);
        clickBtn.setLocation(5, gamePanel.getHeight() + scoreBoard.getHeight());
        add(clickBtn);
        clickBtn.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "input here");
            System.out.println("fileName :"+fileName);

//            controller.readFileData(fileName);
//            controller.writeDataToFile(fileName);
        });

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
