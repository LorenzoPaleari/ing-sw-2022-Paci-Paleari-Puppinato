package it.polimi.ingsw.controller.professorController;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

public class ProfessorContext {
    private ProfessorController strategy;

    public ProfessorContext(ProfessorController strategy){
        this.strategy = strategy;
    }

    public void professorControl(Player player, PawnColor color){
        strategy.checkProfessor(player, color);
    }
}
