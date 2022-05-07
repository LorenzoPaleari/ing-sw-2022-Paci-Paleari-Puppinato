package it.polimi.ingsw.controller.professorController;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

public class ProfessorContext extends Context {
    private ProfessorController strategy;

    public ProfessorContext(ProfessorController strategy){
        this.strategy = strategy;
    }

    @Override
    public void changeContext(ProfessorController strategy){
        this.strategy = strategy;
    }

    @Override
    public void professorControl(Game game, Player player, PawnColor color){
        strategy.checkProfessor(game, player, color);
    }
}
