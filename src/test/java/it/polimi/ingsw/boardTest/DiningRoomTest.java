package it.polimi.ingsw.boardTest;

import it.polimi.ingsw.model.board.DiningRoom;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiningRoomTest{
    private DiningRoom diningRoom;
    private LinkedList<Student> student;
    @BeforeEach
    public void setUp(){
        diningRoom = new DiningRoom();
        student = new LinkedList<>();

    }

    @Test
    void removeStudent() {
        LinkedList<Student> list = new LinkedList<>();
        list.add(new Student(4));
        list.add(new Student(4));
        diningRoom.addStudent(list);
        assertEquals(list, diningRoom.removeStudent(PawnColor.BLUE, 2));
    }
    @Test
    void addStudent(Student s) {
        diningRoom.addStudent(s);
        assertEquals(s.getColor(), diningRoom.getStudent().get(0).getColor());
    }
    @Test
    void addStudent(List<Student> s) {
        diningRoom.addStudent(s);
        assertEquals(s, diningRoom.getStudent());
    }

    @Test
    void isEmpty() {
        assertEquals(true, diningRoom.isEmpty());
        diningRoom.addStudent(new Student(0));
        assertEquals(false, diningRoom.isEmpty());

    }

    @Test
    void find(PawnColor color) {
        assertEquals(null, diningRoom.find(PawnColor.BLUE));
        diningRoom.addStudent(new Student(4));
        Student s = new Student(4);
        assertEquals(s, diningRoom.find(PawnColor.BLUE));
    }

    @Test
    void count(PawnColor c){
        assertEquals(0, diningRoom.count(PawnColor.BLUE));
        diningRoom.addStudent(new Student(4));
        diningRoom.addStudent(new Student(4));
        assertEquals(0, diningRoom.count(PawnColor.BLUE));
    }


}
