package it.polimi.ingsw.boardTest;

import it.polimi.ingsw.model.board.Entrance;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntranceTest{
        LinkedList<Student> stud;
        Entrance entrance;

        @BeforeEach
        public void setUp(){
                stud = new LinkedList<>();
                entrance = new Entrance(stud);

        }

        @Test
        void removeStudent(PawnColor c) {
                setUp();
                Student temp = new Student(4);
                assertEquals(temp, entrance.removeStudent(PawnColor.BLUE));
        }

        @Test
        void addStudent(List<Student> s) {
                setUp();
                assertEquals(s, entrance.getStudent());
        }
        @Test
        void addStudent(Student s) {
                setUp();
                assertEquals(true, entrance.getStudent().contains(s));
        }

        @Test
        void find(PawnColor color) {
                setUp();
                Student temp = new Student(4);
                assertEquals(temp, entrance.find(PawnColor.BLUE));
        }


}
