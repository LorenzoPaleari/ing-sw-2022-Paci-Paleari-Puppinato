package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.controller.islandController.IslandControllerMoreInfluence;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

import java.util.LinkedList;
import java.util.List;

//MORE_INFLUENCE
//EXCHANGE
public class CharacterGroup3 extends Character{
    private CharacterType type;
    private int price;
    private boolean used;
    private IslandController island = new IslandControllerMoreInfluence();

    public CharacterGroup3 (CharacterType type){
        this.type=type;
        price = type.getPrice();
        used = false;
    }
    @Override
    public void returnNoEntryTiles() {

    }
    @Override
    public void activateCharacter(Game game, Player player, PawnColor color, Context context) {

    }

    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {
        islandContext.changeContext(island);

    }

    @Override
    public void activateCharacter(Island island, PawnColor color) {

    }


    @Override
    public void activateCharacter(Player player, PawnColor[] color) {
        List<Student> list1 = new LinkedList<>();
        List<Student> list2 = new LinkedList<>();
        //prima entrance, poi dining
        //exchange. you may exchange up to 2 students between your Entrance and your dining room
        for(int i = 0; i < 2; i++){
            if(color[i] != null){
                list1.add(player.getBoard().getEntrance().removeStudent(color[i]));
            }
            if(color[i + 2] != null){
                list2.addAll(player.getBoard().getDiningRoom().removeStudent(color[i+2], 1));
            }
        }
        player.getBoard().getEntrance().addStudent(list2);
        player.getBoard().getDiningRoom().addStudent(list1);

    }

    @Override
    public void activateCharacter(Island island) {

    }
}
