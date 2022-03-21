package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.pawns.Student;

import java.util.LinkedList;

public class Board {
    private Entrance entrance;
    private DiningRoom diningRoom;
    private ProfessorTable professorTable;
    private TowerCourt towercourt;

    public Board(LinkedList<Student> student){
        entrance = new Entrance(student);
        diningRoom = new DiningRoom();
        professorTable = new ProfessorTable();
        towercourt = new TowerCourt(6);
    }

    public Entrance getEntrance() {return entrance;}

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }

    public ProfessorTable getProfessorTable() {
        return professorTable;
    }

    public TowerCourt getTowercourt() {
        return towercourt;
    }
}
