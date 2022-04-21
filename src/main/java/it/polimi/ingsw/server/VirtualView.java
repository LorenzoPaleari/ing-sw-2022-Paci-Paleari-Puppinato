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

    public void cloudChosen(int cloudPosition){
        controller.chooseCloud(player,cloudPosition);
    }

    public void moveMotherNature(int endPosition){
        controller.moveMotherNature(player,endPosition);
    }

    public void moveStudentToIsland(int islandPosition, PawnColor color){
        controller.useStudentIsland(player, color, islandPosition);
    }
    public void moveStudentToDining(PawnColor color){
        controller.useStudentDining(player, color);
    }

    public void useAssistant(int position){
        controller.useAssistant(position, player);
    }

    public void setUpGameInfo(int numPlayer, boolean expert){
        controller.setExpertMode(expert);
        controller.setNumPlayer(numPlayer);
    }
    public void setUpPlayerInfo(TowerColor color, String nickname){
        controller.getGame().addPlayer(new Player(nickname, color));
    }
}
