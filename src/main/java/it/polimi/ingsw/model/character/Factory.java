package it.polimi.ingsw.model.character;

import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.table.Bag;

public class Factory {
    public  Character getCharacter (CharacterType type, Bag bag) {
        Character character;
        if(type.equals(CharacterType.ADD_MOVES) || type.equals(CharacterType.NO_COLOR))
            character =new CharacterGroup1(type);
        else if(type.equals(CharacterType.CONTROL_PROFESSOR) || type.equals(CharacterType.FAKE_MOTHER_NATURE))
            character =new CharacterGroup2(type);
        else if(type.equals(CharacterType.MORE_INFLUENCE) || type.equals(CharacterType.STUDENT_EXCHANGE))
            character =new CharacterGroup3(type);
        else if(type.equals(CharacterType.NO_TOWER) || type.equals(CharacterType.RETURN_STUDENT))
            character =new CharacterGroup4(type);
        else if(type.equals(CharacterType.NO_ENTRY_TILES))
            character =new CharacterNoEntryTiles(type);
        else
            character =new CharacterGroupStudent(type, bag);
        return character;
    }
}
