package it.polimi.ingsw.controller.characterController;

import it.polimi.ingsw.controller.islandController.IslandContext;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerMoreInfluence;

//paci
//during the influence calculation this turn,
//you count as having 2 more influence
public class MoreInfluence extends Decorator{
    private IslandController islandController;

    public MoreInfluence(){
        islandController = new IslandControllerMoreInfluence();
    }
    @Override
    public void useCharacter() {
        IslandContext.changeIslandContext(islandController);
    }
}