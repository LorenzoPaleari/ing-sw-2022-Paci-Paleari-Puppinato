package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Cloud {
    private final int cloudID;
    private List<Student> cloudStudent;

    public Cloud (int cloudID)
    {
        this.cloudID=cloudID;
    }

    public int getCloudID() {
        return cloudID;
    }

    public List<Student> getCloudStudent() {
        return cloudStudent;
    }

    public void addStudent(List<Student> toAdd)
    {
            cloudStudent.addAll(toAdd);
    }

    public List<Student> removeAllStudent(int maxNumPlayer)
    {
        List<Student> removed;
        removed=cloudStudent;
        cloudStudent.removeAll(removed);
        return removed;
    }

}
