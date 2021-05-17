package components;

import entity.GridStatus;
import minesweeper.MainFrame;
import controller.GameController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.util.Date;

public class GridComponent extends BasicComponent {
    public static int gridSize = 30;

    private String id="";
    public String getId() {
        return id;
    }

    private int row;
    private int col;
    private GridStatus status = GridStatus.Covered;
    private int content = 0;
    Color color1=new Color(0,0,255,27);
    Color color2=new Color(255,222,23,27);


    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton()==1){

                    onMouseLeftClicked();
                }
                if(e.getButton()==3){
                    onMouseRightClicked();
                }
            }
        });
    }

    public GridComponent(int x, int y) {
        this.id=new Date().toString();
        this.setSize(gridSize, gridSize);
        this.row = x;
        this.col = y;
    }

    @Override
    public void onMouseLeftClicked() {
        MainFrame.clickNum++;
        //System.out.printf("Gird (%d,%d) is left-clicked.\n", row, col);
        if (this.status == GridStatus.Covered) {
            this.status = GridStatus.Clicked;

            if (this.getContent()==-1&&MainFrame.clickNum==1){
                MainFrame.controller.setUsedStep(MainFrame.controller.getUsedStep()-1);
                MainFrame.controller.getGamePanel().renewGamePanel(this.row,this.col);
                MainFrame.controller.getGamePanel().getGrid(this.row,this.col).onMouseLeftClicked();
                MainFrame.clickNum=1;
            }
            else  if (this.getContent()==-1){
                MainFrame.controller.getOnTurnPlayer().costScore();
                MainFrame.controller.getOnTurnPlayer().addMistake();
                MainFrame.findedMine++;
            }repaint();
            MainFrame.controllerMap.get(id).nextTurn();
        }

        //TODO: 在左键点击一个格子的时候，还需要做什么？
    }

    @Override
    public void onMouseRightClicked() {
        MainFrame.clickNum++;
        //System.out.printf("Gird (%d,%d) is right-clicked.\n", row, col);
        if (this.status == GridStatus.Covered) {
            this.status = GridStatus.Flag;
            repaint();
            if (this.getContent()!=-1){
                MainFrame.controller.getOnTurnPlayer().addMistake();}
            if (this.getContent()==-1){
                MainFrame.findedMine++;
                MainFrame.controller.getOnTurnPlayer().addScore();}
            MainFrame.controllerMap.get(id).nextTurn();

        }

        //TODO: 在右键点击一个格子的时候，还需要做什么？
    }

    public void draw(Graphics g) {

        if (this.status == GridStatus.Covered&&!MainFrame.whetherCheat) {
            g.setColor(Color.CYAN);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        else if (this.status == GridStatus.Covered){
            g.setColor(Color.BLACK);
            if (content!=0){g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);}
            g.setColor(color1);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

        if (this.status == GridStatus.Clicked) {

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.BLACK);
            if (content!=0){g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);}
        }
        if (this.status == GridStatus.Flag&&!MainFrame.whetherCheat) {

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.RED);
            if (this.getContent()==-1){
                g.drawString("F", getWidth() / 2 - 5, getHeight() / 2 + 5);}
            else {this.status=GridStatus.Clicked;repaint();}

        }
        else if (this.status == GridStatus.Flag){
            g.setColor(Color.BLACK);
            if (content!=0){g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);}
            g.setColor(color2);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.RED);
            if (this.getContent()==-1){
                g.drawString("F", getWidth() / 2 - 5, getHeight() / 2 + 5);}
            else {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
                g.setColor(Color.BLACK);
                if (content!=0){g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);}
                g.setColor(Color.BLACK);g.fillRect(0,0,getWidth()-28,getHeight()-28);
            }
        }
    }

    public void setContent(int content) {
        this.content = content;
    }
    public int getContent(){return this.content;}
    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        draw(g);
    }
    public GridStatus getStatus(){
        return  this.status;
    }
}
