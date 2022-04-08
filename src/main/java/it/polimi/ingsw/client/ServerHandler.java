package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerHandler {
    private Socket socket;
    private boolean isConnected = false;
    public ServerHandler(){

    }
    public void initConnection(String serverIp){
        try{
            socket = new Socket(serverIp, Server.PORT);
            System.out.println("socket = " +  socket);
            isConnected = true;
            listen();
            endConnection();
        }
        catch(IOException e){
        }


    }
    public void listen(){
        while (isConnected) {
            try {
                BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()));
                PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter( socket.getOutputStream())),true);
                String str = in.readLine();

            } catch (IOException e) {
                isConnected = false;
            }
        }
    }
    public void endConnection() {
        try {
            socket.close();
            isConnected = false;
        } catch (IOException e) {

        }
    }
}
