package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ViewUtilities.GameInfo;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;


public class ClientHandler extends Thread{
    private Socket socket;
    private String playerNickname;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private VirtualView virtualView;
    private boolean isConnected = false;
    private boolean firstPlayer;

    public ClientHandler(Socket socket, boolean firstPlayer, VirtualView virtualView, String playerNickname){
        this.socket=socket;
        this.firstPlayer=firstPlayer;
        isConnected = true;
        this.virtualView=virtualView;
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
        if (firstPlayer) {
            initialSetUp();
        } else {
            playerSetUp(false);
        }

        while (isConnected) {
            try {
                GenericMessage message = (GenericMessage) input.readObject();
                if (message.getType() == MessageType.ViewController) {
                    ViewControllerMessage vcMessage = (ViewControllerMessage) message;
                    vcMessage.action(virtualView, playerNickname);
                }

            } catch (IOException | ClassNotFoundException e) {
                isConnected = false;
                System.out.print("Error");
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
            System.out.println("Errore");
        }
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

    public void printWinner(String winner1, String winner2, String nickname){send(new WinnerMessage(winner1, winner2, nickname));}
}


