package it.polimi.ingsw.controller.characterController;

public abstract class ExtraEffectDecorator extends BaseCharacter {
    protected Character character;

    @Override
    public abstract void useCharacter();

}
