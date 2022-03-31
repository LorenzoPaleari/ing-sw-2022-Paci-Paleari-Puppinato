package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerNoTower;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

//NO_TOWER
//RETURN
public class CharacterGroup4 extends Character{
    private CharacterType type;
    private int price;
    private boolean used;
    private IslandController island = new IslandControllerNoTower();
    public CharacterGroup4 (CharacterType type){
        this.type=type;
        price = type.getPrice();
        used = false;
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

    }

    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {
        islandContext.changeContext(island);
    }

    @Override
    public void activateCharacter(Island island, PawnColor color) {

    }
    @Override
    public void activateCharacter(Game game, Player player, PawnColor color, Context context) {
        for (Player p: game.getPlayers()){
            game.getTable().getBag().addStudent(p.getBoard().getDiningRoom().removeStudent(color, 3));
        }

    }


    @Override
    public void activateCharacter(Player player, PawnColor[] color) {

    }

    @Override
    public void activateCharacter(Island island) {

    }
}
