package components;

import entity.GridStatus;
import entity.Sounds;
import minesweeper.MainFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.util.Date;

import javax.swing.ImageIcon;

public class GridComponent extends BasicComponent {
    public static int gridSize = 30;

    private String id;
    public String getId() {
        return id;
    }
    public  void setId(String id){this.id=id;}

    private int row;
    private int col;
    private int clickNum=0;
    private GridStatus status = GridStatus.Covered;
    private int content = 0;
    Color color1=new Color(0,0,255,27);
    Color color2=new Color(255,222,23,27);

    ImageIcon flag = new ImageIcon("Materials\\Flag.png");
    ImageIcon boom = new ImageIcon("Materials\\Boom.png");


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
        Sounds.Music_dig();
        MainFrame.controllerMap.get(this.id).clickNum++;
        if (this.status == GridStatus.Covered) {
            this.status = GridStatus.Clicked;
            if (this.getContent()==0){
                MainFrame.controllerMap.get(this.id).getGamePanel().chainClick(this.row,this.col);
            }

            if (this.getContent()==-1&&MainFrame.controllerMap.get(this.id).clickNum==1){
                MainFrame.controllerMap.get(this.id).setUsedStep(MainFrame.controllerMap.get(this.id).getUsedStep()-1);
                MainFrame.controllerMap.get(this.id).getGamePanel().renewGamePanel(this.row,this.col);
                MainFrame.controllerMap.get(this.id).getGamePanel().getGrid(this.row,this.col).onMouseLeftClicked();
                MainFrame.controllerMap.get(this.id).clickNum=1;
            }
            else  if (this.getContent()==-1){
                MainFrame.controllerMap.get(this.id).getOnTurnPlayer().costScore();
                MainFrame.controllerMap.get(this.id).getOnTurnPlayer().addMistake();
                MainFrame.controllerMap.get(this.id).findedMine++;
            }repaint();
            if (!MainFrame.controllerMap.get(id).getGameOver()){
                MainFrame.controllerMap.get(id).nextTurn();}
        }

        //TODO: 在左键点击一个格子的时候，还需要做什么？
    }

    @Override
    public void onMouseRightClicked() {
        Sounds.Music_plant();
        MainFrame.controllerMap.get(this.id).clickNum++;;
        if (this.status == GridStatus.Covered) {
            this.status = GridStatus.Flag;
            repaint();
            if (this.getContent()!=-1){
                MainFrame.controllerMap.get(this.id).getOnTurnPlayer().addMistake();}
            if (this.getContent()==-1){
                MainFrame.controllerMap.get(this.id).findedMine++;
                MainFrame.controllerMap.get(this.id).getOnTurnPlayer().addScore();}
            if (!MainFrame.controllerMap.get(id).getGameOver()){
                MainFrame.controllerMap.get(id).nextTurn();}
        }
    }

    public void draw(Graphics g) {

        if (this.status == GridStatus.Covered&&!MainFrame.controllerMap.get(this.id).getWhetherCheat()) {
            g.setColor(new Color(175, 220, 202));
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        else if (this.status == GridStatus.Covered){
            g.setColor(Color.BLACK);
            if (content!=0){g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);}
            g.setColor(color1);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

        if (this.status == GridStatus.Clicked) {
            this.clickNum++;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.BLACK);
            if (content!=0){
                if(content==-1) {
                    if(this.clickNum==1) {
                        Sounds.Music_boom();
                    }
                    g.drawImage(boom.getImage(), -1, -1, Color.white, this);
                } else {
                    g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);
                }
            }
        }
        if (this.status == GridStatus.Flag&&!MainFrame.controllerMap.get(this.id).getWhetherCheat()) {

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.RED);
            if (this.getContent()==-1){
                g.drawImage(flag.getImage(), 5, 2, flag.getIconWidth(), flag.getIconHeight(), Color.LIGHT_GRAY, this);
            }
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
