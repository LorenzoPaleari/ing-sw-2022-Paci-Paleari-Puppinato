package it.polimi.ingsw.server;

import it.polimi.ingsw.client.viewUtilities.GameInfo;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

public class VirtualView {
    private Controller controller;
    private List<ClientHandler> clientHandlers;

    public VirtualView(Controller controller){
        this.controller=controller;
        clientHandlers= new LinkedList<>();
    }

    public Controller getController() {return controller;}

    public void addClientHandler(ClientHandler clientHandler){
        clientHandlers.add(clientHandler);
    }

    public List<TowerColor> getAvailableColor(){
        return controller.getAvailableColor();
    }

    public void cloudChosen(int cloudPosition, String playerNickname){
        controller.chooseCloud(controller.getPlayerByNickname(playerNickname),cloudPosition);
    }

    public void moveMotherNature(int endPosition, String playerNickname){
        controller.moveMotherNature(controller.getPlayerByNickname(playerNickname),endPosition);
    }

    public void moveStudentToIsland(int islandPosition, PawnColor color, String playerNickname){
        controller.useStudentIsland(controller.getPlayerByNickname(playerNickname), color, islandPosition);
    }
    public void moveStudentToDining(PawnColor color, String playerNickname){
        controller.useStudentDining(controller.getPlayerByNickname(playerNickname), color);
    }

    public void useAssistant(int position, String playerNickname){
        controller.useAssistant(position, controller.getPlayerByNickname(playerNickname));
    }

    public void useCharacter(int[] colors, int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition, colors);
    }

    public void useCharacter(int islandPosition, PawnColor color , int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition, islandPosition, color);
    }

    public void useCharacter(PawnColor color, int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition, color);
    }

    public void useCharacter(int islandPosition, int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition, islandPosition);
    }

    public void useCharacter(int characterPosition, String playerNickname){
        controller.useCharacter(controller.getPlayerByNickname(playerNickname), characterPosition);
    }

    public void setUpGameInfo(int numPlayer, boolean expert, String playerNickname){
        controller.setExpertMode(expert);
        controller.setNumPlayer(numPlayer);

        colorSetUp(playerNickname);
    }

    public void colorSetUp(String nickname){
        getClientHandlerByNickname(nickname).colorSetUp(false);
    }

    public synchronized void setUpPlayerColor(TowerColor color, String playerNickname){
        if(getAvailableColor().contains(color))
            controller.addPlayer(new Player(playerNickname, color));
        else
            getClientHandlerByNickname(playerNickname).colorSetUp(true);
    }

    public ClientHandler getClientHandlerByNickname(String nickname){
        for (ClientHandler cl: clientHandlers){
            if(nickname.equals(cl.getPlayerNickname()))
                return cl;
        }
        return null;
    }

    public void printGameBoard(Game game){
        for (ClientHandler c : clientHandlers)
            c.printGameBoard(new GameInfo(game, c.getPlayerNickname()));
    }

    public void printError(ClientException exception, String player){
        if (getClientHandlerByNickname(player) != null)
            getClientHandlerByNickname(player).printError(exception);
    }

    public void printInterrupt(String nickname){
        for (ClientHandler c : clientHandlers)
            if (!nickname.equals(c.getPlayerNickname()) && c.connectionAlive()) {
                c.printInterrupt(nickname, false);
        }
    }

    public void printWinner(String winner1, String winner2){
        for (ClientHandler c : clientHandlers) {
            c.printWinner(winner1, winner2, c.getPlayerNickname());
            c.setDisconnected();
        }
    }

}
