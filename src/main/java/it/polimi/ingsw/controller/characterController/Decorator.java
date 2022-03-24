package it.polimi.ingsw.controller.characterController;

import it.polimi.ingsw.model.character.Character;

public abstract class Decorator extends Character {
    protected Character character;

    @Override
    public abstract void useCharacter();

}