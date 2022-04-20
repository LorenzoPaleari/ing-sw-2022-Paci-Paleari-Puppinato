package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
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

    public void addClientHandler(ClientHandler clientHandler){
        clientHandlers.add(clientHandler);
    }

    public List<TowerColor> getAvailableColor(){
        return controller.getAvailableColor();
    }

    public  int[] getIslandStudents(int islandPosition){
        int[] numStudent = new int[5];
        for(int i=0;i<5;i++){
            numStudent[i] = controller.getGame().getTable().getIsland(islandPosition).countStudent(PawnColor.getColor(i));
        }
        return numStudent;
    }
    public int getNumberIslandTowers(int islandPosition){
        return controller.getGame().getTable().getIsland(islandPosition).getWeight();
    }
    public TowerColor getColorIslandTowers(int islandPosition){
        return controller.getGame().getTable().getIsland(islandPosition).getIslandTower().get(0).getColor();
    }
    public boolean isMotherNature(int islandPosition){
        return controller.getGame().getTable().getIsland(islandPosition).isMotherNature();
    }
    public int getLastCharacterUsedOtherPlayer(Player player){
        return player.getLastUsed().getWeight();
    }
}
