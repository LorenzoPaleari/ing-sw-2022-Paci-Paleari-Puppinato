package it.polimi.ingsw.tableTest;

import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.exceptions.GeneralSupplyFinishedException;
import it.polimi.ingsw.model.character.Character;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;
import it.polimi.ingsw.model.table.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private int generalSupply;
    private List<Cloud> cloud;
    private List<Island> island;
    private List<Character> character;
    private Bag bag;
    private List<Professor> professor;
    private Table table;
    private MotherNature motherNature;

    TableTest() {
    }

    @BeforeEach
    void setUp() {
        table= new Table(3, true);
    }

    @Test
    void fillCloud() throws BagIsEmptyException {
        table.getCloud(0).removeAllStudent();
        table.getCloud(1).removeAllStudent();
        table.getCloud(2).removeAllStudent();
        assertEquals(table.getCloud(0).getCloudStudent().size(), 0);
        table.fillCloud(3,table.getBag().withdrawStudent(12));
        for (int i=0; i<3; i++) {
           assertEquals(table.getCloud(i).getCloudStudent().size(),4);
        /*    List<Student> test = new LinkedList<>(table.getCloud(i).getCloudStudent());
            for(Student s: test){
                System.out.println(s.getColor());
            }*/
        }

    }

    @Test
    void withdrawCoin() throws GeneralSupplyFinishedException {
        table.withdrawCoin();
        assertEquals(table.getGeneralSupply(), 16);
        table.addCoin(3);
        assertEquals(table.getGeneralSupply(), 19);
        ;
    }

    @Test
    void findProfessor() {
    }

    @Test
    void moveMotherNature() {
        MotherNature.getInstance().setPosition(9);
        table.moveMotherNature(3);
        assertEquals(table.getMotherPosition(), 0);
    }

    @Test
    void mergeIsland() {
        table.mergeIsland(0);
        assertEquals(table.getNumIsland(), 12, "no Island was merged");
        List<Tower> test1= new LinkedList<>();
        test1.add(new Tower(TowerColor.BLACK));
        test1.add(new Tower(TowerColor.BLACK));
        table.getIsland(0).addTower(test1);
        table.getIsland(0).addStudent(new Student(0));
        table.mergeIsland(0);
        assertEquals(table.getNumIsland(), 12, "no Island was merged because next and previous island don't have tower");
        List<Tower> test2= new LinkedList<>();
        test2.add(new Tower(TowerColor.WHITE));
        table.getIsland(1).addTower(test2);
        table.getIsland(1).addStudent(new Student(0));
        table.mergeIsland(0);
        assertEquals(table.getNumIsland(), 12, "no Island was merged because tower color are different");
        List<Tower> test3= new LinkedList<>();
        test3.add(new Tower(TowerColor.BLACK));
        table.getIsland(11).addTower(test3);
        table.getIsland(11).addStudent(new Student(2));
        table.getIsland(10).addTower(test3);
        table.getIsland(10).addStudent(new Student(3));
        table.mergeIsland(11);
        assertEquals(table.getNumIsland(), 10);
        assertEquals(table.getIsland(9).getIslandTower().size(), 4);
        assertEquals(table.getIsland(9).getIslandStudent().size(), 5);



    }
}