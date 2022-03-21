package it.polimi.ingsw.model.board;

public class Board {
    private Entrance entrance;
    private DiningRoom diningRoom;
    private ProfessorTable professorTable;
    private TowerCourt towercourt;

    public Board(){
        entrance = new Entrance();
        diningRoom = new DiningRoom();
        professorTable = new ProfessorTable();
        towercourt = new TowerCourt();
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
