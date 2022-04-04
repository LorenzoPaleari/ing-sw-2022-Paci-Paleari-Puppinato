package it.polimi.ingsw.gameTest;

import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private Round round;
    private LinkedList<Player> player;

    @BeforeEach
    void setUp() {
        player = new LinkedList<>();
        player.add(new Player("test1", TowerColor.WHITE));
        player.add(new Player("test2", TowerColor.BLACK));
        round=new Round(player);
    }

    @AfterEach
    void tearDown() {
        round=null;
    }

    @Test
    void setLastRound() {
        round.setLastRound();
        assertEquals(round.getLastRound(), true);
    }

    @Test
    void setCloudChosen() {
        int cloudChosen = 1;
        round.setCloudChosen(cloudChosen);
        assertEquals(round.getCloudChosen().get(0), cloudChosen);
    }

    @Test
    void nextActionTurn() {
        assertEquals(round.nextActionTurn(), true);
        player.remove();
        player.remove();
        assertEquals(round.nextActionTurn(), false);

    }

    @Test
    void endPlanningPhase() {
        round.endPlanningPhase();
        assertEquals(round.getNumTurnDone(), 0);
    }

    @Test
    void nextPlanningTurn() {
        assertEquals(round.nextPlanningTurn(), true);
        player.remove();
        player.remove();
        assertEquals(round.nextPlanningTurn(), false);
    }

    @Test
    void endRound() {
        round.endRound();
        assertEquals(round.getNumTurnDone(), 0);
    }
}