package it.polimi.ingsw.controller.motherNatureController;

import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;

public interface MotherNatureController {

    int checkMotherNature(Table table, int endPosition, Player player) throws ClientException;
}
