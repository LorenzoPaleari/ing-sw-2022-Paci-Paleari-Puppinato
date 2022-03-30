package it.polimi.ingsw.boardTest;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class BoardTest{
    @Test
    void getEntrance() {
        LinkedList<Student> stud = new LinkedList<>();
        Board board = new Board(stud, 3, TowerColor.BLACK);
        Entrance entrance = new Entrance(stud);
        Assertions.assertEquals(entrance, board.getEntrance());
    }

    @Test
    void getDiningRoom() {
        DiningRoom dining = new DiningRoom();
    }
    @Test
    void getProfessorTable() {
        ProfessorTable profTable = new ProfessorTable();
    }
    @Test
    void getTowerCourt() {
        TowerCourt towerCourt = new TowerCourt(8, TowerColor.BLACK);
    }
}
