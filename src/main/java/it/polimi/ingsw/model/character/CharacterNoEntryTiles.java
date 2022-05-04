package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.TableHandler;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.exceptions.ErrorType;
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
    public void activateCharacter(Island island, TableHandler tableHandler)throws ClientException {
        if ((numNoEntryTiles < 1 || island.isNoEntryTiles()))
            {throw new ClientException(ErrorType.NO_ENTRY_TILES_SET);}
        else {
            island.setNoEntryTiles(true);
            numNoEntryTiles -= 1;
        }
    }
}
