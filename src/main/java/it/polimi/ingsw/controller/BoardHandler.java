package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.GeneralSupplyFinishedException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;

public class BoardHandler {
    private static Game game;
    private TurnController turnController;
    private static Context professorContext;

    public BoardHandler(Game game, TurnController turnController, Context professorContext){
        this.game = game;
        this.professorContext = professorContext;
        this.turnController = turnController;
    }

    public void useStudentDining(Player player, PawnColor color){
        Round round = game.getRound();
        Board board = player.getBoard();

        try {
            turnController.checkPermission(round.getTurn(), player, PlayerState.ACTION);
            turnController.checkFullDining(board.getDiningRoom(), color);
            turnController.canMove(round.getTurn());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        Student student = board.getEntrance().removeStudent(color);
        board.getDiningRoom().addStudent(student);

        checkProfessor(player, color);

        if (game.isExpertMode() && board.getDiningRoom().count(color) % 3 == 0) {
            try {
                game.getTable().withdrawCoin();
                player.addCoin();
            } catch (GeneralSupplyFinishedException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void checkProfessor(Player player, PawnColor color){
        professorContext.professorControl(game, player, color);
    }
}
