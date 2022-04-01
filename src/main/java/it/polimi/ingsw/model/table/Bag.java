package it.polimi.ingsw.model.table;

import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Bag {

    private List<Student> student;

    public Bag()
    {
        student= new LinkedList<>();
        for(int i=0;i<5;i++)
            for(int j=0;j<24;j++)
                student.add(new Student(i));

        Collections.shuffle(student);
    }

    public LinkedList<Student> initialSetup(int numPlayer){
        LinkedList<Student> draw = new LinkedList<>();

        if (numPlayer == 2) {
            draw.addAll(student.subList(0, 7));
            student.subList(0, 7).clear();
        }
        else {
            draw.addAll(student.subList(0, 9));
            student.subList(0, 9).clear();
        }

        return draw;
    }

    public void addStudent(List<Student> student)
    {
        this.student.addAll(student);
        Collections.shuffle(this.student);
    }


    public List<Student> withdrawStudent(int numStudents) throws BagIsEmptyException {
        List<Student> drawOut;
        try {
            drawOut = new LinkedList<>(student.subList(0, numStudents));
        }
        catch (IndexOutOfBoundsException e){
            student.clear();
            throw new BagIsEmptyException();
        }
        student.subList(0, numStudents).clear();
        return drawOut;
    }


    public boolean isEmpty()
    {
        return student.size()==0;
    }
}
