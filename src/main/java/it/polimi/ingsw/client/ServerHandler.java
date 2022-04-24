package it.polimi.ingsw.client;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.messages.*;
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

    public void cloudChosenRequest(int cloudPosition){
        send(new CloudChosenRequest(cloudPosition));
    }

    public void moveMotherNatureRequest (int endPosition){
        send(new MoveMotherNatureRequest(endPosition));
    }

    public void moveStudentToIslandRequest(int islandPosition, PawnColor color){
        send(new MoveStudentToIslandRequest(islandPosition, color));
    }

    public void moveToDiningRoomRequest(PawnColor color){
        send(new MoveToDiningRoomRequest(color));
    }

    public void useAssistantRequest(int position){
        send(new UseAssistantRequest(position));
    }

    public void setPlayerInfo(String nickname){
        send(new PlayerSetUp(nickname));
    }

    public void initialSetUp(int numPlayer, boolean expert){
        send(new InitialSetUp(numPlayer, expert));
    }

    public void setPlayerColor(TowerColor color){
        send(new ColorSetUp(color));
    }
}
