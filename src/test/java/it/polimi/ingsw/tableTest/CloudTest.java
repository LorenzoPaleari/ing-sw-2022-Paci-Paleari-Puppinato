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
    public void setUp(){
        cloud = new Cloud();
        student = new LinkedList<>();

    }
    @Test
    void getCloudStudent() {
        setUp();
        assertEquals(student, cloud.getCloudStudent());
    }

    @Test
    void addStudent() {
        setUp();
        List<Student> test= new LinkedList<>();
        test.add(new Student(1));
        test.add(new Student(1));
        test.add(new Student(0));

        cloud.addStudent(test);
        assertEquals(cloud.getCloudStudent().get(0).getColor(), PawnColor.RED);
        assertEquals(cloud.getCloudStudent().get(1).getColor(), PawnColor.RED);
        assertEquals(cloud.getCloudStudent().get(2).getColor(), PawnColor.GREEN);
    }

    @Test
    void removeAllStudent() {
        setUp();
        List<Student> test= new LinkedList<>();
        test.add(new Student(1));
        test.add(new Student(1));
        test.add(new Student(0));

        cloud.addStudent(test);
        assertEquals(cloud.removeAllStudent(), test);
    }
}
