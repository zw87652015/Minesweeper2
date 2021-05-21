package entity;

public class Sounds {
    private static boolean isStopped=false;
    private static MyThread bgmThread = new MyThread("out\\production\\MineSweeper-Demo\\minesweeper\\Materials\\bgm.wav");
    public static boolean isSfxOn = true;

    public static void Music_bgmOn() {
        if(isStopped) {
            bgmThread = new MyThread("out\\production\\MineSweeper-Demo\\minesweeper\\Materials\\bgm.wav");
            bgmThread.start();
        } else {
            bgmThread.start();
        }
    }

    public static void Music_bgmOff() {
        isStopped=true;
        bgmThread.stop();
    }

    public static void Music_dig() {
        if(isSfxOn) {
            MyThread t = new MyThread("out\\production\\MineSweeper-Demo\\minesweeper\\Materials\\dig.wav");
            t.start();
        }
    }

    public static void Music_plant() {
        if(isSfxOn) {
            MyThread t = new MyThread("out\\production\\MineSweeper-Demo\\minesweeper\\Materials\\plantflag.wav");
            t.start();
        }
    }

    public static void Music_button() {
        MyThread t = new MyThread("out\\production\\MineSweeper-Demo\\minesweeper\\Materials\\button.wav");
        t.start();
    }
}