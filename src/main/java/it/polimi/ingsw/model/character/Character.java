package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
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
    public abstract void activateCharacter(Context context);
    public abstract void activateCharacter(Island island, PawnColor color, Player player);
    public abstract void activateCharacter(PawnColor color, Game game);
    public abstract void activateCharacter(Player player, PawnColor[] color);
    public abstract void activateCharacter(Island island);

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




