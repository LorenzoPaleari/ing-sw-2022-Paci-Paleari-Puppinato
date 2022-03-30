package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.controller.professorController.ProfessorControllerModified;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

//CONTROL_PROFESSOR
//FAKE_MOTHER_NATURE
public class CharacterGroup2 extends Character {

    private  CharacterType type;
    private int price;
    private boolean used;
    private ProfessorController  professor= new ProfessorControllerModified();
    public CharacterGroup2 (CharacterType type){
        this.type=type;
        price = type.getPrice();
        used = false;
    }

    @Override
    public void returnNoEntryTiles() {

    }
    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {
        professorContext.changeContext(professor);
    }
    @Override
    public void activateCharacter(Game game, Player player, PawnColor color, Context context) {

    }

    @Override
    public void activateCharacter(Island island, PawnColor color) {

    }

    @Override
    public void activateCharacter(Player player, PawnColor[] color) {

    }

    @Override
    public void activateCharacter(Island island) {
        Controller.updateIsland(island);
    }

}
