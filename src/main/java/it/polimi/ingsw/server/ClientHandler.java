package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ACKControl;
import it.polimi.ingsw.client.ViewUtilities.GameInfo;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.service.ErrorMessage;
import it.polimi.ingsw.network.messages.service.InterruptedGameMessage;
import it.polimi.ingsw.network.messages.setUp.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;


public class ClientHandler extends Thread{
    private Socket socket;
    private String playerNickname;

    private LobbyHandler lobbyHandler;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private VirtualView virtualView;
    private boolean isConnected;
    private boolean firstPlayer;

    public ClientHandler(Socket socket, String playerNickname, LobbyHandler lobbyHandler){
        this.socket=socket;
        this.lobbyHandler = lobbyHandler;
        isConnected = true;
        this.playerNickname = playerNickname;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
        }
    }

    public void run(){
        listen();
    }
    public void listen(){
        playerSetUp(false);

        while (isConnected) {
            try {
                GenericMessage message = (GenericMessage) input.readObject();
                if (message.getType() == MessageType.Lobby){
                    LobbyMessage lobbyMessage = (LobbyMessage) message;
                    lobbyMessage.action(lobbyHandler, this);
                } else {
                    message.action(virtualView, playerNickname);
                }
            } catch (IOException | ClassNotFoundException e) {
                isConnected = false;
                System.out.println( getPlayerNickname() + " has disconnected");

                virtualView.printInterrupt(getPlayerNickname());
                endConnection();
            }
        }
    }

    public void send(GenericMessage message) {
        try{
            output.writeUnshared(message);
            output.flush();
            output.reset();
        }
        catch(IOException e){
            System.out.println("Errore2");
        }
    }

    public void gameSetUp(){
        send(new GameSetUp(lobbyHandler.getLobbies()));
    }

    public void gameSetUp(boolean fullGame){
        send(new GameSetUp(lobbyHandler.getLobbies(), fullGame));
    }

    public void initialSetUp(){
        send(new InitialSetUp(firstPlayer));
    }

    public void playerSetUp(boolean requestAgain){
        send(new PlayerSetUp(requestAgain));
    }

    public void colorSetUp(){
        List<TowerColor> towerColor;
        towerColor = virtualView.getAvailableColor();
        send(new ColorSetUp(towerColor));
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public void printGameBoard(GameInfo gameInfo){
        send(new GameInfoMessage(gameInfo));
    }

    public void printError(ClientException error){
        send (new ErrorMessage(error));
    }

    public void printInterrupt(String nickname, String player){
        send (new InterruptedGameMessage(nickname, player));
    }

    public void printWinner(String winner1, String winner2, String nickname){send(new WinnerMessage(winner1, winner2, nickname));}

    public void endConnection() {
        try {
            ACKControl.setSendACK();
            socket.close();
            isConnected = false;
        } catch (IOException e) {
        }
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void refreshLobbies(List<String[]> lobbies, boolean firstLobby) {
        send(new RefreshMessage(lobbies, firstLobby));
    }
}


