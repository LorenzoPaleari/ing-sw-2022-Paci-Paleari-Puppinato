package it.polimi.ingsw.controller.motherNatureController;

import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;

public class MotherNatureControllerStandard implements MotherNatureController{

    public int checkMotherNature(Table table, int endPosition, Player player) throws ClientException {
        int numMoves;

        int initPosition = table.getMotherPosition();
        if (endPosition <= initPosition)
            numMoves = table.getNumIsland() + endPosition - initPosition;
        else
            numMoves = endPosition - initPosition;

        if (player.getLastUsed() == null || numMoves > player.getLastUsed().getNumMovement())
            throw new ClientException(ErrorType.TOO_MUCH_MOVES);

        return numMoves;

    }
}
