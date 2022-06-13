package it.polimi.ingsw.controller.professorStrategy;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.player.Player;

/**
 * Standard professor strategy class
 */
public class ProfessorStrategyStandard implements ProfessorStrategy {
    /**
     * checks professor
     * @param game the current game
     * @param player the current player
     * @param color the pawn color
     */
    public void checkProfessor(Game game, Player player, PawnColor color){
        Professor prof = game.getTable().findProfessor(color);
        Board playerBoard = player.getBoard();

        if (playerBoard.getDiningRoom().count(color)+1 > prof.getNumStudent()){
            prof.setNumStudent(playerBoard.getDiningRoom().count(color)+1);
            for (Player p : game.getPlayers()){
                p.getBoard().getProfessorTable().removeProfessor(prof);
            }

            playerBoard.getProfessorTable().addProfessor(prof);
        }
    }
}
