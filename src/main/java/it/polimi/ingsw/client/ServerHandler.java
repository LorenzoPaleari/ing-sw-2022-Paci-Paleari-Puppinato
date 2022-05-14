package it.polimi.ingsw.client;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.service.InterruptedGameMessage;
import it.polimi.ingsw.network.messages.setUp.*;
import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements NetworkHandler {
    private Socket socket;
    private GameInfoMessage currentMessage;
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
            isConnected = true;
            socket.setSoTimeout(25000);
            ACKControl ACKcontrol= new ACKControl(this, false);
            ACKcontrol.start();
            listen();
        }
        catch(IOException e){
            System.out.print("Connection refused, server may be offline");
        }


    }

    public void listen(){
        while (isConnected) {
            try {
                GenericMessage message = (GenericMessage) input.readObject();
                if(message instanceof InterruptedGameMessage && message.getType().equals(MessageType.ModelView)) {
                    isConnected = false;
                    endConnection();
                }

                if (message instanceof GameInfoMessage){
                    if (currentMessage != null && currentMessage.thread != null) {
                        Thread.sleep(500);
                        view.stopClearer();
                        currentMessage.thread.interrupt();
                    }
                    currentMessage = (GameInfoMessage) message;
                }

                message.action(view);
            } catch (IOException | ClassNotFoundException e) {
                if (isConnected) {
                    isConnected = false;
                    view.printServerDown();
                }

                endConnection();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean connectionAlive() {
        return isConnected;
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
        if(isConnected) {
            try {
                output.writeUnshared(message);
                output.flush();
                output.reset();
            } catch (IOException e) {
                if (isConnected) {
                    isConnected = false;
                    view.printServerDown();
                }

                endConnection();
            }
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

    public void useCharacterRequest(int characterPosition){send(new UseCharacterRequest(characterPosition));}

    public void useCharacterRequest(int islandPosition, int characterPosition){send(new UseCharacterRequest(islandPosition, characterPosition));}

    public void useCharacterRequest(int islandPosition, PawnColor color, int characterPosition){send(new UseCharacterRequest(islandPosition, color,characterPosition));}

    public void useCharacterRequest(PawnColor color, int characterPosition){send(new UseCharacterRequest(color, characterPosition));}

    public void useCharacterRequest(int[] color, int characterPosition){send(new UseCharacterRequest(color, characterPosition));}

    public void setGame(boolean newGame, int lobby){
        send(new GameSetUp(newGame, lobby));
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

    public void refreshLobbies(){
        send(new RefreshMessage());
    }

}
