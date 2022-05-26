package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.BoardHandler;
import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.islandStrategy.IslandStrategy;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

//KNIGHT
//EXCHANGE
public class CharacterGroup3 extends Character{
    private IslandStrategy island;
    private Method checkProfessor;

    public CharacterGroup3 (CharacterType type, Method checkProfessor, IslandStrategy island) {
        super(type);
        this.checkProfessor = checkProfessor;
        this.island = island;
    }

    @Override
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext) {
        islandContext.changeContext(island);
    }

    @Override
    public void activateCharacter(Player player, PawnColor[] color, BoardHandler boardHandler) {
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
                Professor prof = player.getBoard().getProfessorTable().find(color[i+2]);
                if (null != prof)
                    prof.setNumStudent(prof.getNumStudent() - 1);
            }
        }
        player.getBoard().getEntrance().addStudent(list2);
        //player.getBoard().getDiningRoom().addStudent(list1);
        for (Student s : list1) {
            if (player.getBoard().getDiningRoom().count(s.getColor()) % 3 == 0)
                player.addCoin();
            try {
                checkProfessor.invoke(boardHandler, player, s.getColor());
            } catch (InvocationTargetException | IllegalAccessException ignored) {
            }
            player.getBoard().getDiningRoom().addStudent(s);
        }
    }

}
