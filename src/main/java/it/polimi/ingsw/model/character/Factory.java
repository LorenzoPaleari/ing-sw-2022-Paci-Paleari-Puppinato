package it.polimi.ingsw.model.character;

import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.table.Bag;

public class Factory {

    public Factory(CharacterType type, Bag bag) throws BagIsEmptyException {
        if(type.equals(CharacterType.ADD_MOVES) || type.equals(CharacterType.NO_COLOR)) new CharacterGroup1(type);
        else if(type.equals(CharacterType.CONTROL_PROFESSOR) || type.equals(CharacterType.FAKE_MOTHER_NATURE)) new CharacterGroup2(type);
        else if(type.equals(CharacterType.MORE_INFLUENCE) || type.equals(CharacterType.STUDENT_EXCHANGE)) new CharacterGroup3(type);
        else if(type.equals(CharacterType.NO_TOWER) || type.equals(CharacterType.RETURN_STUDENT)) new CharacterGroup4(type);
        else if(type.equals(CharacterType.NO_ENTRY_TILES)) new CharacterNoEntryTiles(type);
        else if (type.equals(CharacterType.ADD_STUDENT_ISLAND) || type.equals(CharacterType.REPLACE_STUDENT) || type.equals(CharacterType.ADD_STUDENT_DINING)) new CharacterGroupStudent(type, bag);
    }
}
