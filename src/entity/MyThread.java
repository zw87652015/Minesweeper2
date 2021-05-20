package entity;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class MyThread extends Thread{
    private String path;

    public MyThread(String path) {
        this.path=path;
    }

    public void run() {
        try {
            AudioInputStream audioInputStream;
            AudioFormat audioFormat;
            SourceDataLine sourceDataLine;
            File file = new File(path);
            audioInputStream = AudioSystem.getAudioInputStream(file);
            audioFormat = audioInputStream.getFormat();
            if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    audioFormat.getSampleRate(), 16, audioFormat.getChannels(),
                    audioFormat.getChannels() * 2, audioFormat.getSampleRate(),false);
                audioInputStream = AudioSystem.getAudioInputStream(audioFormat,audioInputStream);
            }
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
            audioFormat, AudioSystem.NOT_SPECIFIED);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start(); 
            byte[] tempBuffer = new byte[320];
            int cnt;
            while ((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                if (cnt > 0) {
                    sourceDataLine.write(tempBuffer, 0, cnt);
                }
            }
            sourceDataLine.drain();
            sourceDataLine.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
}