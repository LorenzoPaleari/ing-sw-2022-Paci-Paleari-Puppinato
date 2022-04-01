package it.polimi.ingsw.tableTest;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.table.Cloud;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    private Cloud cloud;
    private List<Student> student;

    @BeforeEach
    public void setup(){
        cloud = new Cloud();
        student = new LinkedList<>();

    }
    @Test
    void getCloudStudent() {
        setup();
        assertEquals(student, cloud.getCloudStudent());
    }

    @Test
    void addStudent() {
        setup();
        List<Student> test= new LinkedList<>();
        test.add(new Student(1));
        test.add(new Student(1));
        test.add(new Student(0));

        cloud.addStudent(test);
        assertEquals(test.get(0).getColor(), PawnColor.RED);
        assertEquals(test.get(1).getColor(), PawnColor.RED);
        assertEquals(test.get(2).getColor(), PawnColor.GREEN);
    }

    @Test
    void removeAllStudent() {
        setup();
        List<Student> test= new LinkedList<>();
        test.add(new Student(1));
        test.add(new Student(1));
        test.add(new Student(0));

        cloud.addStudent(test);
        assertEquals(cloud.removeAllStudent(), test);
    }
}
