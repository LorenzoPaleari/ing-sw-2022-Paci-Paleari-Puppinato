package it.polimi.ingsw.controller.islandController;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.table.Island;

public class IslandContext extends Context {
    private IslandController strategy;

    public IslandContext(IslandController strat){
        strategy = strat;
    }

    @Override
    public void changeContext(IslandController strat) {
        strategy = strat;
    }

    @Override
    public boolean conquerIsland(Island island, Game game){
        return strategy.calculateInfluence(island, game);
    }
}
