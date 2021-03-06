package minesweeper;

import components.GridComponent;
import entity.GridStatus;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Random;


public class GamePanel extends JPanel implements Serializable{
    private GridComponent[][] mineField;
    private int[][] chessboard;
    private final Random random = new Random();
    private int xCount, yCount, mineCount;

    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getXCount() {
        return xCount;
    }
    public int getYCount() {
        return yCount;
    }
    public GridComponent[][] getMineField() {
        return mineField;
    }
    private static Random ran = new Random();

    /**
     * 初始化一个具有指定行列数格子、并埋放了指定雷数的雷区。
     *
     * @param xCount    count of grid in column
     * @param yCount    count of grid in row
     * @param mineCount mine count
     */
    public GamePanel(int xCount, int yCount, int mineCount) {
        this.id="PanelId"+ (ran.nextInt(9000)+1000);
        System.out.println(this.id);
        this.xCount=xCount;
        this.yCount=yCount;
        this.mineCount=mineCount;
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(GridComponent.gridSize * yCount, GridComponent.gridSize * xCount);
        getInitChessBoard(xCount,yCount);
        initialGame(xCount, yCount, mineCount);
        this.mineCount=mineCount;this.xCount=xCount;this.yCount=yCount;
        repaint();
    }

    public void initialGame(int xCount, int yCount, int mineCount) {
        mineField = new GridComponent[xCount][yCount];
        generateChessBoard(xCount, yCount, mineCount);

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                GridComponent gridComponent = new GridComponent(i, j);
                gridComponent.setId(this.id);
                gridComponent.setContent(chessboard[i][j]);
                gridComponent.setLocation(j * GridComponent.gridSize, i * GridComponent.gridSize);
                mineField[i][j] = gridComponent;
                this.add(mineField[i][j]);
            }
        }
    }


    public void generateChessBoard(int xCount, int yCount, int mineCount) {
        int[][] chessboard2=new int[xCount+2][yCount+2];
        int i=0;int x,y;
        while(i<mineCount){
            x=random.nextInt(xCount)+1;
            y=random.nextInt(yCount)+1;
            if (chessboard2[x][y]==0&&chessboard[x-1][y-1]!=1){chessboard2[x][y]=-1;i++;}
        }
        for (int m=1;m<xCount+1;m++){
            for (int n=1;n<yCount+1;n++){
                if (chessboard2[m][n]!=-1){chessboard2[m][n]=getMineAround(chessboard2,m,n);}
                else {if (getMineAround(chessboard2,m,n)==8){generateChessBoard(xCount,yCount,mineCount);}}
            }
        }
        for (int m=0;m<xCount;m++){
            for (int n=0;n<yCount;n++){
                chessboard[m][n]=chessboard2[m+1][n+1];
            }
        }
    }
        public void renewGamePanel(int x,int y){
            this.removeAll();
            getInitChessBoard(xCount,yCount);
            changeValue(x,y);
            initialGame(xCount,yCount,mineCount);
            repaint();
        }
        public void getInitChessBoard(int a, int b){
            chessboard=new int[a][b];
        }
        public void changeValue(int x,int y){
            chessboard[x][y]=1;
        }
        public static int getMineAround(int[][]chessboard, int m,int n){
            int i=0;
            if (chessboard[m-1][n]==-1){i++;}
            if (chessboard[m-1][n-1]==-1){i++;}
            if (chessboard[m-1][n+1]==-1){i++;}
            if (chessboard[m][n-1]==-1){i++;}
            if (chessboard[m][n+1]==-1){i++;}
            if (chessboard[m+1][n]==-1){i++;}
            if (chessboard[m+1][n-1]==-1){i++;}
            if (chessboard[m+1][n+1]==-1){i++;}
            return i;

        }
        public int getxCount(){return xCount;}
        public int getyCount(){return yCount;}
        public int getMineCount(){return mineCount;}






    /**
     * 获取一个指定坐标的格子。
     * 注意请不要给一个棋盘之外的坐标哦~
     *
     * @param x 第x列
     * @param y 第y行
     * @return 该坐标的格子
     */
    public GridComponent getGrid(int x, int y) {
        try {
            return mineField[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void chainClick(int row, int col){
        if (col!=0&&this.getGrid(row,col-1).getStatus()==GridStatus.Covered){
            MainFrame.controllerMap.get(this.id).setUsedStep(MainFrame.controllerMap.get(this.id).getUsedStep()-1);
            this.getGrid(row,col-1).onMouseLeftClicked();}
        if (col!=yCount-1&&this.getGrid(row,col+1).getStatus()==GridStatus.Covered){
            MainFrame.controllerMap.get(this.id).setUsedStep(MainFrame.controllerMap.get(this.id).getUsedStep()-1);
            this.getGrid(row,col+1).onMouseLeftClicked();}
        if (row!=0&&this.getGrid(row-1,col).getStatus()==GridStatus.Covered){
            MainFrame.controllerMap.get(this.id).setUsedStep(MainFrame.controllerMap.get(this.id).getUsedStep()-1);
            this.getGrid(row-1,col).onMouseLeftClicked();}
        if (row!=xCount-1&&this.getGrid(row+1,col).getStatus()==GridStatus.Covered){
            MainFrame.controllerMap.get(this.id).setUsedStep(MainFrame.controllerMap.get(this.id).getUsedStep()-1);
            this.getGrid(row+1,col).onMouseLeftClicked();}
        if (col!=0&&row!=0&&this.getGrid(row-1,col-1).getStatus()==GridStatus.Covered){
            MainFrame.controllerMap.get(this.id).setUsedStep(MainFrame.controllerMap.get(this.id).getUsedStep()-1);
            this.getGrid(row-1,col-1).onMouseLeftClicked();}
        if (col!=0&&row!=xCount-1&&this.getGrid(row+1,col-1).getStatus()==GridStatus.Covered){
            MainFrame.controllerMap.get(this.id).setUsedStep(MainFrame.controllerMap.get(this.id).getUsedStep()-1);
            this.getGrid(row+1,col-1).onMouseLeftClicked();}
        if (col!=yCount-1&&row!=0&&this.getGrid(row-1,col+1).getStatus()==GridStatus.Covered){
            MainFrame.controllerMap.get(this.id).setUsedStep(MainFrame.controllerMap.get(this.id).getUsedStep()-1);
            this.getGrid(row-1,col+1).onMouseLeftClicked();}
        if (col!=yCount-1&&row!=xCount-1&&this.getGrid(row+1,col+1).getStatus()==GridStatus.Covered){
            MainFrame.controllerMap.get(this.id).setUsedStep(MainFrame.controllerMap.get(this.id).getUsedStep()-1);
            this.getGrid(row+1,col+1).onMouseLeftClicked();}

    }
    public void getAOperation(){
        int x=random.nextInt(xCount-2)+1;
        int y=random.nextInt(yCount-2)+1;
        int coveredNumber=0;int coveredMine=0;int xMine=0,yMine=0;
        for (int i=x-1;i<=x+1;i++){
            for (int j=y-1;j<=y+1;j++){
                if (this.getGrid(i,j).getStatus()==GridStatus.Covered&&this.getGrid(i,j).getContent()==-1){
                    coveredMine++;xMine=i;yMine=j;coveredNumber++;
                }
                else if (this.getGrid(i,j).getStatus()==GridStatus.Covered){coveredNumber++;}

            }
        }
        if (coveredNumber==0){getAOperation();}
        else {
            if (coveredNumber>=5){if (coveredMine<=2){this.getUnCovered(x-1,y-1).onMouseLeftClicked();}
                                   else {this.getUnCovered(x-1,y-1).onMouseRightClicked();}}
            else if (coveredMine>=1){this.getGrid(xMine,yMine).onMouseRightClicked();}
            else {this.getUnCovered(x-1,y-1).onMouseLeftClicked();}
        }
    }
    public GridComponent getUnCovered(int x,int y){
        int i=random.nextInt(3);
        int j=random.nextInt(3);
        if (this.getGrid(x+i,y+j).getStatus()==GridStatus.Covered){
            return this.getGrid(x+i,y+j);
        }
        else {return this.getUnCovered(x,y);}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public int[][] getChessboard() {
        return chessboard;
    }
}
