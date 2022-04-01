package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;

import java.util.LinkedList;
import java.util.*;
import java.util.Set;

public class TowerCourt {
    private LinkedList<Tower> tower;

    public TowerCourt(int numTower, TowerColor towerColor) {
        tower = new LinkedList<>();
        for (int i = 0; i < numTower; i++)
            tower.add(new Tower(towerColor));
    }

    public boolean isEmpty(){
        return tower.isEmpty();
    }

    public LinkedList<Tower> getTower() {
        return tower;
    }

    public LinkedList<Tower> removeTower(int numT){
        LinkedList<Tower> drawOut = new LinkedList<>();
        try{
            drawOut.addAll(tower.subList(0, numT));
        } catch (IndexOutOfBoundsException e) {
            drawOut.addAll(tower);
            tower.clear();
        }
        tower.subList(0, numT).clear();
        return drawOut;
    }

    public void addTower(List<Tower> tower){
        this.tower.addAll(tower);
    }
}
