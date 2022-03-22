package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.LinkedList;
import java.util.List;

public class DiningRoom {
    private LinkedList<Student> student;

    public DiningRoom() {
        student = new LinkedList<Student>();
    }

    public LinkedList<Student> getStudent() {
        return student;
    }

    public List<Student> removeStudent(PawnColor color, int numStudent){
        List<Student> list = new LinkedList<>();
        Student temp;
        for(int i = 0; i < numStudent; i++){
            temp = find(color);
            if(!temp.equals(null))
                list.add(temp);
        }
        student.removeAll(list);
        return list;

    }
    public void addStudent(Student s){
        student.addLast(s);
    }

    public boolean isEmpty(){
        return student.size() == 0;
    }

    public Student find(PawnColor color){
        Student stud;
        for(Student s : student){
            if(s.getColor() == color){
                return s;
            }
        }
        return null;
    }

    public LinkedList<Integer> countAll(){
        int cont;
        LinkedList<Integer> studCount = new LinkedList<Integer>();
        for(PawnColor color : PawnColor.values()){
            cont = 0;
            for(Student s: student){
                if(s.getColor() == color) cont++;
            }
            studCount.addLast(cont);
        }


        return null;
    }

    public int count(PawnColor c){
        int count = 0;
        for(Student s : student){
            if(s.getColor() == c){
                count++;
            }
        }
        return count;
    }
}
