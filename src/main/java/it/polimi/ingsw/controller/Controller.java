package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.islandController.IslandContext;
import it.polimi.ingsw.controller.islandController.IslandControllerStandard;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureContext;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureControllerStandard;
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
    MotherNatureContext motherNatureContext;
    IslandContext islandContext;
    PawnColor noInfluence = null;
    private Game game;

    public Controller(){
        game = Game.getInstance();
        turnController = new TurnController();
        professorContext = new ProfessorContext(new ProfessorControllerStandard());
        motherNatureContext = new MotherNatureContext(new MotherNatureControllerStandard());
        islandContext = new IslandContext(new IslandControllerStandard());
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

        int weight = player.getDeck().getAssistant(position).getWeight();
        if(sameAssistant(weight, player)) {
            System.out.println("Choose an Assistant different from others player");
            return;
        }

        player.addAssistant(position);
        if (!round.nextPlanningTurn())
            round.endPlanningPhase();
    }

    public boolean sameAssistant(int weight, Player player){
        boolean value = false;

        int size = player.getDeck().getSize();
        for (Player p: game.getPlayers()){
            if (p.getDeck().getSize() == size - 1 && p.getLastUsed().getWeight() == weight)
                value = true;
        }

        if (size == 1 || size == 2 && game.getRound().getTurnDone() ==2)
            value = false;

        return value;
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

        professorContext.professorControl(game, player, color);
    }

    public void moveMotherNature(int endPosition, Player player){
        int numMoves = motherNatureContext.motherNatureControl(game.getTable(), endPosition, player);
        if (numMoves == 0){
            System.out.println("You can't go so far with your Assistant, please choose another Island");
            return;
        }

        game.getTable().moveMotherNature(numMoves);

        if(islandContext.conquerIsland(game.getTable().getIsland(game.getTable().getMotherPosition()), game, player, noInfluence)) {
            game.getTable().mergeIsland(game.getTable().getMotherPosition());

            //Temporary Win
            for (Player p : game.getPlayers()){
                if (p.getBoard().getTowercourt().isEmpty())
                    System.out.println("Complimenti!!! "+p+" hai vinto");
            }

            if(game.getTable().getNumIsland() <= 3)
                System.out.println("3 o meno isole rimaste");
        }
    }






    public void setNoInfluence(PawnColor color){
        noInfluence = color;
    }

}
