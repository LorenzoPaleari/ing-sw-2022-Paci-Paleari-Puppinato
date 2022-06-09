package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Entrance {
    private LinkedList<Student> student;

    /**
     * Constructor
     * Initialize the students list
     * @param stud
     */
    public Entrance(LinkedList<Student> stud) {
        this.student = stud;
    }

    /**
     * Gets the students list
     * @return
     */
    public LinkedList<Student> getStudent() {
        return student;
    }

    /**
     * Removes a student of the indicated color
     * @param c
     * @return
     */
    public Student removeStudent(PawnColor c){
        Student temp;
        temp = find(c);
        student.remove(temp);
        return temp;
    }

    /**
     * adds a list of students to the list student
     * @param s
     */
    public void addStudent(List<Student> s){
        student.addAll(s);
    }

    /**
     * adds a student to the list student
     * @param s
     */
    public void addStudent(Student s){
        student.add(s);
    }

    /**
     * returns a student of the indicated color
     * @param color
     * @return
     */
    public Student find (PawnColor color){
        for(Student s : student)
            if(s.getColor() == color) return s;
        return null;
    }

    /**
     * returns a list with the count of all the students
     * @return
     */
    public Integer[] countAll() {
        int count;
        Integer[] colorsCount = new Integer[5];

        for (PawnColor p : PawnColor.values()) {
            count = 0;
            for (Student s : student) {
                if (s.getColor().equals(p))
                    count++;
            }

            colorsCount[p.getIndex()] = count;
        }
        return colorsCount;
    }
}
