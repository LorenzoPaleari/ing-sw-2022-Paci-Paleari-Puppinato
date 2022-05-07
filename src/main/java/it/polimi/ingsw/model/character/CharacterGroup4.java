package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.BoardHandler;
import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerNoTower;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.player.Player;

//CENTAUR
//RETURN
public class CharacterGroup4 extends Character{
    private IslandController island = new IslandControllerNoTower();
    public CharacterGroup4 (CharacterType type){
        super(type);
    }

    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {
        islandContext.changeContext(island);
    }

    @Override
    public void activateCharacter(Game game, Player player, PawnColor color, Context context, BoardHandler boardHandler) {
        Professor prof = game.getTable().findProfessor(color);
        for (Player p: game.getPlayers()){
                game.getTable().getBag().addStudent(p.getBoard().getDiningRoom().removeStudent(color, 3));
        }
        if (prof.getNumStudent()<3)
            prof.setNumStudent(0);
        else
            prof.setNumStudent(prof.getNumStudent() - 3);
    }
}
