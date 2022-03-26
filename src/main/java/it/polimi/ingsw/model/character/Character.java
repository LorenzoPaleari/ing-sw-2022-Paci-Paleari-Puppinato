package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

public abstract class Character {
    public abstract CharacterType getType();
    public abstract int getCosto();
    public abstract void returnNoEntryTiles();
    public abstract void activateCharacter(Context context);
    public abstract void activateCharacter(Island island, PawnColor color, Player player);
    public abstract void activateCharacter(PawnColor color, Game game);
    public abstract void activateCharacter(Player player, PawnColor[] color);
    public abstract void activateCharacter(Island island);


}

