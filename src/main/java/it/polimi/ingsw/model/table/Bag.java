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

    public LinkedList<Student> initialSetup(int quantity){
        LinkedList<Student> draw = new LinkedList<>();

        draw.addAll(student.subList(0, quantity));
        student.subList(0, quantity).clear();

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

    public List<Student> getStudent() {
        return student;
    }
}
