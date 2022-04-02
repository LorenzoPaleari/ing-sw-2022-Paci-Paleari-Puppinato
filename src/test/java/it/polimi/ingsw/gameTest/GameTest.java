package it.polimi.ingsw.gameTest;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private static Game instance = null;
    @BeforeEach
    void setUp() {
      instance =Game.getInstance();
    }

    @Test
    void setNumPlayer() {
        instance.setNumPlayer(2);
        assertEquals(instance.getNumPlayer(),2 );
    }

    @Test
    void setExpertMode() {
       instance.setExpertMode(true);
        assertTrue(instance.isExpertMode());
    }

    @Test
    void startGame() {
        instance.addPlayer(new Player("test1", TowerColor.WHITE));
        instance.addPlayer(new Player("test2", TowerColor.BLACK));
        instance.startGame();
        assertNotNull(instance.getTable());
        assertNotNull(instance.getRound());
        assertEquals(instance.getPlayers().get(0).getState(), PlayerState.PLANNING);
    }

    @Test
    void addPlayer() {
        instance.setNumPlayer(2);
        List<Player> test= new LinkedList<>();
        test.add(new Player("test1", TowerColor.WHITE));
        assertEquals(instance.addPlayer(test.get(0)), 1);
        assertEquals(instance.getPlayers().get(0).getNickname(),"test1");

    }

    @Test
    void endGame() {
    }
    @Test
    void isNicknameUsed(){
        instance.addPlayer(new Player("test", TowerColor.WHITE));
        String test1= "test";
        assertTrue(instance.isNicknameUsed(test1));
       instance.addPlayer(new Player("test5", TowerColor.BLACK));
        String test2= "test2";
        assertFalse(instance.isNicknameUsed(test2));
    }
}