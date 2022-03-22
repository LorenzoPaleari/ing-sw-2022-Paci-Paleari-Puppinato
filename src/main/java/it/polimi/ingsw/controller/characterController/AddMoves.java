package it.polimi.ingsw.controller.characterController;

import it.polimi.ingsw.controller.motherNatureController.MotherNatureContext;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureControllerModified;

//paci
//you may move mother nature up to 2 additional islands
//than is indicated by the assistant card you've played
public class AddMoves extends Decorator{
    private MotherNatureController motherNatureController;

    public AddMoves(){
        motherNatureController = new MotherNatureControllerModified();
    }
    @Override
    public void useCharacter() {
        MotherNatureContext.changeMotherNatureContext(motherNatureController);
    }
}
