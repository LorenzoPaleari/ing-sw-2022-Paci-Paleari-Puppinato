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

    @BeforeEach
    void setUp() {
        List<Player> player = new LinkedList<>();
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

    }

    @Test
    void setCloudChosen() {
    }

    @Test
    void nextActionTurn() {
    }

    @Test
    void endPlanningPhase() {
    }

    @Test
    void nextPlanningTurn() {
    }

    @Test
    void endRound() {
    }
}