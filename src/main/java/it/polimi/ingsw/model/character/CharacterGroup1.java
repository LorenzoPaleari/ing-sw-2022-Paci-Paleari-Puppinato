package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.BoardHandler;
import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.islandStrategy.IslandStrategy;
import it.polimi.ingsw.controller.islandStrategy.IslandStrategyMushroomHunter;
import it.polimi.ingsw.controller.motherNatureStrategy.MotherNatureStrategy;
import it.polimi.ingsw.controller.motherNatureStrategy.MotherNatureStrategyMagicDeliveryMan;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

//MAGIC_DELIVERY_MAN
//MUSHROOM_HUNTER
public class CharacterGroup1 extends Character{

    private IslandStrategy islandStrategy = new IslandStrategyMushroomHunter();
    private MotherNatureStrategy motherNature = new MotherNatureStrategyMagicDeliveryMan();

    public CharacterGroup1 (CharacterType type){
        super(type);
    }
    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {
        motherNatureContext.changeContext(motherNature);
    }

    @Override
    public void activateCharacter(Game game, Player player, PawnColor color, Context context, BoardHandler boardHandler) {
        islandStrategy.setNoColor(color);
        context.changeContext(islandStrategy);
    }
}
