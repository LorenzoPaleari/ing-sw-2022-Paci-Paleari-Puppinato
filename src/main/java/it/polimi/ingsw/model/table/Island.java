package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;

import java.util.*;

public class Island {
    private int weight;
    private String playerName;
    private Set<Student> islandStudent;
    private boolean motherNature;
    private boolean noEntryTiles;
    private int islandID;
    private Set<Tower> IslandTower;

    public int getWeight() {
        return weight;
    }

    public String getPlayerName() {
        return playerName;
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

    public Set<Tower> getIslandTower() { return IslandTower; }

    public boolean hasMotherNature(int position)
    {
        return islandID==position;
    }

    public int addStudent(Student s)
    {

    }


}