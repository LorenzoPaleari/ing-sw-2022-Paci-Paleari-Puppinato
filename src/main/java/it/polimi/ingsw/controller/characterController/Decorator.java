package it.polimi.ingsw.controller.characterController;

public abstract class Decorator extends BaseCharacter {
    protected BaseCharacter baseCharacter;

    @Override
    public abstract void useCharacter();

}