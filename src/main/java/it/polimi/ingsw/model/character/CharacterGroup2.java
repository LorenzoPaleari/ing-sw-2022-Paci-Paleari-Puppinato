package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.TableHandler;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.controller.professorController.ProfessorControllerModified;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.table.Island;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//FARMER
//HERALD
public class CharacterGroup2 extends Character {

    private ProfessorController  professor= new ProfessorControllerModified();
    private Method updateIsland;
    public CharacterGroup2 (CharacterType type, Method updateIsland){
        super(type);
        this.updateIsland = updateIsland;
    }

    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {
        professorContext.changeContext(professor);
    }
    @Override
    public void activateCharacter(Island island, TableHandler tableHandler) {
        try {
            updateIsland.invoke(tableHandler, island);
        } catch (IllegalAccessException | InvocationTargetException ignored){}
    }

}
