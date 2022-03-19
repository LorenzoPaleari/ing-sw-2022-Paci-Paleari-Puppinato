package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.pawns.Student;

import java.util.Set;

public class Entrance {
    private Set<Student> student;

    public void removeStudent(Student s){
        student.remove(s);
    }
    public void addStudent(Set<Student> s){
        student.addAll(s);
    }
    public void addStudent(Student s){
        student.add(s);
    }
}
