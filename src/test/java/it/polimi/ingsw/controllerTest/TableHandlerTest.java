package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;
import it.polimi.ingsw.model.table.MotherNature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableHandlerTest {
    private Controller controller;
    Player player1 = new Player("TEST1", TowerColor.WHITE);
    Player player2 = new Player("TEST2", TowerColor.BLACK);
    Player player3 = new Player("TEST3", TowerColor.GREY);
    private Game game;

    @BeforeEach
    void setUp() {
        controller = new Controller();
        controller.setNumPlayer(3);
        controller.setExpertMode(true);

        controller.addPlayer(player1);
        controller.addPlayer(player2);
        controller.addPlayer(player3);

        game = controller.getGame();

        controller.useAssistant(0, player1);
        controller.useAssistant(3, player2);
        controller.useAssistant(6, player3);

        MotherNature.getInstance().setPosition(0);

    }

    @AfterEach
    void tearDown() {
        controller = null;
        game = null;
    }

    @Test
    void moveMotherNature() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        game.getTable().setCharacter(0, CharacterType.NO_ENTRY_TILES);
        game.getRound().getTurn().resetRemainingMovements(1);
        controller.moveMotherNature(player1, 1);
        //assertEquals("Before moving mother nature, please move all the students\n",outContent.toString());
        outContent.reset();
        game.getRound().getTurn().resetRemainingMovements(0);
        controller.moveMotherNature(player1, 2);
        //assertEquals("You can't go so far with your Assistant, please choose another Island\n",outContent.toString());
        outContent.reset();

        game.getTable().getIsland(1).setNoEntryTiles(true);
        controller.moveMotherNature(player1, 1);
        assertFalse(game.getTable().getIsland(1).isNoEntryTiles());
        assertEquals(player1.getState(), PlayerState.ENDTURN);

        game.getRound().nextActionTurn();
        game.getRound().getTurn().resetRemainingMovements(0);
        List<Tower> test = new LinkedList<>();
        test.add(new Tower(TowerColor.BLACK));

        for (int i = 0; i < 5; i++) {
            game.getTable().getIsland(i).addTower(test);
        }
        game.getTable().mergeIsland(1);
        assertEquals(game.getTable().getIsland(0).getIslandTower().size(), 3);
        game.getTable().mergeIsland(1);
        game.getTable().getIsland(game.getTable().getMotherPosition()).setMotherNature(false);
        MotherNature.getInstance().setPosition(7);
        game.getTable().getIsland(7).setMotherNature(true);
        assertEquals(game.getTable().getIsland(0).getIslandTower().size(), 5);
        for (int i = 0; i < 16; i++) {
            game.getTable().getIsland(1).addStudent(new Student(0));
        }
        player2.getBoard().getTowerCourt().removeTower(5);
        player2.getBoard().getProfessorTable().addProfessor(game.getTable().findProfessor(PawnColor.GREEN));
        assertEquals(player2.getBoard().getProfessorTable().find(PawnColor.GREEN), game.getTable().findProfessor(PawnColor.GREEN));
        controller.moveMotherNature(player2, 1);
        assertEquals(game.getTable().getIsland(1).getIslandStudent().size(), 0);
        assertEquals(player2.getBoard().getTowerCourt().getTower().size(), 0);
        //assertEquals(player2+" Has won the game\n", outContent.toString());
    }

    @Test
    void updateIsland() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        game.getRound().getTurn().resetRemainingMovements(0);
        List<Tower> test = new LinkedList<>();
        test.add(new Tower(TowerColor.WHITE));
        for (int i = 0; i <7; i++) {
            game.getTable().getIsland(i).addTower(test);
        }
        game.getTable().mergeIsland(1);
        game.getTable().mergeIsland(1);
        game.getTable().mergeIsland(1);
        game.getTable().mergeIsland(0);
        for (int i = 0; i < 16; i++) {
            game.getTable().getIsland(1).addStudent(new Student(0));
        }
        player1.getBoard().getProfessorTable().addProfessor(game.getTable().findProfessor(PawnColor.GREEN));
        controller.moveMotherNature(player1, 1);
        //assertEquals(player1+" Has won the game\n", outContent.toString());
    }
}