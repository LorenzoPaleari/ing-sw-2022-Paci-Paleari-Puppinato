package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.BoardHandler;
import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Bag;
import it.polimi.ingsw.model.table.Island;

import java.util.*;

//MONK
//SPOILED_PRINCESS
//REPLACE
public class CharacterGroupStudent extends Character{
    private Bag bag;
    private List<Student> student;

    public CharacterGroupStudent (CharacterType type, Bag bag) {
        super(type);
        this.bag=bag;
        student=new LinkedList<>();
        student.addAll(bag.initialSetup(type.hasStudent()));

    }

    public Student removeStudent(PawnColor color){
        Student s = findStudent(color);
        student.remove(s);
        return s;
    }

    public Student findStudent(PawnColor color){
        Student stud = null;

        for (Student s : student)
            if (s.getColor().equals(color)) {
                stud = s;
                break;
            }

        return stud;
    }

    @Override
    public void addStudent(Student s) {
        student.add(0, s);
    }

    @Override
    public void activateCharacter(Game game, Player player, PawnColor color, Context context) throws BagIsEmptyException {
        player.getBoard().getDiningRoom().addStudent(removeStudent(color)); //add_student_dining
        BoardHandler.checkProfessor(player, color);
        student.addAll(bag.withdrawStudent(1));
    }
    @Override
    public void activateCharacter(Island island, PawnColor color) throws BagIsEmptyException {
        island.addStudent(removeStudent(color)); //add_student_islands

        student.addAll(bag.withdrawStudent(1));
    }
    @Override
    public void activateCharacter(Player player, PawnColor[] color) { // replace
        List<Student> list1 = new LinkedList<>();
        List<Student> list2 = new LinkedList<>();
        for(int i = 0; i < 3; i++){
            if(color[i] != null){
                list1.add(player.getBoard().getEntrance().removeStudent(color[i]));
            }
            if(color[i + 3] != null){
                list2.add(removeStudent(color[i+3]));
            }
        }
        player.getBoard().getEntrance().addStudent(list2);
        student.addAll(list1);

    }

    @Override
    public int count(PawnColor pawnColor){
        int count = 0;
        for(Student s: student)
            if(s.getColor().equals(pawnColor))
                count++;

        return count;
    }
}
