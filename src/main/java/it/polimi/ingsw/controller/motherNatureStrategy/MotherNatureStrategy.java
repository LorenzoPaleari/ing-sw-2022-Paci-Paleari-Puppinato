package it.polimi.ingsw.controller.motherNatureStrategy;

import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;

public interface MotherNatureStrategy {

    int checkMotherNature(Table table, int endPosition, Player player) throws ClientException;
}
