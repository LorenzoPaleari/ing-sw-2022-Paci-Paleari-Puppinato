package it.polimi.ingsw.controller.characterController;

import it.polimi.ingsw.model.character.Character;

public abstract class ExtraEffectDecorator extends Character {
    protected it.polimi.ingsw.controller.characterController.Character character;

    @Override
    public abstract void useCharacter();

}
