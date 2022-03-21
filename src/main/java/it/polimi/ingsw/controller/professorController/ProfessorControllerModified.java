package it.polimi.ingsw.controller.professorController;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.player.Player;

public class ProfessorControllerModified implements ProfessorController{
    public void checkProfessor(Game game, Player player, PawnColor color){
        Professor prof = game.getTable().findProfessor(color);
        Board playerBoard = player.getBoard();

        if (playerBoard.getDiningRoom().count(color) >= prof.getNumStudent()){
            for (Player p : game.getPlayers()){
                p.getBoard().getProfessorTable().removeProfessor(prof);
            }

            playerBoard.getProfessorTable().addProfessor(prof);
        }
    }
}
