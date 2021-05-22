package entity;

public class Sounds {
    private static boolean isStopped=false;
    private static MyThread bgmThread = new MyThread("Materials\\bgm.wav");
    public static boolean isSfxOn = true;

    public static void Music_bgmOn() {
        if(isStopped) {
            bgmThread = new MyThread("Materials\\bgm.wav");
            bgmThread.start();
        } else {
            bgmThread.start();
        }
    }

    public static void Music_boom() {
        if(isSfxOn) {
            MyThread t = new MyThread("Materials\\boom.wav");
            t.start();
        }
    }

    public static void Music_bgmOff() {
        isStopped=true;
        bgmThread.stop();
    }

    public static void Music_dig() {
        if(isSfxOn) {
            MyThread t = new MyThread("Materials\\dig.wav");
            t.start();
        }
    }

    public static void Music_plant() {
        if(isSfxOn) {
            MyThread t = new MyThread("Materials\\plantflag.wav");
            t.start();
        }
    }

    public static void Music_button() {
        MyThread t = new MyThread("Materials\\button.wav");
        t.start();
    }
}