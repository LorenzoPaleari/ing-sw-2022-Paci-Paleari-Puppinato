package it.polimi.ingsw.model.board;

import it.polimi.ingsw.listeners.ModelListener;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.LinkedList;
import java.util.List;

public class DiningRoom {
    private LinkedList<Student> student;
    private ModelListener modelListener;
    private boolean listen;

    public DiningRoom() {
        student = new LinkedList<>();
        listen = false;
    }

    public LinkedList<Student> getStudent() {
        return student;
    }

    public List<Student> removeStudent(PawnColor color, int numStudent){
        List<Student> list = new LinkedList<>();
        Student temp;
        for(int i = 0; i < numStudent; i++){
            temp = find(color);
            if(temp != null)
                list.add(temp);
        }
        student.removeAll(list);
        return list;

    }
    public void addStudent(Student s){
        student.addLast(s);
        notifyView();
    }

    public void addStudent(List<Student> s){
        student.addAll(s);
    }

    public boolean isEmpty(){
        return student.size() == 0;
    }

    public Student find(PawnColor color){
        Student temp;
        for(Student s : student)
            if(s.getColor() == color){
                temp = s;
                student.remove(s);
                return temp;
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

    public Integer[] countAll() {
        Integer[] colorsCount = new Integer[5];

        for (PawnColor p : PawnColor.values())
            colorsCount[p.getIndex()] = count(p);

        return colorsCount;
    }

    public void attach(ModelListener modelListener){this.modelListener=modelListener;
    listen = true;}
    public void notifyView() {
        if (listen)
            modelListener.update();
    }
    public void detach(){
        listen=false;
    }
    public void reattach(){listen = true;}
}
