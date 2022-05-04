package it.polimi.ingsw.model.tableTest;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.controller.BoardHandler;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.TableHandler;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.islandController.IslandContext;
import it.polimi.ingsw.controller.islandController.IslandControllerMoreInfluence;
import it.polimi.ingsw.controller.islandController.IslandControllerNoColor;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureContext;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureControllerModified;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureControllerStandard;
import it.polimi.ingsw.controller.professorController.ProfessorContext;
import it.polimi.ingsw.controller.professorController.ProfessorControllerStandard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.character.Character;
import it.polimi.ingsw.model.character.Factory;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Bag;

import it.polimi.ingsw.model.table.Island;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharacterTest {
    Factory factory = new Factory();
    private Character character;

    private TableHandler tableHandler = new TableHandler(new TurnController(), new Game(), new IslandContext(new IslandControllerNoColor()), new ProfessorContext(new ProfessorControllerStandard()), new MotherNatureContext(new MotherNatureControllerModified()), new ProfessorControllerStandard(), new MotherNatureControllerModified(), new IslandControllerNoColor(), new VirtualView(new Controller(), new LobbyHandler()));
    private BoardHandler boardHandler = new BoardHandler(new Game(), new TurnController(), new MotherNatureContext(new MotherNatureControllerStandard()), new VirtualView(new Controller(), new LobbyHandler()));
    @BeforeEach
    void setUp() throws NoSuchMethodException {
        character=factory.getCharacter(CharacterType.MAGIC_DELIVERY_MAN, new Bag(), tableHandler.getClass().getMethod("updateIsland", Island.class), boardHandler.getClass().getMethod("checkProfessor", Player.class, PawnColor.class), new IslandControllerMoreInfluence());
    }
    @AfterEach
    void tearDown(){
        character=null;
    }
    @Test
    void firstUse(){
        assertEquals(character.getType().getDescription(),"You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you've played.");
        assertEquals(character.getType().getText(),CharacterType.MAGIC_DELIVERY_MAN.toString());
        character.firstUse();
        assertEquals(character.getType(), CharacterType.MAGIC_DELIVERY_MAN);
        assertTrue(character.isUsed());
        assertEquals(character.getPrice(), 2);
    }
}
