package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;
import it.polimi.ingsw.model.table.Table;
import it.polimi.ingsw.server.VirtualView;

import java.util.List;

public class TableHandler {
    private TurnController turnController;
    private VirtualView virtualView;
    private static Game game;
    private static Context professorContext;
    private final ProfessorController professorControllerStandard;
    private final Context motherNatureContext;
    private final MotherNatureController motherNatureController;
    private static Context islandContext;
    private final IslandController islandController;

    public TableHandler(TurnController turnController, Game game, Context professorContext, Context motherNatureContext, Context islandContext, ProfessorController professorControllerStandard, MotherNatureController motherNatureController, IslandController islandController, VirtualView virtualView){
        this.game = game;
        this.virtualView = virtualView;
        this.turnController = turnController;
        this.professorControllerStandard = professorControllerStandard;
        this.professorContext = professorContext;
        this.motherNatureContext = motherNatureContext;
        this.motherNatureController = motherNatureController;
        this.islandContext = islandContext;
        this.islandController = islandController;
    }

    public void useStudentIsland(Player player, PawnColor color, int position){
        Round round = game.getRound();

        try {
            turnController.checkPermission(round.getTurn(), player, PlayerState.ACTION);
            turnController.canMove(round.getTurn());
        } catch (Exception e) {
            virtualView.printError(e.getMessage(), player.getNickname());
            return;
        }

        Student student = player.getBoard().getEntrance().removeStudent(color);
        game.getTable().getIsland(position).addStudent(student);
    }

    public void moveMotherNature(Player player, int endPosition) {
        int numMoves;

        try {
            turnController.checkPermission(game.getRound().getTurn(), player, PlayerState.ACTION);
            turnController.canMoveMother(game.getRound().getTurn());
            numMoves = motherNatureContext.motherNatureControl(game.getTable(), endPosition, player);
        } catch (Exception e) {
            virtualView.printError(e.getMessage(), player.getNickname());
            return;
        }

        game.getTable().moveMotherNature(numMoves);

        updateIsland(game.getTable().getIsland(game.getTable().getMotherPosition()));

        player.changeState(PlayerState.ENDTURN);
    }

    public static void updateIsland(Island island){
        if (island.isNoEntryTiles()){
            for (int i = 0; i < 3; i++)
                if (game.getTable().getCharacter(i).getType().equals(CharacterType.NO_ENTRY_TILES)) {
                    game.getTable().getCharacter(i).returnNoEntryTiles();
                    island.setNoEntryTiles(false);
                    return;
                }
        }
        else if(islandContext.conquerIsland(island , game)) {
            game.getTable().mergeIsland(game.getTable().getMotherPosition());

            for (Player p : game.getPlayers()){
                if (p.getBoard().getTowerCourt().isEmpty())
                    Controller.winner();
            }

            if(game.getTable().getNumIsland() <= 3)
                Controller.winner();
        }
    }

    public void chooseCloud(Player player, int position){
        try {
            turnController.checkPermission(game.getRound().getTurn(), player, PlayerState.ENDTURN);
            turnController.checkCloud(game.getRound(), position);
        } catch (Exception e){
            virtualView.printError(e.getMessage(), player.getNickname());
            return;
        }

        game.getRound().setCloudChosen(position);

        Table table = game.getTable();
        List<Student> student = table.getCloud(position).removeAllStudent();
        player.getBoard().getEntrance().addStudent(student);

        professorContext.changeContext(professorControllerStandard);
        islandContext.changeContext(islandController);
        motherNatureContext.changeContext(motherNatureController);
        game.getRound().getTurn().setUsedCharacter(false);

        if (!game.getRound().nextActionTurn()){
            if (game.getRound().getLastRound().equals(true))
                Controller.winner();

            try {
                List<Student> students = table.getBag().withdrawStudent(game.getNumPlayer() * (game.getNumPlayer() + 1));
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
}
