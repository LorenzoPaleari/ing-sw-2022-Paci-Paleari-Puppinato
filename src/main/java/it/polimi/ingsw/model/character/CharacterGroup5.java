package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.islandController.IslandContext;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerNoTower;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureContext;
import it.polimi.ingsw.controller.professorController.ProfessorContext;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.controller.professorController.ProfessorControllerModified;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

//NO_TOWER
//RETURN
public class CharacterGroup5 extends Character{
    private CharacterType type;
    private int price;
    private boolean used;
    private IslandController island = new IslandControllerNoTower();
    public CharacterGroup5 (CharacterType type){
        this.type=type;
        price = type.getPrice();
        used = false;
    }
    @Override
    public void returnNoEntryTiles() {

    }

    @Override
    public void activateCharacter(ProfessorContext professorContext, MotherNatureContext motherNatureContext, IslandContext islandContext) {
        islandContext.changeContext(island);
    }

    @Override
    public void activateCharacter(Island island, PawnColor color) {

    }
    @Override
    public void activateCharacter(Game game, Player player, PawnColor color, Context context) {

    }


    @Override
    public void activateCharacter(Player player, PawnColor[] color) {

    }

    @Override
    public void activateCharacter(Island island) {

    }
}
