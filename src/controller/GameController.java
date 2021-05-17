package controller;

import minesweeper.GamePanel;
import components.GridComponent;
import java.io.Serializable;
import minesweeper.MainFrame;
import entity.Player;
import minesweeper.ScoreBoard;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameController implements Serializable {

    private ArrayList<Player> players=new ArrayList<Player>();

    private Player onTurn;

    private GamePanel gamePanel;
    private ScoreBoard scoreBoard;
    private int usedStep=0;
    ArrayList<Player> players2=new ArrayList<>();

    private String id;
    private int numberOfPlayers;

    public GameController(ArrayList<Player> players,GamePanel gamePanel) {
        this.gamePanel=gamePanel;
        this.init( players);
        if (players.size()>0){
            this.onTurn = players.get(0);}
    }

    /**
     * 初始化游戏。在开始游戏前，应先调用此方法，给予游戏必要的参数。
     *

     */
    public void init(ArrayList<Player> players) {
        if (players.size()>=1){
            this.players.addAll(players);
        }

        //TODO: 在初始化游戏的时候，还需要做什么？
    }

    /**
     * 进行下一个回合时应调用本方法。
     * 在这里执行每个回合结束时需要进行的操作。
     * <p>
     * (目前这里没有每个玩家进行n回合的计数机制的，请自行修改完成哦~）
     */
    public void nextTurn() {
        usedStep++;
        //判断回合结束
        if (usedStep>=MainFrame.stepCount){
            players2=scoreOrder(players);
            //判断获胜 //非单2人提前获胜
            if (players.size()>1&&
                    players2.get(0).getScore()-players2.get(1).getScore()>gamePanel.getMineCount()-MainFrame.findedMine) {
                JLabel jLabel=new JLabel();
                jLabel.setSize(400,100);
                jLabel.setText(players2.get(0).getUserName()+" win"+", score and mis is "+players2.get(0).getScore()+" "+players2.get(0).getMistake());
                this.giveWinner().add(jLabel);
                jLabel.setLocation(100,100);
            }
            if (gamePanel.getMineCount()==MainFrame.findedMine){
                if (players2.get(0).getScore()>players2.get(1).getScore()){
                    //第一名获胜
                    JLabel jLabel=new JLabel();
                    jLabel.setSize(400,100);
                    jLabel.setText(players2.get(0).getUserName()+" win"+", score and mis is "+players2.get(0).getScore()+" "+players2.get(0).getMistake());
                    this.giveWinner().add(jLabel);
                    jLabel.setLocation(100,100);

                }
                else {
                    //找到所有相等,平局,比较mistake
                    JLabel jLabel=new JLabel();
                    int min=players2.get(0).getMistake();int people=0;
                    for (Player player : players) {
                        if (player.getScore() == players2.get(0).getScore() && player.getMistake() < players2.get(0).getMistake()) {
                            min=player.getMistake();
                        }
                    }
                    String s="";
                    for (Player player:players){
                        if (player.getScore()==players2.get(0).getScore()&&player.getMistake()==min){
                            s=s+player.getUserName()+" ";people++;
                        }
                    }
                    if (people!=1){
                        jLabel.setText(s+"\n"+"have equal max score and mis: "+players2.get(0).getScore()+" "+min);}
                    else {jLabel.setText(s+"win"+", score and mis "+players2.get(0).getScore()+" "+min);}
                    jLabel.setSize(400,100);
                    this.giveWinner().add(jLabel);
                    jLabel.setLocation(100,100);
                    //System.exit(0);
                }
            }
            //更改onTurn
            int a=players.indexOf(onTurn);
            if (a<players.size()-1){
                this.onTurn = players.get(a+1);}
            else {this.onTurn=players.get(0);}
           // System.out.println("Now it is " + onTurn.getUserName() + "'s turn.");


            usedStep=0;
            //System.out.println("Now it is " + onTurn.getUserName() + "'s turn.");

        }

        scoreBoard.update();
        //TODO: 在每个回合结束的时候，还需要做什么 (例如...检查游戏是否结束？)

    }
    public ArrayList<Player> scoreOrder(ArrayList<Player> players){
        ArrayList<Player> P1=new ArrayList<>();
        for (Player player:players){
            for (int i=0;i<=P1.size();i++){
                if (i==P1.size()||player.getScore()>P1.get(i).getScore()){
                    P1.add(i,player);break;
                }
            }
        }return P1;
    }


    /**
     * 获取正在进行当前回合的玩家。
     *
     * @return 正在进行当前回合的玩家
     */
    public Player getOnTurnPlayer() {
        return onTurn;
    }


    public Player onePlayer(Integer i) {
        return players.get(i-1);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public JFrame giveWinner(){
        JFrame jFrame=new JFrame();
        jFrame.setLayout(null);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("The Result of Game");
        jFrame.setSize(600,300);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        return jFrame;
    }
    public void setUsedStep(int usedStep){this.usedStep=usedStep;}
    public int getUsedStep(){return this.usedStep;}

    public void readFileData(String fileName) {
        //todo: read date from file

    }

    public void writeDataToFile(String fileName){
        //todo: write data into file
    }
}
