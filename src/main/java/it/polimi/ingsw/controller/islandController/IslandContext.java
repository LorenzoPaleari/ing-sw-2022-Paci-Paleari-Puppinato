package it.polimi.ingsw.controller.islandController;

import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

public class IslandContext {
    private static IslandController strategy;

    public IslandContext(IslandController strat){
        strategy = strat;
    }

    public static void changeIslandContext(IslandController strat){
        strategy = strat;
    }

    public boolean conquerIsland(Island island, Game game, Player player, PawnColor pawnColor){
        return strategy.calculateInfluence(island, game, player, pawnColor);
    }
}
