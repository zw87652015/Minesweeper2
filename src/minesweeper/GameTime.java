package minesweeper;

import components.GridComponent;
import entity.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GameTime extends JPanel {
    private Player player=new Player();
    JLabel jLabel=new JLabel();
    Timer t=new Timer();
    myTimer myTime=new myTimer();
    private int time=10;
    private String id;
    private boolean gameRun=true;


    public GameTime(int xCount, int yCount) {
        this.setSize(150, 75);
        this.setLocation(yCount* GridComponent.gridSize+10, 200);
        this.setVisible(true);
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.add(jLabel);

        t.schedule(myTime, 0,1000);

    }
    class myTimer extends TimerTask {
        @Override
        public void run() {
            if (gameRun){
            time--;}

            if (time<=0){MainFrame.controllerMap.get(id).setUsedStep(99);MainFrame.controllerMap.get(id).nextTurn();}
            jLabel.setText(player.getUserName()+":"+String.valueOf(time));
        }
    }

    public void uptime(){
        this.time=10;
    }
    public void setPlayer(Player player){this.player=player;}
    public void setId(String id){this.id=id;}
    public void  setGameRun(boolean gameRun){this.gameRun=gameRun;}

}