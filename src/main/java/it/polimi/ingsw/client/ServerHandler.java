package it.polimi.ingsw.client;

import it.polimi.ingsw.network.*;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;

public class ServerHandler {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private View view;
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
                    mvMessage.action(view);
                }
                else if (message.getType() == MessageType.ControllerView) {
                    ControllerViewMessage cvMessage = (ControllerViewMessage) message;
                    cvMessage.action(view);
                }
            } catch (IOException | ClassNotFoundException e) {
                isConnected = false;
            }
        }
    }

    public void setView(View view){
        this.view = view;

    }

    public void endConnection() {
        try {
            socket.close();
            isConnected = false;
        } catch (IOException e) {

        }


    }
    public void send(GenericMessage message) {
        try{
            output.writeUnshared(message);
            output.flush();
            output.reset();
        }
        catch(IOException e){
            System.out.println("Errore");
        }
    }


}
