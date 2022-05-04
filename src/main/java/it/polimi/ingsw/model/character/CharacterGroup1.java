package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.BoardHandler;
import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerNoColor;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureController;
import it.polimi.ingsw.controller.motherNatureController.MotherNatureControllerModified;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

//MAGIC_DELIVERY_MAN
//MUSHROOM_HUNTER
public class CharacterGroup1 extends Character{

    private IslandController islandController= new IslandControllerNoColor();
    private MotherNatureController motherNature = new MotherNatureControllerModified();

    public CharacterGroup1 (CharacterType type){
        super(type);
    }
    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {
        motherNatureContext.changeContext(motherNature);
    }

    @Override
    public void activateCharacter(Game game, Player player, PawnColor color, Context context, BoardHandler boardHandler) {
        islandController.setNoColor(color);
        context.changeContext(islandController);
    }
}
