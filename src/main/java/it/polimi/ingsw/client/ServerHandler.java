package it.polimi.ingsw.client;

import it.polimi.ingsw.network.GenericMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;

public class ServerHandler {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean isConnected = false;
    public ServerHandler(){

    }
    public void initConnection(String serverIp){
        try{
            socket = new Socket(serverIp, Server.PORT);
            System.out.println("socket = " +  socket);
            isConnected = true;
            listen();
        }
        catch(IOException e){
        }


    }
    public void listen(){
        while (isConnected) {
            try {
                GenericMessage serverMessage = (GenericMessage) input.readObject();
                if (serverMessage.getType() == MessageType.ModelView) {
                }
            } catch (IOException e) {
                isConnected = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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
