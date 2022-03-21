package it.polimi.ingsw.controller.motherNatureController;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;

public class MotherNatureControllerStandard implements MotherNatureController{

    public int checkMotherNature(Table table, int endPosition, Player player){
        int numMoves;

        int initPosition = table.getMotherPosition();
        if (endPosition <= initPosition)
            numMoves = table.getNumIsland() + endPosition - initPosition;
        else
            numMoves = endPosition - initPosition;

        if (numMoves <= player.getLastUsed().getNumMovement())
            return numMoves;

        return 0;
    }
}
