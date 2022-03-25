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
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.exceptions.GeneralSupplyFinishedException;
import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.character.Character;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;
import it.polimi.ingsw.model.table.Table;

import java.sql.Array;
import java.util.List;

public class Controller {
    private TurnController turnController;
    private Context professorContext;
    private ProfessorController professorControllerStandard;
    private Context motherNatureContext;
    private MotherNatureController motherNatureController;
    private static Context islandContext;
    private IslandController islandController;
    private static Game game;

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

        if (size == 2 && game.getRound().getNumTurnDone() == 2)
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
        Board board = player.getBoard();

        if(!turnController.checkPermission(round.getTurn(), player, PlayerState.ACTION))
            return;

        if(!turnController.canMove(round.getTurn()))
            return;

        Student student = board.getEntrance().removeStudent(color);
        board.getDiningRoom().addStudent(student);

        professorContext.professorControl(game, player, color);

        if (game.isExpertMode() && board.getDiningRoom().count(color) % 3 == 0) {
            try {
                game.getTable().withdrawCoin();
                player.addCoin();
            } catch (GeneralSupplyFinishedException e){

            }
        }
    }

    public void moveMotherNature(int endPosition, Player player){
        if(!turnController.checkPermission(game.getRound().getTurn(), player, PlayerState.ACTION))
            return;

        if(!turnController.canMoveMother(game.getRound().getTurn()))
            return;

        int numMoves = motherNatureContext.motherNatureControl(game.getTable(), endPosition, player);
        if (numMoves == 0){
            System.out.println("You can't go so far with your Assistant, please choose another Island");
            return;
        }

        game.getTable().moveMotherNature(numMoves);

        updateIsland(game.getTable().getIsland(game.getTable().getMotherPosition()));

        player.changeState(PlayerState.ENDTURN);
    }

    public static void updateIsland(Island island){
        if(islandContext.conquerIsland(island , game)) {
            game.getTable().mergeIsland(game.getTable().getMotherPosition());

            for (Player p : game.getPlayers()){
                if (p.getBoard().getTowerCourt().isEmpty())
                    winner();
            }

            if(game.getTable().getNumIsland() <= 3)
                winner();
        }
    }

    public void chooseCloud(int position, Player player){
        if(!turnController.checkPermission(game.getRound().getTurn(), player, PlayerState.ENDTURN))
            return;

        Table table = game.getTable();
        List<Student> student = table.getCloud(position).removeAllStudent();
        player.getBoard().getEntrance().addStudent(student);

        professorContext.changeContext(professorControllerStandard);
        islandContext.changeContext(islandController);
        motherNatureContext.changeContext(motherNatureController);

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

    public void useCharacter(Player player, int characterPosition){
        Character character = game.getTable().getCharacter(characterPosition);

        if(!turnController.checkCharacter(game, player, character.getCosto(), character))
            return;

        if (character.getType().equals(CharacterType.CONTROL_PROFESSOR))
            character.activateCharacter(professorContext);
        else if (character.getType().equals(CharacterType.ADD_MOVES))
            character.activateCharacter(motherNatureContext);
        else
            character.activateCharacter(islandContext);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition){
        Character character = game.getTable().getCharacter(characterPosition);

        if(!turnController.checkCharacter(game, player, character.getCosto(), character))
            return;

        character.activateCharacter(game.getTable().getIsland(islandPosition));
    }

    public void useCharacter(Player player, int characterPosition, PawnColor color){
        Character character = game.getTable().getCharacter(characterPosition);

        if(!turnController.checkCharacter(game, player, character.getCosto(), character))
            return;

        if(character.getType().hasStudent() > 0) {
            PawnColor[] colorArray = {color};
            character.activateCharacter(player, colorArray);
        }
        else
            character.activateCharacter(color, game);
    }

    public void useCharacter(Player player, int characterPosition, int[] colors){
        Character character = game.getTable().getCharacter(characterPosition);

        if(!turnController.checkCharacter(game, player, character.getCosto(), character))
            return;

        PawnColor[] color;
        int size = colors.length;
        color = new PawnColor[size];

        for (int i = 0; i < size; i++){
            color[i] = PawnColor.getColor(colors[i]);
        }

        character.activateCharacter(player, color);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition, PawnColor color){
        Character character = game.getTable().getCharacter(characterPosition);

        if(!turnController.checkCharacter(game, player, character.getCosto(), character))
            return;

        character.activateCharacter(game.getTable().getIsland(islandPosition), color, player);
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

        if(!winner2.equals(null))
            System.out.println("Draw between"+winner1+"and"+winner2);
        else
            System.out.println(winner1 + "Has won the game");
    }

}
