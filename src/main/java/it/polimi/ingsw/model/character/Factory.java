package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.table.Bag;

public class Factory {
    public  Character getCharacter (CharacterType type, Bag bag) {
        Character character;
        if(type.equals(CharacterType.MAGIC_DELIVERY_MAN) || type.equals(CharacterType.MUSHROOM_HUNTER))
            character =new CharacterGroup1(type);
        else if(type.equals(CharacterType.FARMER) || type.equals(CharacterType.HERALD))
            character =new CharacterGroup2(type);
        else if(type.equals(CharacterType.KNIGHT) || type.equals(CharacterType.MINSTREL))
            character =new CharacterGroup3(type);
        else if(type.equals(CharacterType.CENTAUR) || type.equals(CharacterType.THIEF))
            character =new CharacterGroup4(type);
        else if(type.equals(CharacterType.GRANDMOTHER_HERBS))
            character =new CharacterNoEntryTiles(type);
        else
            character =new CharacterGroupStudent(type, bag);
        return character;
    }
}
