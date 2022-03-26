package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Character_TEMP {
    private int costo;
    private CharacterType type;
    private List<Student> student;
    private int numNoEntryTiles;
    private boolean used;

    public Character_TEMP(CharacterType type){
        this.type = type;
        costo = type.getCosto();
        used = false;
        numNoEntryTiles = 0;
        student = new LinkedList<Student>();

        if(type.equals(CharacterType.NO_ENTRY_TILES))
            numNoEntryTiles = 4;
    }

    public Character_TEMP(CharacterType type, List<Student> student){
        this.type = type;
        costo = type.getCosto();;
        used = false;
        numNoEntryTiles = 0;
        this.student = new LinkedList<>();
        this.student = student;
    }

    public void firstUse(){
        costo += 1;
        used = true;
    }

    public int getCosto() {
        return costo;
    }

    public CharacterType getType() {
        return type;
    }

    public int getNumNoEntryTiles() {
        return numNoEntryTiles;
    }

    public void returnNoEntryTiles(){
        numNoEntryTiles += 1;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isEmpty(){
        boolean value = false;

        if (numNoEntryTiles == 0 && student.size() == 0)
            value = true;

        return value;
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
}