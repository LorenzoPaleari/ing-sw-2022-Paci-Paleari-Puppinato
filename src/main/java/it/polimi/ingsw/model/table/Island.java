package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;

import java.util.*;

public class Island {
    private int weight;
    private Set<Student> islandStudent;
    private boolean motherNature;
    private boolean noEntryTiles;
    private int islandID;
    private Set<Tower> islandTower;

/*    public Island(int weight, Set<Student> islandStudent,boolean motherNature,boolean noEntryTiles,int islandID,Set<Tower> islandTower)
    {
        this.weight=weight;
        this.islandStudent=islandStudent;
        this.motherNature=motherNature;
        this.noEntryTiles=noEntryTiles;
        this.islandID=islandID;
        this.islandTower=islandTower;
    }*/

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Set<Student> getIslandStudent() {
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

    public Set<Tower> getIslandTower() { return islandTower; }

    public boolean hasMotherNature(int position)
    {
        return islandID==position;
    }

    public void addStudent(Student s)
    {
        islandStudent.add(s);
    }


}