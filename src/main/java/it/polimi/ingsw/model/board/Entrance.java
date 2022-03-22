package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.LinkedList;
import java.util.*;

public class Entrance {
    private LinkedList<Student> student;

    public Entrance(LinkedList<Student> stud) {
        this.student = stud;
    }

    public LinkedList<Student> getStudent() {
        return student;
    }

    public Student removeStudent(PawnColor c){
        Student temp;
        for(Student s : student){
            if(s.getColor() == c){
                temp = s;
                student.remove(s);
                return temp;
            }
        }
        return null;
    }
    public void addStudent(List<Student> s){

        student.addAll(s);
    }
    public void addStudent(Student s){

        student.add(s);
    }

    public Student find (PawnColor color){
        for(Student s : student){
            if(s.getColor() == color) return s;
        }
        return null;
    }
}
