package it.polimi.ingsw.controller.professorController;

import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

public class ProfessorContext {
    private static ProfessorController strategy;

    public ProfessorContext(ProfessorController strat){
        strategy = strat;
    }

    public static void changeProfessorContext(ProfessorController strat){
        strategy = strat;
    }

    public void professorControl(Game game, Player player, PawnColor color){
        strategy.checkProfessor(game, player, color);
    }
}
