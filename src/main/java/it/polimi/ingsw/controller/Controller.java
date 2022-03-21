package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.professorController.ProfessorContext;
import it.polimi.ingsw.controller.professorController.ProfessorControllerStandard;
import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;

public class Controller {
    private TurnController turnController;
    ProfessorContext professorContext;
    private Game game;

    public Controller(){
        game = Game.getInstance();
        turnController = new TurnController();
        professorContext = new ProfessorContext(new ProfessorControllerStandard());
    }

    public void setNumPlayer(int num) {
        game.setNumPlayer(num);
    }

    public void addPlayer(Player player){
        int remaining = game.addPlayer(player);
        if (remaining == 0)
            game.startGame();
    }



    public void useAssistant(int position, Player player){
        Round round = game.getRound();

        if(!turnController.checkPermission(round.getTurn(), player, PlayerState.PLANNING))
            return;


        round.getTurn().getCurrentPlayer().addAssistant(position);
        if (!round.nextPlanningTurn())
            round.endPlanningPhase();
    }

    public void useStudentIsland(Player player, PawnColor color, int position){
        Round round = game.getRound();

        if(!turnController.checkPermission(round.getTurn(), player, PlayerState.ACTION))
            return;

        if(!turnController.canMove(round.getTurn()))
            return;

        Student student = player.getBoard().getEntrance().removeStudent(color);
        game.getTable().getIsland(position).addStudent(student);
    }

    public void useStudentDining(Player player, PawnColor color){
        Round round = game.getRound();

        if(!turnController.checkPermission(round.getTurn(), player, PlayerState.ACTION))
            return;

        if(!turnController.canMove(round.getTurn()))
            return;

        Student student = player.getBoard().getEntrance().removeStudent(color);
        player.getBoard().getDiningRoom().addStudent(student);

        professorContext.professorControl(player, color);

    }

}
