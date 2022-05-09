package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.TableHandler;
import it.polimi.ingsw.controller.professorStrategy.ProfessorStrategy;
import it.polimi.ingsw.controller.professorStrategy.ProfessorStrategyFarmer;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.table.Island;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//FARMER
//HERALD
public class CharacterGroup2 extends Character {

    private ProfessorStrategy professor= new ProfessorStrategyFarmer();
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
