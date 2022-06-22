package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ACKControl;
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

/**
 * communicates with the serverHandler
 */
public class ClientHandler extends Thread implements NetworkHandler{
    private final Socket socket;
    private String playerNickname;
    private final LobbyHandler lobbyHandler;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private VirtualView virtualView;
    private boolean isConnected;

    /**
     * Constructor
     * Sets the socket, the lobby handler, the player nickname and the ack control
     * @param socket the socket
     * @param playerNickname the player nickname
     * @param lobbyHandler the lobby handler
     */
    public ClientHandler(Socket socket, String playerNickname, LobbyHandler lobbyHandler){
        this.socket=socket;
        this.lobbyHandler = lobbyHandler;
        isConnected = true;
        this.playerNickname = playerNickname;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            socket.setSoTimeout(25000);
        } catch (IOException e) {
        }

        ACKControl ACKcontrol= new ACKControl(this, true);
        ACKcontrol.start();
    }

    /**
     * starts listening
     */
    public void run(){
        listen();
    }

    /**
     * listens and prints status messages
     */
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

    /**
     * sends message
     * @param message the message to be sent
     */
    public synchronized void send(GenericMessage message) {
        if(isConnected){
            try {
                output.writeUnshared(message);
                output.flush();
                output.reset();
            }
            catch(IOException e){
                System.out.println(getPlayerNickname() + "disconnected during Message sending");
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

    /**
     * sends gamesetup message
     */
    public void gameSetUp(){
        send(new GameSetUp());
    }

    /**
     *
     * @param fullGame
     */
    public void gameSetUp(boolean fullGame){
        send(new GameSetUp(fullGame));
    }

    /**
     *
     */
    public void initialSetUp(){
        send(new InitialSetUp());
    }

    /**
     *
     * @param requestAgain
     */
    public void playerSetUp(boolean requestAgain){
        send(new PlayerSetUp(requestAgain));
    }

    /**
     * sets the tower color
     * @param requestAgain
     */
    public void colorSetUp(boolean requestAgain){
        List<TowerColor> towerColor;
        towerColor = virtualView.getAvailableColor();
        send(new ColorSetUp(towerColor, requestAgain));
    }

    /**
     * gets player nickname
     * @return
     */
    public String getPlayerNickname() {
        return playerNickname;
    }

    /**
     * sets player nickname
     * @param playerNickname
     */
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    /**
     *
     * @param gameInfo
     */
    public void printGameBoard(GameInfo gameInfo){
        send(new GameInfoMessage(gameInfo));
    }

    /**
     *
     * @param error
     */
    public void printError(ClientException error){
        send (new ErrorMessage(error));
    }

    /**
     *
     * @param nickname
     * @param notEntered
     */
    public void printInterrupt(String nickname, boolean notEntered){
        if (notEntered)
            send(new InterruptedGameMessage(nickname, true));
        else
            send(new InterruptedGameMessage(nickname));
    }

    /**
     *
     * @return
     */
    public boolean connectionAlive() {
        return isConnected;
    }

    /**
     *
     */
    public void setDisconnected() {
        isConnected = false;
        lobbyHandler.terminateLobby(virtualView, this);
        endConnection();
    }

    /**
     *
     * @param winner1
     * @param winner2
     * @param nickname
     */
    public void printWinner(String winner1, String winner2, String nickname){
        send(new WinnerMessage(winner1, winner2, nickname));
    }

    /**
     * closes the connection
     */
    public void endConnection() {
        try {
            socket.close();
            isConnected = false;
        } catch (IOException e) {
        }
    }

    /**
     * sets the virtual view
     * @param virtualView
     */
    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    /**
     *
     * @param lobbies
     * @param firstLobby
     */
    public void refreshLobbies(List<String[]> lobbies, boolean firstLobby) {
        send(new RefreshMessage(lobbies, firstLobby));
    }
}


