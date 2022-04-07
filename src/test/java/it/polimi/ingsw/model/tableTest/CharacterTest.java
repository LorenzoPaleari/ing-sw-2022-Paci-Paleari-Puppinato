package it.polimi.ingsw.model.tableTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.character.Character;
import it.polimi.ingsw.model.character.Factory;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.table.Bag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharacterTest {
    Factory factory = new Factory();
    private Character character;
    @BeforeEach
    void setUp(){
        character=factory.getCharacter(CharacterType.ADD_MOVES, new Bag());
    }
    @AfterEach
    void tearDown(){
        character=null;
    }
    @Test
    void firstUse(){
        character.firstUse();
        assertEquals(character.getType(), CharacterType.ADD_MOVES);
        assertTrue(character.isUsed());
        assertEquals(character.getPrice(), 2);
    }
}
