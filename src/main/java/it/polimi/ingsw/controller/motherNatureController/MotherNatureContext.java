package it.polimi.ingsw.controller.motherNatureController;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;

public class MotherNatureContext {
    private static MotherNatureController strategy;

    public MotherNatureContext(MotherNatureController strat){
        strategy = strat;
    }

    public static void changeMotherNatureContext(MotherNatureController strat){
        strategy = strat;
    }

    public int motherNatureControl(Table table, int endPosition, Player player){
        int numMoves = strategy.checkMotherNature(table, endPosition, player);
        return numMoves;
    }
}
