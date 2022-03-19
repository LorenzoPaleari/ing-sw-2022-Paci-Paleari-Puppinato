package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Cloud {
    private int cloudID;
    private List<Student> cloudStudent;

    public int getCloudID() {
        return cloudID;
    }

    public List<Student> getCloudStudent() {
        return cloudStudent;
    }


    public void addStudent(List<Student> toAdd,int maxNumPlayer)
    {
        for (int i=0; i<=maxNumPlayer;i++)
            cloudStudent.add(toAdd.get(i));
    }
    public List<Student> removeAll(int maxNumPlayer)
    {
        List<Student> removed = new LinkedList<Student>();
        for (int i=0; i<+maxNumPlayer;i++)
            removed.add(cloudStudent.remove(i));
        cloudStudent.subList(0, maxNumPlayer).clear();
        return removed;
    }

}
