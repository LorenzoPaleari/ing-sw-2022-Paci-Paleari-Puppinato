package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.LinkedList;

public class Board {
    private Entrance entrance;
    private DiningRoom diningRoom;
    private ProfessorTable professorTable;
    private TowerCourt towercourt;

    public Board(LinkedList<Student> student, int numPlayer, TowerColor towerColor){
        entrance = new Entrance(student);
        diningRoom = new DiningRoom();
        professorTable = new ProfessorTable();
        if (numPlayer == 2)
            towercourt = new TowerCourt(8, towerColor);
        else
            towercourt = new TowerCourt(6, towerColor);
    }

    public Entrance getEntrance() {return entrance;}

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }

    public ProfessorTable getProfessorTable() {
        return professorTable;
    }

    public TowerCourt getTowerCourt() {
        return towercourt;
    }
}
