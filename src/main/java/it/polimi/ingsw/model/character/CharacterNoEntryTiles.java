package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
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

    @Override
    public void returnNoEntryTiles() {
        numNoEntryTiles += 1;
    }

    @Override
    public void activateCharacter(Context context) {

    }

    @Override
    public void activateCharacter(Island island, PawnColor color, Player player) {

    }

    @Override
    public void activateCharacter(PawnColor color, Game game) {

    }

    @Override
    public void activateCharacter(Player player, PawnColor[] color) {

    }

    @Override
    public void activateCharacter(Island island) { //potrebbe generare eccezione
        if(numNoEntryTiles>0) {
            island.setNoEntryTiles(true);
            numNoEntryTiles -= 1;
        }
    }
}
