package it.polimi.ingsw.controller.motherNatureStrategy;

import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;

public class MotherNatureStrategyMagicDeliveryMan implements MotherNatureStrategy {

    public int checkMotherNature(Table table, int endPosition, Player player) throws ClientException {
        int numMoves;

        int initPosition = table.getMotherPosition();
        if (endPosition <= initPosition)
            numMoves = table.getNumIsland() + endPosition - initPosition;
        else
            numMoves = endPosition - initPosition;

        if (numMoves > 2 + player.getLastUsed().getNumMovement())
            throw new ClientException(ErrorType.TOO_MUCH_MOVES);

        return numMoves;
    }
}
