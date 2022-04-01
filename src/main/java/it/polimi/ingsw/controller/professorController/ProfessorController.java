package it.polimi.ingsw.controller.professorController;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

public interface ProfessorController {

    void checkProfessor(Game game, Player player, PawnColor color);
}
