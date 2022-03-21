package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.LinkedList;
import java.util.Set;

public class DiningRoom {
    private LinkedList<Student> student;

    public DiningRoom() {
        student = new LinkedList<Student>();
    }

    public LinkedList<Student> getStudent() {
        return student;
    }

    public void removeStudent(Student s){
        student.remove(s);

    }
    public void addStudent(Student s){
        student.addLast(s);
    }

    public boolean isEmpty(){
        return student.size() == 0;
    }

    public Student find(PawnColor color){
        Student stud;
        for(Student s : student){
            if(s.getColor() == color){
                return s;
            }
        }
        return null;
    }

    public LinkedList<Integer> countAll(){
        return null;
    }

    public int count(PawnColor c){
        int count = 0;
        for(Student s : student){
            if(s.getColor() == c){
                count++;
            }
        }
        return count;
    }
}
