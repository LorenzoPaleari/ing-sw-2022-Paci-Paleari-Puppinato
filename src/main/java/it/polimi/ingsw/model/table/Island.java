package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;

import java.util.*;

public class Island {
    private int weight;
    private List<Student> islandStudent;
    private boolean motherNature;
    private boolean noEntryTiles;
    private int islandID;
    private List<Tower> islandTower;

   public Island(int islandID)
    {
        weight=1;
        islandStudent=new LinkedList<Student>();
        motherNature=false;
        noEntryTiles=false;
        this.islandID=islandID;
        islandTower=new LinkedList<Tower>();
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public List<Student> getIslandStudent() {
        return islandStudent;
    }

    public boolean isMotherNature() {
        return motherNature;
    }

    public boolean isNoEntryTiles() {
        return noEntryTiles;
    }

    public int getIslandID() {
        return islandID;
    }

    public List<Tower> getIslandTower() { return islandTower; }


    public boolean hasMotherNature(int position)
    {
        return islandID==position;
    }

    public void addStudent(Student s)
    {
        islandStudent.add(s);
    }


}