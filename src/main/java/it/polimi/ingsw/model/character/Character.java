package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.exceptions.NoEntryTilesSetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

public abstract class Character {

    private  CharacterType type;
    private int price;
    private boolean used;
    public abstract void returnNoEntryTiles();
    public abstract void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext);
    public abstract void activateCharacter(Game game, Player player, PawnColor color, Context context) throws BagIsEmptyException;
    public abstract void activateCharacter(Player player, PawnColor[] color);
    public abstract void activateCharacter(Island island) throws NoEntryTilesSetException;
    public abstract void activateCharacter(Island island, PawnColor color) throws BagIsEmptyException;


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

}




