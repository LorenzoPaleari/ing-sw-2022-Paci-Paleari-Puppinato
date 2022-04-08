package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerHandler {
    private Socket socket;
    public ServerHandler(){

    }
    public void initConnection(String serverIp){
        try{
            Socket socket = new Socket(serverIp, Server.PORT);
            System.out.println("socket = " +  socket);
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()));
            PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter( socket.getOutputStream())),true);
            String str = in.readLine();
            socket.close();
        }
        catch(IOException e){

        }

    }
}
