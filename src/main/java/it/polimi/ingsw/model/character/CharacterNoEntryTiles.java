package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.exceptions.NoEntryTilesSetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

//NO_ENTRY_TILES
public class CharacterNoEntryTiles extends Character{
    private CharacterType type;
    private int price;
    private boolean used;
    private int numNoEntryTiles;
    public CharacterNoEntryTiles (CharacterType type){
        this.type=type;
        price = type.getPrice();
        used = false;
        numNoEntryTiles=4;
    }

    public void firstUse(){
        price += 1;
        used = true;
    }
    public boolean isUsed() {
        return used;
    }
    public int getPrice() {
        return price;
    }
    public CharacterType getType() {
        return type;
    }
    @Override
    public void returnNoEntryTiles() {
        numNoEntryTiles += 1;
    }
    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {

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
    public void activateCharacter(Island island)throws NoEntryTilesSetException {
        if ((numNoEntryTiles < 1 || island.isNoEntryTiles()))
            {throw new NoEntryTilesSetException("You don't have any other No Entry Tile left or the choosen island has already one No Entry Tile on");}
        else {
            island.setNoEntryTiles(true);
            numNoEntryTiles -= 1;
        }
    }
}
