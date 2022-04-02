package it.polimi.ingsw.boardTest;


import it.polimi.ingsw.model.board.DiningRoom;
import it.polimi.ingsw.model.board.TowerCourt;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TowerCourtTest {
    private TowerCourt towerCourt;
    private LinkedList<Tower> tower;
    @BeforeEach
    public void setUp(){
        towerCourt = new TowerCourt(6, TowerColor.BLACK);
        tower = new LinkedList<>();

    }
    @Test
    void isEmpty(){
        LinkedList<Tower> tower = new LinkedList<>();
        tower.add(new Tower(TowerColor.BLACK));
        assertEquals(true, towerCourt.isEmpty());
        towerCourt.addTower(tower);
        assertEquals(false, towerCourt.isEmpty());

    }

    @Test
    void removeTower(int numT){
        tower.add(new Tower(TowerColor.BLACK));
        tower.add(new Tower(TowerColor.BLACK));
        assertEquals(tower, towerCourt.removeTower(2));

    }

    @Test
    void addTower(List<Tower> tower){
        assertEquals(tower, towerCourt.getTower());
    }

}
