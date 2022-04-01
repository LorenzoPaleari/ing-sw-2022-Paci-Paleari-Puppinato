package it.polimi.ingsw.model.table;

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

    public List<Student> removeAllStudent()
    {
        List<Student> removed = new LinkedList<>(cloudStudent);
        cloudStudent.clear();
        return removed;
    }

}
