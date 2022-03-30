package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.islandController.IslandContext;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerNoColor;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureContext;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureControllerModified;
import it.polimi.ingsw.controller.professorController.ProfessorContext;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

//ADD_MOVES
//NO_COLOR
public class CharacterGroup1 extends Character{
    private CharacterType type;
    private int price;
    private boolean used;
    private IslandController islandController= new IslandControllerNoColor();
    private MotherNatureController motherNature = new MotherNatureControllerModified();

    public CharacterGroup1 (CharacterType type){
        this.type = type;
        price = type.getPrice();
        used = false;
    }
    @Override
    public void returnNoEntryTiles() {

    }

    @Override
    public void activateCharacter(ProfessorContext professorContext, MotherNatureContext motherNatureContext, IslandContext islandContext) {
        motherNatureContext.changeContext(motherNature);
    }

    @Override
    public void activateCharacter(Game game, Player player, PawnColor color, Context context) {
        islandController.setNoColor(color);
        context.changeContext(islandController);
    }
    @Override
    public void activateCharacter(Island island, PawnColor color) {

    }


    @Override
    public void activateCharacter(Player player, PawnColor[] color) {

    }

    @Override
    public void activateCharacter(Island island) {
    }

}
