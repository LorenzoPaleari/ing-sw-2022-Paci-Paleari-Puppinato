package it.polimi.ingsw.controller.characterController;

import it.polimi.ingsw.controller.islandController.IslandContext;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerNoTower;

//paci
//when resolving a conqueringon an island,
//towers do not count towards influence
public class NoTower extends Decorator{
    private IslandController islandController;

    public NoTower(){
        islandController = new IslandControllerNoTower();
    }
    @Override
    public void useCharacter() {
        IslandContext.changeIslandContext(islandController);
    }
}