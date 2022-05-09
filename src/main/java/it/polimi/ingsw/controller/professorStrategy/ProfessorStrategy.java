package it.polimi.ingsw.controller.professorStrategy;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

public interface ProfessorStrategy {

    void checkProfessor(Game game, Player player, PawnColor color);
}
