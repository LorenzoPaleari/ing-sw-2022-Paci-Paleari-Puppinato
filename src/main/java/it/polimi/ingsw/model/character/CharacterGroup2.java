package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.controller.professorController.ProfessorControllerModified;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.table.Island;

//CONTROL_PROFESSOR
//FAKE_MOTHER_NATURE
public class CharacterGroup2 extends Character {

    private ProfessorController  professor= new ProfessorControllerModified();
    public CharacterGroup2 (CharacterType type){
        super(type);
    }

    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {
        professorContext.changeContext(professor);
    }
    @Override
    public void activateCharacter(Island island) {
        Controller.updateIsland(island);
    }

}
