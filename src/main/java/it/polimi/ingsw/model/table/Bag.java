package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Bag {

    private List<Student> student;

    public List<Student> withdrawStudent(int numStudents)
            //TODO: n could be bigger than student.size()
    {
        List<Student> drawOut = new LinkedList<Student>();
        drawOut.addAll(student.subList(0,numStudents-1));
        student.subList(0, numStudents -1).clear();
        return drawOut;
    }


    public boolean isEmpty()
    {
        return student.size()==0;
    }
}
