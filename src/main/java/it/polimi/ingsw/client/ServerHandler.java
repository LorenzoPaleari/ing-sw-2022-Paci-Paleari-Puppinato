package it.polimi.ingsw.client;

import it.polimi.ingsw.network.*;
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
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
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
                GenericMessage message = (GenericMessage) input.readObject();
                if (message.getType() == MessageType.ModelView) {
                    ModelViewMessage mvMessage = (ModelViewMessage) message;
                }
                else if (message.getType() == MessageType.ControllerView) {
                    ControllerViewMessage cvMessage = (ControllerViewMessage) message;
                }
            } catch (IOException | ClassNotFoundException e) {
                isConnected = false;
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
