package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Cloud {
    private List<Student> cloudStudent;

    public Cloud(){
        cloudStudent = new LinkedList<>();
    }

    public List<Student> getCloudStudent() {
        return cloudStudent;
    }

    public void addStudent(List<Student> toAdd)
    {
            cloudStudent.addAll(toAdd);
    }

    public Integer[] countAll(){
        Integer[] colorsCount = new Integer[5];

        for (PawnColor p : PawnColor.values())
            colorsCount[p.getIndex()] = countStudent(p);

        return colorsCount;
    }

    public int countStudent(PawnColor c){
        int count = 0;
        for(Student s : cloudStudent){
            if(s.getColor() == c){
                count++;
            }
        }
        return count;
    }

    public List<Student> removeAllStudent()
    {
        List<Student> removed = new LinkedList<>(cloudStudent);
        cloudStudent.clear();
        return removed;
    }

}
