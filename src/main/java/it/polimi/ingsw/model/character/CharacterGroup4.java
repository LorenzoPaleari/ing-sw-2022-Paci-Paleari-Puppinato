package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerNoColor;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.controller.professorController.ProfessorControllerModified;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

//NO_COLOR
//FAKE_MOTHER_NATURE
public class CharacterGroup4 extends Character{
    private CharacterType type;
    private int price;
    private boolean used;
    private IslandController islandController= new IslandControllerNoColor();
    public CharacterGroup4 (CharacterType type){
        this.type=type;
        price = type.getPrice();
        used = false;
    }
    @Override
    public void returnNoEntryTiles() {

    }

    @Override
    public void activateCharacter(Context islandContext) {
        islandContext.changeContext(islandController);
    }

    @Override
    public void activateCharacter(Island island, PawnColor color) {

    }

    @Override
    public void activateCharacter(PawnColor color, Game game) {

    }

    @Override
    public void activateCharacter(Player player, PawnColor[] color) {

    }
    @Override
    public void activateCharacter(Player player, PawnColor color) {

    }
    @Override
    public void activateCharacter(Island island) {
        Controller.updateIsland(island);
    }
}
