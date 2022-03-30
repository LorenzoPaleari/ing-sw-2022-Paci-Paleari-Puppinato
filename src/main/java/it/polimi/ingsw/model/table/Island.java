package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;

import java.util.*;

public class Island {
    private int weight;
    private List<Student> islandStudent;
    private boolean motherNature;
    private boolean noEntryTiles;
    private List<Tower> islandTower;

   public Island()
    {
        weight=1;
        islandStudent = new LinkedList<Student>();
        motherNature = false;
        noEntryTiles = false;
        islandTower = new LinkedList<Tower>();
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setNoEntryTiles(boolean noEntryTiles) {
        this.noEntryTiles = noEntryTiles;
    }

    public List<Student> getIslandStudent() {
        return islandStudent;
    }

    public boolean isMotherNature() {
        return motherNature;
    }

    public void setMotherNature(boolean motherNature) {
        this.motherNature = motherNature;
    }

    public boolean isNoEntryTiles() {
        return noEntryTiles;
    }

    public List<Tower> getIslandTower() { return islandTower; }

    public int countStudent(PawnColor c){
        int count = 0;
        for(Student s : islandStudent){
            if(s.getColor() == c){
                count++;
            }
        }
        return count;
    }

    public void addStudent(Student s)
    {
        islandStudent.add(s);
    }

    public List<Tower> removeTower(){
       List<Tower> tower = new LinkedList<>();
       tower.addAll(islandTower);
       islandTower.removeAll(tower);
       return tower;
    }

    public void addTower(List<Tower> tower){
       islandTower.addAll(tower);
    }

}