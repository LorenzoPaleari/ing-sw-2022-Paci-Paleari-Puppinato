package it.polimi.ingsw.controller.islandController;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

public interface IslandController {
    void setNoColor(PawnColor noColor);
    void calculateInfluence(Island island, Game game, String[] owner, TowerColor color, String[] playerCandidate);
}

