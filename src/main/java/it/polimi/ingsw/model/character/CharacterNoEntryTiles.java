package it.polimi.ingsw.model.character;

import it.polimi.ingsw.exceptions.NoEntryTilesSetException;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.table.Island;

//GRANDMOTHER_HERBS
public class CharacterNoEntryTiles extends Character{
    private int numNoEntryTiles;
    public CharacterNoEntryTiles (CharacterType type){
        super(type);
        numNoEntryTiles=4;
    }

    @Override
    public void returnNoEntryTiles() {
        numNoEntryTiles += 1;
    }

    @Override
    public int getNumNoEntryTiles() {
        return numNoEntryTiles;
    }

    @Override
    public void activateCharacter(Island island)throws NoEntryTilesSetException {
        if ((numNoEntryTiles < 1 || island.isNoEntryTiles()))
            {throw new NoEntryTilesSetException("You don't have any other No Entry Tile left or the chosen island has already one No Entry Tile on");}
        else {
            island.setNoEntryTiles(true);
            numNoEntryTiles -= 1;
        }
    }
}
