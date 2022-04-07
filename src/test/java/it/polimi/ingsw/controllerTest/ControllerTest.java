package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.MotherNature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller controller;
    Player player1 = new Player("TEST1", TowerColor.WHITE);
    Player player2= new Player("TEST2", TowerColor.BLACK);
    Player player3= new Player("TEST3", TowerColor.GREY);
    private Game game;

    @BeforeEach
    void setUp(){
        controller = new Controller();
        controller.setNumPlayer(3);
        controller.setExpertMode(true);

        controller.addPlayer(player1);
        controller.addPlayer(player2);
        controller.addPlayer(player3);

        MotherNature.getInstance().setPosition(0);

        game = controller.getGame();
    }

    @AfterEach
    void tearDown(){
        controller = null;
        game = null;
    }

    @Test
    void setNumPlayer(){
        controller.setNumPlayer(3);
        assertEquals(3, game.getNumPlayer());
    }

    @Test
    void setExpertMode(){
        controller.setExpertMode(false);
        assertFalse(game.isExpertMode());
    }

    @Test
    void addPlayer(){
        assertEquals(player1, game.getPlayers().get(0));
        assertEquals("WHITE", game.getPlayers().get(0).getTowerColor().getText());

        assertEquals(player2, game.getPlayers().get(1));

        assertEquals(player3, game.getPlayers().get(2));
        assertEquals(PlayerState.PLANNING, game.getPlayers().get(0).getState());
        assertTrue(4 == player1.getDeck().getAssistant(3).getWeight());
    }

    @Test
    void useAssistant(){
        controller.useAssistant(6, player1);
        assertEquals(7, player1.getLastUsed().getWeight());

        controller.useAssistant(6, player2);
        assertEquals(null, player2.getLastUsed());

        controller.useAssistant(3, player2);
        assertEquals(4, player2.getLastUsed().getWeight());

        controller.useAssistant(1, player3);
        assertEquals(2, player3.getLastUsed().getWeight());
    }

    @Test
    void useAssistant2(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        controller.useAssistant(3, player2);
        assertEquals(null, player2.getLastUsed());
        outContent.reset();

        controller.moveMotherNature(player1, 3);
        assertEquals(0, game.getTable().getMotherPosition());

        for (int i = 3; i < 10; i++){
            player1.addAssistant(0);
            player2.addAssistant(0);
            player3.addAssistant(0);
        }
        player1.addAssistant(2);
        player2.addAssistant(1);
        player3.addAssistant(1);

        controller.useAssistant(1, player1);
        controller.useAssistant(1, player2);
        controller.useAssistant(1, player3);
        assertEquals(9, player3.getLastUsed().getWeight());

        controller.useAssistant(0, player3);

        assertEquals(9, player1.getLastUsed().getWeight());
        assertEquals(10, player2.getLastUsed().getWeight());
        assertEquals(8, player3.getLastUsed().getWeight());
    }

    @Test
    void useAssistant3(){
        for (int i = 1; i < 10; i++){
            player1.addAssistant(0);
            player2.addAssistant(0);
            player3.addAssistant(0);
        }

        controller.useAssistant(0, player1);
        controller.useAssistant(0, player2);
        controller.useAssistant(0, player3);

        assertTrue(game.getRound().getLastRound());
    }

    @Test
    void winner(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.
        // So, you can normally call, print(items);
        // I will assume items is already initialized properly.
        // Now you have to validate the output. Let's say items had 1 element.
        // With name as FirstElement and number as 1. String expectedOutput = "Name: FirstElement\nNumber: 1"
        // Notice the \n for new line. // Do the actual assertion. assertEquals(expectedOutput, outContent.toString());

        player1.getBoard().getTowerCourt().removeTower(4);
        player2.getBoard().getTowerCourt().removeTower(3);
        player3.getBoard().getTowerCourt().removeTower(3);

        Controller.winner();
        String expected = player1 + " Has won the game\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void winner2(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        player1.getBoard().getTowerCourt().removeTower(4);
        player2.getBoard().getTowerCourt().removeTower(4);
        player3.getBoard().getTowerCourt().removeTower(3);

        Controller.winner();
        String expected = "Draw between " +player1+ " and " +player2 + "\n";
        assertEquals(expected, outContent.toString());
    }

}
