package minesweeper;

import components.GridComponent;
import entity.Player;
import java.awt.*;
import java.util.ArrayList;
import java.io.Serializable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * 此类的对象是一个计分板容器，通过传入玩家对象，
 * 可以用update()方法实时更新玩家的分数以及失误数。
 */
public class ScoreBoard extends JPanel implements Serializable{

    ArrayList<Player> players=new ArrayList<Player>();
    ArrayList<JLabel> labels =new ArrayList<JLabel>();

    /**
     * 通过进行游戏的玩家来初始化计分板。这里只考虑了两个玩家的情况。
     * 如果想要2-4人游戏甚至更多，请自行修改(建议把所有玩家存在ArrayList)~
     *
     */
    public ScoreBoard(ArrayList<Player> players, int xCount, int yCount) {
        this.setSize(400, players.size()*40);
        this.setLocation(yCount * GridComponent.gridSize+10, 0);
        for (int i=0;i<players.size();i++){
            this.labels.add(new JLabel());
        }
        this.players.addAll(players);
        for (int i =0;i<players.size();i++){
            this.add(labels.get(i));
        }

        this.setLayout(new BoxLayout(this, 1));
        update();
    }

    /**
     * 刷新计分板的数据。
     * 计分板会自动重新获取玩家的分数，并更新显示。
     */
    public void update() {
        for (int i=0;i<labels.size();i++){
            labels.get(i).setText(String.format("%s: 总得分 %d 分，%d 次错误",
                    players.get(i).getUserName(), players.get(i).getScore(), players.get(i).getMistake()));
            labels.get(i).setFont(MainFrame.MyFont(Font.PLAIN, 15));
            labels.get(i).setForeground(new Color(232, 105, 74));
        }
    }

}
