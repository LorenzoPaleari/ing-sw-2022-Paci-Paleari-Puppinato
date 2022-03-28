package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.islandController.IslandContext;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerMoreInfluence;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.controller.professorController.ProfessorControllerModified;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

//MORE_INFLUENCE
//EXCHANGE
public class CharacterGroup3 extends Character{
    private CharacterType type;
    private int price;
    private boolean used;
    private IslandController island = new IslandControllerMoreInfluence();

    public CharacterGroup3 (CharacterType type){
        this.type=type;
        price = type.getPrice();
        used = false;
    }
    @Override
    public void returnNoEntryTiles() {

    }

    @Override
    public void activateCharacter(Context islandContext) {
        islandContext.changeContext(island);

    }

    @Override
    public void activateCharacter(Island island, PawnColor color, Player player) {

    }

    @Override
    public void activateCharacter(PawnColor color, Game game) {

    }

    @Override
    public void activateCharacter(Player player, PawnColor[] color) {
        //exchange
    }

    @Override
    public void activateCharacter(Island island) {

    }
}
