package it.polimi.ingsw.controller.professorController;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

public interface ProfessorController {

    public void checkProfessor(Player player, PawnColor color);
}
