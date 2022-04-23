package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;
import java.util.*;

public class VirtualView {
    private Controller controller;
    private List<ClientHandler> clientHandlers;
    private Player player; //TEMPORANEO, TODO sostituire col vero giocatore che manda la request

    public VirtualView(Controller controller){
        this.controller=controller;
        clientHandlers= new LinkedList<>();
    }

    public void addClientHandler(ClientHandler clientHandler){
        clientHandlers.add(clientHandler);
    }

    public List<TowerColor> getAvailableColor(){
        return controller.getAvailableColor();
    }

    public void cloudChosen(int cloudPosition, String playerNickname){
        controller.chooseCloud(player,cloudPosition);
    }

    public void moveMotherNature(int endPosition, String playerNickname){
        controller.moveMotherNature(player,endPosition);
    }

    public void moveStudentToIsland(int islandPosition, PawnColor color, String playerNickname){
        controller.useStudentIsland(player, color, islandPosition);
    }
    public void moveStudentToDining(PawnColor color, String playerNickname){
        controller.useStudentDining(player, color);
    }

    public void useAssistant(int position, String playerNickname){
        controller.useAssistant(position, player);
    }

    public void setUpGameInfo(int numPlayer, boolean expert, String playerNickname){
        controller.setExpertMode(expert);
        controller.setNumPlayer(numPlayer);
        clientHandlers.get(0).playerSetUp();
    }
    public void setUpPlayerInfo(TowerColor color, String nickname, String playerNickname){
        controller.addPlayer(new Player(nickname, color));
        getClientHandlerByNickname(playerNickname).setPlayerNickname(nickname);
    }
    public ClientHandler getClientHandlerByNickname(String nickname){
        for (ClientHandler cl: clientHandlers){
            if(nickname.equals(cl.getPlayerNickname()))
                return cl;
        }
        return null;
    }

}
