package it.polimi.ingsw.controller.motherNatureController;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;

public class MotherNatureContext extends Context {
    private MotherNatureController strategy;

    public MotherNatureContext(MotherNatureController strategy){
        this.strategy = strategy;
    }

    @Override
    public void changeContext(MotherNatureController strategy){
        this.strategy = strategy;
    }

    @Override
    public int motherNatureControl(Table table, int endPosition, Player player) throws ClientException {
        return strategy.checkMotherNature(table, endPosition, player);
    }
}
