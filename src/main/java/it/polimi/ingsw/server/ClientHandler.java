package it.polimi.ingsw.server;

import it.polimi.ingsw.client.viewUtilities.GameInfo;
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
    private final Socket socket;
    private String playerNickname;
    private final LobbyHandler lobbyHandler;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private VirtualView virtualView;
    private boolean isConnected;


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
                System.out.println(getPlayerNickname() + " has disconnected");
                if (isConnected){
                    isConnected = false;

                    lobbyHandler.terminateLobby(virtualView, this);
                    if (virtualView != null)
                        virtualView.printInterrupt(getPlayerNickname());
                }
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
            System.out.println(getPlayerNickname() + "disconnected during Message sending");
            isConnected = false;
            lobbyHandler.terminateLobby(virtualView, this);
            if (virtualView != null) {
                virtualView.printInterrupt(getPlayerNickname());
            }
            endConnection();
        }
    }

    public void gameSetUp(){
        send(new GameSetUp());
    }

    public void gameSetUp(boolean fullGame){
        send(new GameSetUp(lobbyHandler.getLobbies(), fullGame));
    }

    public void initialSetUp(){
        send(new InitialSetUp());
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

    public void printInterrupt(String nickname){
        synchronized (this) {
            send(new InterruptedGameMessage(nickname));
            if (!nickname.equals(""))
                endConnection();
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setDisconnected() {
        isConnected = false;
    }

    public void printWinner(String winner1, String winner2, String nickname){send(new WinnerMessage(winner1, winner2, nickname));}

    public void endConnection() {
        try {
            socket.close();
            isConnected = false;
        } catch (IOException e) {
        }
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public void refreshLobbies(List<String[]> lobbies, boolean firstLobby) {
        send(new RefreshMessage(lobbies, firstLobby));
    }
}


