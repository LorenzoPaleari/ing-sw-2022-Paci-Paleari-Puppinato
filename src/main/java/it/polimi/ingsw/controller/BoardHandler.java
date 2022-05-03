package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.exceptions.GeneralSupplyFinishedException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;
import it.polimi.ingsw.server.VirtualView;

public class BoardHandler {
    private Game game;
    private VirtualView virtualView;
    private TurnController turnController;
    private static Context professorContext;

    public BoardHandler(Game game, TurnController turnController, Context professorContext, VirtualView virtualView){
        this.game = game;
        this.virtualView = virtualView;
        this.professorContext = professorContext;
        this.turnController = turnController;
        try {
            this.game.setMethodBoard(getClass().getMethod("checkProfessor", Player.class, PawnColor.class));
        } catch (NoSuchMethodException ignored){}
    }

    public void useStudentDining(Player player, PawnColor color){
        Round round = game.getRound();
        Board board = player.getBoard();

        try {
            turnController.checkPermission(round.getTurn(), player, PlayerState.ACTION);
            turnController.checkFullDining(board.getDiningRoom(), color);
            turnController.canMove(round.getTurn());
        } catch (ClientException e){
            virtualView.printError(e, player.getNickname());
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
            }
        }
    }

    public void checkProfessor(Player player, PawnColor color){
        professorContext.professorControl(game, player, color);
    }
}
