package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.pawns.Student;

import java.util.Set;

public class DiningRoom {
    private Set<Student> student;

    public void removeStudent(Student s){
        student.remove(s);

    }
    public void addStudent(Student s){
        student.add(s);
    }

    public boolean isEmpty(){
        return student.isEmpty();
    }
}
