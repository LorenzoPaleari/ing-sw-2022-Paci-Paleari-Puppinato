package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;
import it.polimi.ingsw.model.table.Table;

public abstract class Context {
    public void changeContext(IslandController strat){}
    public void changeContext(MotherNatureController strat){}
    public void changeContext(ProfessorController strat){}

    public boolean conquerIsland(Island island, Game game){
        return false;
    }
    public void professorControl(Game game, Player player, PawnColor color){}
    public int motherNatureControl(Table table, int endPosition, Player player){
        return 0;
    }
}
