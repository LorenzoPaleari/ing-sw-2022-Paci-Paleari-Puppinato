package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.islandController.IslandContext;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerStandard;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureContext;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureControllerStandard;
import it.polimi.ingsw.controller.professorController.ProfessorContext;
import it.polimi.ingsw.controller.professorController.ProfessorControllerStandard;
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Cloud;
import it.polimi.ingsw.model.table.Table;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private TurnController turnController;
    ProfessorContext professorContext;
    ProfessorControllerStandard professorControllerStandard;
    MotherNatureContext motherNatureContext;
    MotherNatureController motherNatureController;
    IslandContext islandContext;
    IslandController islandController;
    PawnColor noInfluence = null;
    private Game game;

    public Controller(){
        game = Game.getInstance();
        turnController = new TurnController();
        professorControllerStandard = new ProfessorControllerStandard();
        professorContext = new ProfessorContext(professorControllerStandard);
        motherNatureController = new MotherNatureControllerStandard();
        motherNatureContext = new MotherNatureContext(motherNatureController);
        islandController = new IslandControllerStandard();
        islandContext = new IslandContext(islandController);
    }

    public void setNumPlayer(int num) {
        game.setNumPlayer(num);
    }

    public void addPlayer(Player player) throws BagIsEmptyException {
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
        if (!round.nextPlanningTurn()) {
            if (player.getDeck().isEmpty())
                round.setLastRound();
            round.endPlanningPhase();
        }
    }

    public boolean sameAssistant(int weight, Player player){
        boolean value = false;
        List<Integer> weights = null;

        int size = player.getDeck().getSize();
        for (Player p: game.getPlayers()){
            if (p.getDeck().getSize() == size - 1) {
                weights.add(p.getLastUsed().getWeight());
                if (p.getLastUsed().getWeight() == weight)
                    value = true;
            }
        }

        if (size == 1)
            value = false;

        if (size == 2 && game.getRound().getTurnDone() == 2)
            value = false;
            for (Assistant a : player.getDeck().getAssistant())
                if (a.getWeight() != weights.get(0) && a.getWeight() != weights.get(1))
                    value = true;

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
        if(!turnController.checkPermission(game.getRound().getTurn(), player, PlayerState.ACTION))
            return;

        if(game.getRound().getTurn().getRemainingMovements() > 0){
            System.out.println("Non hai finito di muovere gli studenti");
        }

        int numMoves = motherNatureContext.motherNatureControl(game.getTable(), endPosition, player);
        if (numMoves == 0){
            System.out.println("You can't go so far with your Assistant, please choose another Island");
            return;
        }

        game.getTable().moveMotherNature(numMoves);

        if(islandContext.conquerIsland(game.getTable().getIsland(game.getTable().getMotherPosition()), game, player, noInfluence)) {
            game.getTable().mergeIsland(game.getTable().getMotherPosition());

            for (Player p : game.getPlayers()){
                if (p.getBoard().getTowerCourt().isEmpty())
                    winner();
            }

            if(game.getTable().getNumIsland() <= 3)
                winner();
        }
    }

    public void chooseIsland(int position, Player player){
        Table table = game.getTable();
        List<Student> student = table.getCloud(position).removeAllStudent();
        player.getBoard().getEntrance().addStudent(student);

        if (!game.getRound().nextActionTurn()){
            if (game.getRound().getLastRound().equals(true))
                winner();

            try {
                List<Student> students = table.getBag().withdrawStudent(game.getNumPlayer() * (game.getNumPlayer()) + 1);
                game.getTable().fillCloud(game.getNumPlayer(), students);
            }
            catch(BagIsEmptyException e) {
                game.getRound().setLastRound();
            }
            finally {
                game.getRound().endRound();
            }
        }
    }

    public void winner(){
        Player winner1 = null;
        Player winner2 = null;
        int numTowers = 9;
        int numProfessors = 0;

        for (Player p: game.getPlayers()){
            int nTowers = p.getBoard().getTowerCourt().getTower().size();
            int nProfessors = p.getBoard().getProfessorTable().getProfessors().size();

            if (numTowers > nTowers || (numTowers == nTowers && numProfessors < nProfessors)){
                winner1 = p;
                winner2 = null;
                numProfessors = nProfessors;
                numTowers = nTowers;
            } else if (numTowers == nTowers && numProfessors == nProfessors){
                winner2 = p;
            }
        }

        if(!winner2.equals(null))
            System.out.println("Draw between"+winner1+"and"+winner2);
        else
            System.out.println(winner1 + "Has won the game");
    }

    public void setNoInfluence(PawnColor color){
        noInfluence = color;
    }

}
