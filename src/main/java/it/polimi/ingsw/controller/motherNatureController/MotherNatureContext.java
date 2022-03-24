package it.polimi.ingsw.controller.motherNatureController;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;

public class MotherNatureContext extends Context {
    private MotherNatureController strategy;

    public MotherNatureContext(MotherNatureController strat){
        strategy = strat;
    }

    @Override
    public void changeContext(MotherNatureController strat){
        strategy = strat;
    }

    @Override
    public int motherNatureControl(Table table, int endPosition, Player player){
        int numMoves = strategy.checkMotherNature(table, endPosition, player);
        return numMoves;
    }
}
