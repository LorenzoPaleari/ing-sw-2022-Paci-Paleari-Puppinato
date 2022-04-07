package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.islandController.IslandContext;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerStandard;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureContext;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureControllerStandard;
import it.polimi.ingsw.controller.professorController.ProfessorContext;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.controller.professorController.ProfessorControllerStandard;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;

import java.util.LinkedList;
import java.util.List;

public class Controller {
    private final TurnController turnController;
    private static Context professorContext;
    private final ProfessorController professorControllerStandard;
    private final Context motherNatureContext;
    private final MotherNatureController motherNatureController;
    private static Context islandContext;
    private final IslandController islandController;
    private static Game game;
    private BoardHandler boardHandler;
    private TableHandler tableHandler;
    private CharacterHandler characterHandler;

    public Controller(){
        game = new Game();
        game.setExpertMode(false);
        turnController = new TurnController();

        professorControllerStandard = new ProfessorControllerStandard();
        professorContext = new ProfessorContext(professorControllerStandard);

        motherNatureController = new MotherNatureControllerStandard();
        motherNatureContext = new MotherNatureContext(motherNatureController);

        islandController = new IslandControllerStandard();
        islandContext = new IslandContext(islandController);

        boardHandler = new BoardHandler(game, turnController, professorContext);
        tableHandler = new TableHandler(turnController, game, professorContext, motherNatureContext, islandContext, professorControllerStandard, motherNatureController, islandController);
        characterHandler = new CharacterHandler(turnController, game, professorContext, motherNatureContext, islandContext);
    }

    public void setNumPlayer(int num) {
        game.setNumPlayer(num);
    }

    public void setExpertMode(boolean bool){
        game.setExpertMode(bool);
    }

    public void addPlayer(Player player) {
        int remaining = game.addPlayer(player);
        if (remaining == 0)
            game.startGame();
    }

    public void useAssistant(int position, Player player){
        Round round = game.getRound();
        int weight = player.getDeck().getAssistant(position).getWeight();

        try {
            turnController.checkPermission(round.getTurn(), player, PlayerState.PLANNING);
            sameAssistant(weight, player);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        player.addAssistant(position);

        if (!round.nextPlanningTurn()) {
            if (player.getDeck().isEmpty())
                round.setLastRound();
            round.endPlanningPhase();
        }
    }

    private void sameAssistant(int weight, Player player) throws SameAssistantException {
        boolean value = false;
        List<Integer> weights = new LinkedList<>();
        int size = player.getDeck().getSize();

        if (size == 1)
            return;

        for (Player p: game.getPlayers()){
            if (p.getDeck().getSize() == size - 1) {
                weights.add(p.getLastUsed().getWeight());
                if (p.getLastUsed().getWeight() == weight)
                    value = true;
            }
        }

        if (value == true && size == 2 && game.getRound().getNumTurnDone() == 2) {
            value = false;
            for (Assistant a : player.getDeck().getAssistant())
                if (!weights.contains(a.getWeight())) {
                    value = true;
                    break;
                }
        }

        if (value)
            throw new SameAssistantException();
    }

    public void useStudentDining(Player player, PawnColor color){
        boardHandler.useStudentDining(player, color);
    }

    public void useStudentIsland(Player player, PawnColor color, int position){
        tableHandler.useStudentIsland(player, color, position);
    }

    public void moveMotherNature(Player player, int endPosition) {
        tableHandler.moveMotherNature(player, endPosition);
    }

    public void chooseCloud(Player player, int position){
        tableHandler.chooseCloud(player, position);
    }

    public void useCharacter(Player player, int characterPosition){
        characterHandler.useCharacter(player, characterPosition);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition){
        characterHandler.useCharacter(player, characterPosition, islandPosition);
    }

    public void useCharacter(Player player, int characterPosition, PawnColor color){
        characterHandler.useCharacter(player, characterPosition, color);
    }

    public void useCharacter(Player player, int characterPosition, int[] colors){
        characterHandler.useCharacter(player, characterPosition, colors);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition, PawnColor color){
        characterHandler.useCharacter(player, characterPosition, islandPosition, color);
    }

    public static void winner(){
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

        game.endGame();

        if(winner2 != null)
            System.out.println("Draw between "+winner1+" and "+winner2);
        else
            System.out.println(winner1 + " Has won the game");
    }

    public static Game getGame() {
        return game;
    }
}
