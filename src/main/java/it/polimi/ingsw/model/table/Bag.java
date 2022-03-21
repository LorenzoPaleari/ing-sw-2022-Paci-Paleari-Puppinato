package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Bag {

    private List<Student> student;

    public Bag()
    {
        student= new LinkedList<Student>();
        for(int i=0;i<5;i++)
            for(int j=0;j<24;j++)
                student.add(new Student(i));
    }

    public LinkedList<Student> initialSetup(int numPlayer){
        LinkedList<Student> draw = new LinkedList<Student>();

        if (numPlayer == 2) {
            draw.addAll(student.subList(0, 6));
            student.subList(0, 6).clear();
        }
        else {
            draw.addAll(student.subList(0, 8));
            student.subList(0, 8).clear();
        }

        return draw;
    }

    public List<Student> withdrawStudent(int numStudents) {
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
