package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerNoTower;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.player.Player;

//NO_TOWER
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
    public void activateCharacter(Game game, Player player, PawnColor color, Context context) {
        Professor prof = game.getTable().findProfessor(color);
        for (Player p: game.getPlayers()){
            if(p.getBoard().getDiningRoom().count(color)<3){
                game.getTable().getBag().addStudent(p.getBoard().getDiningRoom().removeStudent(color, p.getBoard().getDiningRoom().count(color)));
            }
            else {
                game.getTable().getBag().addStudent(p.getBoard().getDiningRoom().removeStudent(color, 3));
            }
        }
        prof.setNumStudent(player.getBoard().getDiningRoom().count(color));
    }
}
