package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.pawns.Tower;

import java.util.LinkedList;
import java.util.Set;

public class TowerCourt {
    private LinkedList<Tower> tower;

    public TowerCourt() {
        tower = new LinkedList<Tower>();
    }

    public LinkedList<Tower> getTower() {
        return tower;
    }

    public void removeTower(int numT){
        for(int i = 0; i < numT; i++)
                tower.remove(i);
    }
    public void addTower(int numT){
        for(int i = 0; i < numT; i++)
            tower.addLast(new Tower());
    }
}
