package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.MotherNature;
import it.polimi.ingsw.server.VirtualView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
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
        controller.setVirtualView(new VirtualView(controller));

        controller.addPlayer(player1);
        controller.addPlayer(player2);
        controller.addPlayer(player3);

        game = controller.getGame();

        controller.useAssistant(7, player1);
        controller.useAssistant(8, player2);
        controller.useAssistant(9, player3);

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
        controller.moveMotherNature(player1, 8);
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
        outContent.reset();
    }

    @Test
    void updateIsland() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        List<Tower> test = new LinkedList<>();
        test.add(new Tower(TowerColor.WHITE));
        for (int i = 0; i <5; i++) {
            game.getTable().getIsland(i).addTower(test);
        }
        List<Tower> test1 = new LinkedList<>();
        test1.add(new Tower(TowerColor.BLACK));
        for (int i = 5; i <9; i++) {
            game.getTable().getIsland(i).addTower(test1);
        }

        List<Tower> test2 = new LinkedList<>();
        test2.add(new Tower(TowerColor.GREY));
        for (int i = 9; i <11; i++) {
            game.getTable().getIsland(i).addTower(test2);
        }
        game.getTable().mergeIsland(1);
        assertEquals(game.getTable().getNumIsland(), 10);
        game.getTable().mergeIsland(1);
        assertEquals(game.getTable().getNumIsland(), 8);
        game.getTable().mergeIsland(2);
        assertEquals(game.getTable().getIsland(2).getIslandTower().get(0).getColor(),TowerColor.BLACK);
        assertEquals(game.getTable().getNumIsland(), 6);
        game.getTable().mergeIsland(2);
        game.getTable().mergeIsland(3);
        assertEquals(game.getTable().getNumIsland(), 4);
        for (int i = 0; i < 16; i++) {
            game.getTable().getIsland(3).addStudent(new Student(0));
        }
        player1.getBoard().getTowerCourt().removeTower(5);
        player2.getBoard().getTowerCourt().removeTower(4);
        player3.getBoard().getTowerCourt().removeTower(2);
        game.getRound().nextActionTurn();
        game.getRound().nextActionTurn();
        game.getRound().getTurn().resetRemainingMovements(0);
        player3.getBoard().getProfessorTable().addProfessor(game.getTable().findProfessor(PawnColor.GREEN));
        controller.moveMotherNature(player3, 3);
        //assertEquals(player1+" Has won the game\n", outContent.toString());
        outContent.reset();
    }
    @Test
    void chooseCloud() throws BagIsEmptyException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        List<Tower> test = new LinkedList<>();
        test.add(new Tower(TowerColor.GREY));
        game.getTable().getIsland(8).addTower(test);
        game.getTable().getIsland(9).addTower(test);
        game.getTable().getIsland(10).addTower(test);
        game.getTable().getIsland(11).addTower(test);
        game.getRound().getTurn().resetRemainingMovements(0);
        controller.moveMotherNature(player1, 2);
        controller.chooseCloud(player1,0);
        assertFalse(game.getRound().getTurn().isUsedCharacter());

        game.getRound().getTurn().resetRemainingMovements(0);
        controller.moveMotherNature(player2, 3);
        controller.chooseCloud(player2,0);
        //assertEquals("This cloud has already been chose, please select another\n",outContent.toString());
        outContent.reset();
        controller.useAssistant(6, player2);
        //assertEquals("Devi scegliere una Nuvola\n", outContent.toString());
        outContent.reset();
        controller.chooseCloud(player2,1);
        game.getRound().getTurn().resetRemainingMovements(0);
        controller.moveMotherNature(player3, 4);
        controller.chooseCloud(player3,2);
        controller.useAssistant(4, player1);
        controller.useAssistant(5, player2);
        controller.useAssistant(6, player3);

        game.getRound().nextActionTurn();
        game.getRound().nextActionTurn();
        game.getRound().getTurn().resetRemainingMovements(0);
        controller.moveMotherNature(player3, 5);
        game.getTable().getBag().withdrawStudent(game.getTable().getBag().getStudent().size());
        controller.chooseCloud(player3,2);
        controller.useAssistant(5, player1);
        controller.useAssistant(6, player2);
        controller.useAssistant(7, player3);
        player3.getBoard().getTowerCourt().removeTower(4);
        game.getRound().nextActionTurn();
        game.getRound().nextActionTurn();
        game.getRound().getTurn().resetRemainingMovements(0);
        controller.moveMotherNature(player3, 6);
        controller.chooseCloud(player3,2);
        //assertEquals(player3+" Has won the game\n", outContent.toString());
        outContent.reset();
    }

    @Test
    void useStudentIsland(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PawnColor color= player1.getBoard().getEntrance().getStudent().get(0).getColor();
        game.getRound().getTurn().resetRemainingMovements(1);
        controller.useStudentIsland(player1, color, 0);
        assertEquals(player1.getBoard().getEntrance().getStudent().size(), 8);
        assertEquals(game.getRound().getTurn().getRemainingMovements(),0);
        assertEquals(game.getTable().getIsland(0).getIslandStudent().size(),1);
        assertEquals(game.getTable().getIsland(0).getIslandStudent().get(0).getColor(),color);
        PawnColor color2= player1.getBoard().getEntrance().getStudent().get(3).getColor();
        controller.useStudentIsland(player1, color2, 0);
        //assertEquals("You have already moved all the student, please move Mother Nature\n",outContent.toString());
    }
}