package it.polimi.ingsw.model.character;

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

//ADD_STUDENT_ISLAND
//ADD_STUDENT_DINING
//REPLACE
public class CharacterGroupStudent extends Character{
    private CharacterType type;
    private int price;
    private boolean used;
    private Bag bag;
    private List<Student> student;

    public CharacterGroupStudent (CharacterType type, Bag bag) throws BagIsEmptyException {
        this.type=type;
        price = type.getPrice();
        used = false;
        this.bag=bag;
        student=new LinkedList<>();
        student.addAll(bag.withdrawStudent(type.hasStudent()));

    }

    public Student removeStudent(PawnColor color){
        Student s = findStudent(color);
        student.remove(s);
        return s;
    }

    public Student findStudent(PawnColor color){
        Student stud = null;

        for (Student s : student){
            if (s.getColor().equals(color)) {
                stud = s;
                return s;
            }
        }
        return null;
    }

    public void addStudent(Student s) {
        student.add(s);
    }

    @Override
    public void returnNoEntryTiles() {

    }

    @Override
    public void activateCharacter(Context context) {

    }

    @Override
    public void activateCharacter(Island island, PawnColor color) {
        island.addStudent(removeStudent(color)); //add_student_islands

    }

    @Override
    public void activateCharacter(PawnColor color, Game game) {


    }
    @Override
    public void activateCharacter(Player player, PawnColor color) {
        player.getBoard().getDiningRoom().addStudent(removeStudent(color));

    }

    @Override
    public void activateCharacter(Player player, PawnColor[] color) {

    }

    @Override
    public void activateCharacter(Island island) {

    }
}
