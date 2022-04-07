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
        assertEquals(character.getType().getDescription(),"You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you've played.");
        assertEquals(character.getType().getText(),CharacterType.ADD_MOVES.toString());
        character.firstUse();
        assertEquals(character.getType(), CharacterType.ADD_MOVES);
        assertTrue(character.isUsed());
        assertEquals(character.getPrice(), 2);
    }
}
