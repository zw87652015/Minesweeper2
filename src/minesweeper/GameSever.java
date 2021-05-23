package minesweeper;

import entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameSever {
    private Player player1;
    ServerSocket serverSocket = null;
    Socket socket=null;
    BufferedReader is;
    PrintWriter os;

    public GameSever() throws IOException {
        serverSocket= new ServerSocket(4000);
        try {
            socket=serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
         is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
         os=new PrintWriter(socket.getOutputStream());
         




    }
}
