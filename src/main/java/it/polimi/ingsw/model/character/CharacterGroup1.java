package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureControllerModified;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

//ADD_MOVES
public class CharacterGroup1 extends Character{
    private CharacterType type;
    private int price;
    private boolean used;
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
    public void activateCharacter(Context motherNatureContext) {
        motherNatureContext.changeContext(motherNature);
    }

    @Override
    public void activateCharacter(Context context,PawnColor color) {

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
    public void activateCharacter(Island island) {

    }
    @Override
    public void activateCharacter(Player player, PawnColor color) {

    }
}
