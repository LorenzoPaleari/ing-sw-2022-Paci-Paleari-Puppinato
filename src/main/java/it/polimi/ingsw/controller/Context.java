package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.islandStrategy.IslandStrategy;
import it.polimi.ingsw.controller.motherNatureStrategy.MotherNatureStrategy;
import it.polimi.ingsw.controller.professorStrategy.ProfessorStrategy;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;
import it.polimi.ingsw.model.table.Table;

public abstract class Context {
    public void changeContext(IslandStrategy strategy){}
    public void changeContext(MotherNatureStrategy strategy){}
    public void changeContext(ProfessorStrategy strategy){}

    public boolean conquerIsland(Island island, Game game){
        return false;
    }
    public void professorControl(Game game, Player player, PawnColor color){}
    public int motherNatureControl(Table table, int endPosition, Player player) throws ClientException {
        return 0;
    }
}
