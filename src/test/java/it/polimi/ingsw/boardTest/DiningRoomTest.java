package it.polimi.ingsw.boardTest;

import it.polimi.ingsw.model.board.DiningRoom;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiningRoomTest{
    private DiningRoom diningRoom;
    @BeforeEach
    public void setUp(){
        diningRoom = new DiningRoom();
    }
    @AfterEach
    public void tearDown(){
        diningRoom = null;
    }

    @Test
    void removeStudent() {
        List<Student> list = new LinkedList<>();
        list.add(new Student(4));
        list.add(new Student(4));
        diningRoom.addStudent(list);
        List<Student> temp = diningRoom.removeStudent(PawnColor.BLUE, 2);
        assertEquals(temp, list);
        assertEquals(diningRoom.getStudent().size(), 0);
    }
    @Test
    void addStudent() {
        Student s = new Student(0);
        diningRoom.addStudent(s);
        assertEquals(s, diningRoom.getStudent().get(0));
        diningRoom.removeStudent(PawnColor.GREEN, 1);
        List<Student> list = new LinkedList<>();
        list.add(new Student(0));
        list.add(new Student(0));
        diningRoom.addStudent(list);
        assertEquals(list, diningRoom.getStudent());
    }

    @Test
    void isEmpty() {
        assertEquals(true, diningRoom.isEmpty());
        diningRoom.addStudent(new Student(0));
        assertEquals(false, diningRoom.isEmpty());

    }

    @Test
    void find() {
        assertEquals(null, diningRoom.find(PawnColor.BLUE));
        Student s = new Student(4);
        diningRoom.addStudent(s);

        assertEquals(s, diningRoom.find(PawnColor.BLUE));
    }

    @Test
    void count(){
        assertEquals(0, diningRoom.count(PawnColor.BLUE));
        diningRoom.addStudent(new Student(4));
        diningRoom.addStudent(new Student(4));
        assertEquals(2, diningRoom.count(PawnColor.BLUE));
    }


}
