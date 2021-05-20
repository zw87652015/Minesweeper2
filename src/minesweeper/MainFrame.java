package minesweeper;

import components.GridComponent;
import controller.GameController;
import entity.Player;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.*;

public class MainFrame extends JFrame {
    public static HashMap<String, GameController> controllerMap = new HashMap<>();
    public static int frameCount=0;
    private int xCount;
    private int yCount;
    private int mineCount;
    private int numberOfPlayers;
    private GameController controller;
    public static boolean whetherCheat=false;
    public static int findedMine=0;
    public static int clickNum=0;
    public static  int stepCount=2;
    private int level=1;//1, 2, 3, 4 for basic, intermediate, advanced and customized, respectively.

    private JPanel jp;
    private JTextField selectLevel, selectNumberOfPlayers;
    private JTextField customizedX, customizedY, customizedMineCount, promptX, promptY, promtMineCount, acquiredData, promptNumberOfPlayers, customizedNumberOfPlayers;
    private ButtonGroup levelGroup, playersGroup;
    private JRadioButton basic, intermediate, advanced, customized, twoPlayers, customizedPlayers;
    private JButton startButton, loadButton, OK;

    ImageIcon OKImage = new ImageIcon("E:\\Java\\Java Codes\\Project MineSweeper\\Minesweeper2\\src\\minesweeper\\Materials\\OK.png");
    ImageIcon StartImage = new ImageIcon("E:\\Java\\Java Codes\\Project MineSweeper\\Minesweeper2\\src\\minesweeper\\Materials\\Start.png");
    ImageIcon LoadImage = new ImageIcon("E:\\Java\\Java Codes\\Project MineSweeper\\Minesweeper2\\src\\minesweeper\\Materials\\Load.png");


    private class RadioButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource() == basic) {
                customizedX.setEditable(false);
                customizedY.setEditable(false);
                customizedMineCount.setEditable(false);
                customizedX.setText("9");
                customizedY.setText("9");
                customizedMineCount.setText("10");
                startButton.setVisible(false);
                xCount=9;yCount=9;mineCount=10;level=1;
            }
            if(ae.getSource() == intermediate) {
                customizedX.setEditable(false);
                customizedY.setEditable(false);
                customizedMineCount.setEditable(false);
                customizedX.setText("16");
                customizedY.setText("16");
                customizedMineCount.setText("40");
                startButton.setVisible(false);
                xCount=16;yCount=16;mineCount=40;level=2;
            }
            if(ae.getSource() == advanced) {
                customizedX.setEditable(false);
                customizedY.setEditable(false);
                customizedMineCount.setEditable(false);
                customizedX.setText("16");
                customizedY.setText("30");
                customizedMineCount.setText("99");
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
                        acquiredData.setText("将以 X:"+String.valueOf(xCount)+" Y:" + String.valueOf(yCount) + " Mine:" + String.valueOf(mineCount) + " Players:" + String.valueOf(numberOfPlayers) + "开始游戏");
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

                    ArrayList<Player> playersArray=new ArrayList<Player>();
                    for (int i=0;i<numberOfPlayers;i++){
                        Player p1=new Player();
                        if (!playersArray.contains(p1)) {playersArray.add(p1);}
                    }

                    GamePanel gamePanel = new GamePanel(xCount, yCount, mineCount);
                    controller = new GameController(playersArray,gamePanel);
                    MainFrame.controllerMap.put(controller.getId(), controller);
                    controller.setGamePanel(gamePanel);
                    ScoreBoard scoreBoard = new ScoreBoard(playersArray, xCount, yCount);
                    controller.setScoreBoard(scoreBoard);

                    MainFrame frame = new MainFrame(xCount, yCount, mineCount);
                    frame.add(gamePanel);
                    frame.add(scoreBoard);

                    JButton cheatButton = new JButton("Cheat"); //加入透视
                    cheatButton.setSize(80, 20);
                    cheatButton.setLocation(205, gamePanel.getHeight() + scoreBoard.getHeight());

                    add(cheatButton);
                    cheatButton.addActionListener( new listenCheat() );

                    JButton clickBtn = new JButton("Click");
                    clickBtn.setSize(80, 20);
                    clickBtn.setLocation(5, gamePanel.getHeight() + scoreBoard.getHeight());
                    frame.add(clickBtn);
                    clickBtn.addActionListener(e -> {
                        String fileName = JOptionPane.showInputDialog(this, "input here");

                        frame.setVisible(true);
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    });

                    setVisible(false);
                    if(frameCount==1) {
                        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    }
                });
            }

            if(ae.getSource() == loadButton) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showOpenDialog(new JLabel());
                File file = jfc.getSelectedFile();
                if(file!=null) {
                    dispose();
                    GameSaver.load(file);
                }
            }
        }
    }

    public MainFrame(String frameName) {
        super("Mine Sweeper");
        setBackground(new Color(226,226,226));

        this.xCount=9;
        this.yCount=9;
        this.mineCount=10;
        this.numberOfPlayers=2;

        RadioButtonListener listener = new RadioButtonListener();
        setSize(500,400);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        selectLevel = new JTextField("难度");
        selectLevel.setEditable(false);
        selectLevel.setHorizontalAlignment(JTextField.CENTER);
        selectLevel.setBounds(0,0,10,5);
        selectLevel.setBorder(new EmptyBorder(0,0,0,0));
        selectLevel.setForeground(new Color(66,65,66));
        selectLevel.setFont(new Font("叶根友毛笔行书简体-个人版",Font.PLAIN,30));
        levelGroup = new ButtonGroup();
        basic = new JRadioButton("初级", true);
        basic.setBounds(0,10,10,5);
        basic.setBorder(new EmptyBorder(0,0,0,0));
        basic.setHorizontalAlignment(JRadioButton.CENTER);
        intermediate = new JRadioButton("中级");
        intermediate.setBounds(10,10,10,5);
        intermediate.setBorder(new EmptyBorder(0,0,0,0));
        intermediate.setHorizontalAlignment(JRadioButton.CENTER);
        advanced = new JRadioButton("高级");
        advanced.setBounds(10,20,10,5);
        advanced.setBorder(new EmptyBorder(0,0,0,0));
        advanced.setHorizontalAlignment(JRadioButton.CENTER);
        customized = new JRadioButton("自定义");
        customized.setBounds(10,30,10,5);
        customized.setBorder(new EmptyBorder(0,0,0,0));
        customized.setHorizontalAlignment(JRadioButton.CENTER);
        promptX = new JTextField("棋盘长度");
        promptX.setEditable(false);
        promptX.setBounds(10,40,5,5);
        promptX.setBorder(new EmptyBorder(0,0,0,0));
        promptX.setHorizontalAlignment(JTextField.CENTER);
        customizedX = new JTextField("9");
        customizedX.setEditable(false);
        customizedX.setBounds(20,40,10,5);
        customizedX.setBorder(new EmptyBorder(0,0,0,0));
        promptY = new JTextField("棋盘宽度");
        promptY.setEditable(false);
        promptY.setBounds(10,50,5,5);
        promptY.setBorder(new EmptyBorder(0,0,0,0));
        promptY.setHorizontalAlignment(JTextField.CENTER);
        customizedY = new JTextField("9");
        customizedY.setEditable(false);
        customizedY.setBounds(20,50,10,5);
        customizedY.setBorder(new EmptyBorder(0,0,0,0));
        promtMineCount= new JTextField("雷数");
        promtMineCount.setEditable(false);
        promtMineCount.setBounds(10,60,5,5);
        promtMineCount.setBorder(new EmptyBorder(0,0,0,0));
        promtMineCount.setHorizontalAlignment(JTextField.CENTER);
        customizedMineCount = new JTextField("10");
        customizedMineCount.setEditable(false);
        customizedMineCount.setBounds(20,60,10,5);
        customizedMineCount.setBorder(new EmptyBorder(0,0,0,0));
        /*
        promptNumberOfPlayers= new JTextField("Please enter the number of players");
        promptNumberOfPlayers.setEditable(false);
        promptNumberOfPlayers.setBounds(10,70,5,5);
        */

        selectNumberOfPlayers = new JTextField("玩家数");
        selectNumberOfPlayers.setEditable(false);
        selectNumberOfPlayers.setHorizontalAlignment(JTextField.CENTER);
        selectNumberOfPlayers.setBounds(0,80,10,5);
        selectNumberOfPlayers.setBorder(new EmptyBorder(0,0,0,0));
        selectNumberOfPlayers.setHorizontalAlignment(JTextField.CENTER);
        selectNumberOfPlayers.setForeground(new Color(66,65,66));
        selectNumberOfPlayers.setFont(new Font("叶根友毛笔行书简体-个人版",Font.PLAIN,30));
        playersGroup = new ButtonGroup();
        twoPlayers = new JRadioButton("双人", true);
        twoPlayers.setHorizontalAlignment(JRadioButton.CENTER);
        customizedPlayers = new JRadioButton("自定义玩家数");
        customizedPlayers.setBorder(new EmptyBorder(0,0,0,0));
        customizedPlayers.setHorizontalAlignment(JRadioButton.CENTER);
        customizedNumberOfPlayers = new JTextField("2");
        customizedNumberOfPlayers.setEditable(false);
        customizedNumberOfPlayers.setBounds(10,90,10,5);
        customizedNumberOfPlayers.setBorder(new EmptyBorder(0,0,0,0));

        acquiredData = new JTextField("将以 X:9 Y:9 Mine:10 Players:2 开始游戏");
        acquiredData.setEditable(false);
        acquiredData.setBounds(10,100,30,5);
        acquiredData.setBorder(new EmptyBorder(0,0,0,0));
        acquiredData.setHorizontalAlignment(JTextField.CENTER);

        playersGroup.add(twoPlayers);
        playersGroup.add(customizedPlayers);
        levelGroup.add(basic);
        levelGroup.add(intermediate);
        levelGroup.add(advanced);
        levelGroup.add(customized);

        jp = new JPanel();
        jp.setLayout(new GridLayout(0,4));
        jp.add(selectLevel);
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
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
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(selectNumberOfPlayers);
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(twoPlayers);
        jp.add(customizedPlayers);
        jp.add(customizedNumberOfPlayers);
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(acquiredData);
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});
        jp.add(new JComponent(){{
            setBorder(new EmptyBorder(0,0,0,0));
        }});

        basic.addActionListener(listener);
        intermediate.addActionListener(listener);
        advanced.addActionListener(listener);
        customized.addActionListener(listener);
        twoPlayers.addActionListener(listener);
        customizedPlayers.addActionListener(listener);

        OK = new JButton(OKImage);
        OK.setContentAreaFilled(false);
        OK.setOpaque(false);
        OK.setBorder(new EmptyBorder(0,0,0,0));
        jp.add(OK);
        OK.addActionListener(listener);

        startButton = new JButton(StartImage);
        startButton.setContentAreaFilled(false);
        startButton.setOpaque(false);
        startButton.setBorder(new EmptyBorder(0,0,0,0));
        jp.add(startButton);
        startButton.addActionListener(listener);

        loadButton = new JButton(LoadImage);
        loadButton.setContentAreaFilled(false);
        loadButton.setOpaque(false);
        loadButton.setBorder(new EmptyBorder(0,0,0,0));
        jp.add(loadButton);
        loadButton.addActionListener(listener);

        add(jp);
    }

    public void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu_game = new JMenu("Game");
        menuBar.add(menu_game);

        JMenuItem menuItem_newGame = new JMenuItem("New game...");
        menuItem_newGame.addActionListener((e) -> {
            dispose();
            MainFrame.frameCount--;
            SwingUtilities.invokeLater(() -> {
                MainFrame starterFrame = new MainFrame("stargerFrame");
                starterFrame.setVisible(true);
            });
        });
        menu_game.add(menuItem_newGame);

        JMenuItem menuItem_load = new JMenuItem("Load...");
        menuItem_load.addActionListener((e) -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jfc.showOpenDialog(new JLabel());
            File file = jfc.getSelectedFile();
            if(file!=null) {
                GameSaver.load(file);
            }
        });
        menu_game.add(menuItem_load);

        JMenuItem menuItem_save = new JMenuItem("Save...");
        menuItem_save.addActionListener((e) -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showSaveDialog(new JLabel());
            File file = jfc.getSelectedFile();
            if(file!=null) {
                String fileName = JOptionPane.showInputDialog(this, "Please enter a save file name:");
                GameSaver.save(controller, file.getPath(), fileName);
            }
        });
        menu_game.add(menuItem_save);

        JMenuItem menuItem_endgame = new JMenuItem("Exit");
        menuItem_endgame.addActionListener((e) -> {
            System.exit(0);
        });
        menu_game.add(menuItem_endgame);
    }

    public MainFrame(int xCount, int yCount, int mineCount) {
        frameCount++;
        this.setLayout(null);
        this.setSize(yCount * GridComponent.gridSize + 20, xCount * GridComponent.gridSize + 200);
        this.setLocationRelativeTo(null);

        ArrayList<Player> playersArray2=new ArrayList<Player>();
        for (int i=0;i<numberOfPlayers;i++){
            Player p1=new Player();
            if (!playersArray2.contains(p1)) {playersArray2.add(p1);}
        }


        GamePanel gamePanel = new GamePanel(xCount, yCount, mineCount);
        controller = new GameController(playersArray2, gamePanel);
        this.setTitle("Mine Sweeper - ID: "+controller.getId());
        MainFrame.controllerMap.put(controller.getId(), controller);
        controller.setGamePanel(gamePanel);
        ScoreBoard scoreBoard = new ScoreBoard(playersArray2, xCount, yCount);
        controller.setScoreBoard(scoreBoard);

        this.addMenuBar();

        this.add(gamePanel);
        this.add(scoreBoard);

        JButton cheatButton = new JButton("Cheat"); //加入透视
        cheatButton.setSize(80, 20);
        cheatButton.setLocation(205, gamePanel.getHeight() + scoreBoard.getHeight());

        add(cheatButton);
        cheatButton.addActionListener( new listenCheat() );

        JButton clickBtn = new JButton("Click");
        clickBtn.setSize(80, 20);
        clickBtn.setLocation(5, gamePanel.getHeight() + scoreBoard.getHeight());
        add(clickBtn);
        clickBtn.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "input here");
        });

        this.setVisible(true);
        if(MainFrame.frameCount==1) {
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    public MainFrame(GameController controller) {
        frameCount++;
        this.setTitle("Mine Sweeper - ID: "+controller.getId());
        this.setLayout(null);
        this.setSize(controller.getGamePanel().getYCount() * GridComponent.gridSize + 20, controller.getGamePanel().getXCount() * GridComponent.gridSize + 200);
        this.setLocationRelativeTo(null);
        this.addMenuBar();
        MainFrame.controllerMap.put(controller.getId(), controller);
        for(GridComponent[] gcArray : controller.getGamePanel().getMineField()) {
            for(GridComponent gc : gcArray) {
                gc.addListener();
            }
        }

        this.controller=controller;
        this.add(controller.getGamePanel());
        this.add(controller.getScoreBoard());
    }
    public void setWhetherCheat(boolean whetherCheat){ MainFrame.whetherCheat =whetherCheat;}
    public static boolean getWhetherCheat(){return whetherCheat;}
    public  class listenCheat implements ActionListener {
        public void actionPerformed(ActionEvent e){
            whetherCheat= !whetherCheat;
            for (int i=0;i<xCount;i++){
                for (int j=0;j<yCount;j++){
                    controller.getGamePanel().getGrid(i,j).repaint();

                }
            }
        }
    }

    public void setxCount(int xCount){
        this.xCount= xCount;
    }


    public void setyCount(int yCount){
        this.yCount=yCount;
    }
    public void setMineCount(int mineCount){
        this.mineCount=mineCount;
    }
    public  int getxCount(){return xCount;}
    public  int getyCount(){return this.yCount;}
    public int getMineCount(){return this.mineCount;}
    public int getNumberOfPlayers(){return this.numberOfPlayers;}
    public GameController getController(){return this.controller;}
}
