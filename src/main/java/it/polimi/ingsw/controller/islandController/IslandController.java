package it.polimi.ingsw.controller.islandController;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

public interface IslandController {
    public boolean calculateInfluence(Island island, Game game, Player player, PawnColor pawnColor);
}
