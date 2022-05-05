package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;
import it.polimi.ingsw.listeners.ModelListener;

import java.util.*;

public class Island {
    private int weight;
    private List<Student> islandStudent;
    private boolean motherNature;
    private boolean noEntryTiles;
    private List<Tower> islandTower;
    private ModelListener modelListener=null;

   public Island()
    {
        weight=1;
        islandStudent = new LinkedList<>();
        motherNature = false;
        noEntryTiles = false;
        islandTower = new LinkedList<>();
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

    public Integer[] countAll(){
        Integer[] colorsCount = new Integer[5];

        for (PawnColor p : PawnColor.values())
            colorsCount[p.getIndex()] = countStudent(p);

        return colorsCount;
    }

    public void addStudent(Student s) {
        islandStudent.add(s);
        notifyView();
    }

    public List<Tower> removeTower(){
       List<Tower> tower = new LinkedList<>(islandTower);
       islandTower.removeAll(tower);
       return tower;
    }

    public void addTower(List<Tower> tower){
       islandTower.addAll(tower);
    }

    public void attach(ModelListener modelListener){this.modelListener=modelListener;}
    public void notifyView() {
        if (modelListener != null)
            modelListener.update();
    }
    public void detach(){
       modelListener=null;
   }
}