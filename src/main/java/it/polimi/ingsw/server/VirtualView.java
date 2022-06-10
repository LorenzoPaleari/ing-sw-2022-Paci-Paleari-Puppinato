package it.polimi.ingsw.server;

import it.polimi.ingsw.client.viewUtilities.GameInfo;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

/**
 * virtual view class
 */
public class VirtualView {
    private Controller controller;
    private List<ClientHandler> clientHandlers;

    /**
     * Constructor
     * Initialize the controller and the client handlers list
     * @param controller
     */
    public VirtualView(Controller controller){
        this.controller=controller;
        clientHandlers= new LinkedList<>();
    }

    /**
     * returns the controller
     * @return
     */
    public Controller getController() {return controller;}

    /**
     * adds a client handler to the list client handlers
     * @param clientHandler
     */
    public void addClientHandler(ClientHandler clientHandler){
        clientHandlers.add(clientHandler);
    }

    /**
     * gets the available colors for the towers
     * @return
     */
    public List<TowerColor> getAvailableColor(){
        return controller.getAvailableColor();
    }

    /**
     *
     * @param cloudPosition
     * @param playerNickname
     */
    public void cloudChosen(int cloudPosition, String playerNickname){
        controller.chooseCloud(controller.getPlayerByNickname(playerNickname),cloudPosition);
    }

    /**
     *
     * @param endPosition
     * @param playerNickname
     */
    public void moveMotherNature(int endPosition, String playerNickname){
        controller.moveMotherNature(controller.getPlayerByNickname(playerNickname),endPosition);
    }

    /**
     *
     * @param islandPosition
     * @param color
     * @param playerNickname
     */
    public void moveStudentToIsland(int islandPosition, PawnColor color, String playerNickname){
        controller.useStudentIsland(controller.getPlayerByNickname(playerNickname), color, islandPosition);
    }

    /**
     *
     * @param color
     * @param playerNickname
     */
    public void moveStudentToDining(PawnColor color, String playerNickname){
        controller.useStudentDining(controller.getPlayerByNickname(playerNickname), color);
    }

    /**
     *
     * @param position
     * @param playerNickname
     */
    public void useAssistant(int position, String playerNickname){
        controller.useAssistant(position, controller.getPlayerByNickname(playerNickname));
    }

    /**
     *
     * @param colors
     * @param characterPosition
     * @param playerNickname
     */
    public void useCharacter(int[] colors, int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition, colors);
    }

    /**
     *
     * @param islandPosition
     * @param color
     * @param characterPosition
     * @param playerNickname
     */
    public void useCharacter(int islandPosition, PawnColor color , int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition, islandPosition, color);
    }

    /**
     *
     * @param color
     * @param characterPosition
     * @param playerNickname
     */
    public void useCharacter(PawnColor color, int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition, color);
    }

    /**
     *
     * @param islandPosition
     * @param characterPosition
     * @param playerNickname
     */
    public void useCharacter(int islandPosition, int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition, islandPosition);
    }

    /**
     *
     * @param characterPosition
     * @param playerNickname
     */
    public void useCharacter(int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition);
    }

    /**
     *
     * @param numPlayer
     * @param expert
     * @param playerNickname
     */
    public void setUpGameInfo(int numPlayer, boolean expert, String playerNickname){
        controller.setExpertMode(expert);
        controller.setNumPlayer(numPlayer);

        colorSetUp(playerNickname);
    }

    /**
     *
     * @param nickname
     */
    public void colorSetUp(String nickname){
        getClientHandlerByNickname(nickname).colorSetUp(false);
    }

    /**
     *
     * @param color
     * @param playerNickname
     */
    public synchronized void setUpPlayerColor(TowerColor color, String playerNickname){
        if(getAvailableColor().contains(color))
            controller.addPlayer(new Player(playerNickname, color));
        else
            getClientHandlerByNickname(playerNickname).colorSetUp(true);
    }

    /**
     *
     * @param nickname
     * @return
     */
    public ClientHandler getClientHandlerByNickname(String nickname){
        for (ClientHandler cl: clientHandlers){
            if(nickname.equals(cl.getPlayerNickname()))
                return cl;
        }
        return null;
    }

    /**
     *
     * @param game
     */
    public void printGameBoard(Game game){
        for (ClientHandler c : clientHandlers)
            c.printGameBoard(new GameInfo(game, c.getPlayerNickname()));
    }

    /**
     *
     * @param exception
     * @param player
     */
    public void printError(ClientException exception, String player){
        if (getClientHandlerByNickname(player) != null)
            getClientHandlerByNickname(player).printError(exception);
    }

    /**
     *
     * @param nickname
     */
    public void printInterrupt(String nickname){
        for (ClientHandler c : clientHandlers)
            if (!nickname.equals(c.getPlayerNickname()) && c.connectionAlive()) {
                c.printInterrupt(nickname, false);
        }
    }

    /**
     * 
     * @param winner1
     * @param winner2
     */
    public void printWinner(String winner1, String winner2){
        for (ClientHandler c : clientHandlers) {
            c.printWinner(winner1, winner2, c.getPlayerNickname());
            c.setDisconnected();
        }
    }

}
