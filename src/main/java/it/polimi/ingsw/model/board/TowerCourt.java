package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.pawns.Tower;

import java.util.LinkedList;
import java.util.Set;

public class TowerCourt {
    private LinkedList<Tower> tower;
    private int numInitialTowers;

    public TowerCourt(int numTower) {
        this.numInitialTowers = numTower;
        this.tower = new LinkedList<Tower>();
    }

    public LinkedList<Tower> getTower() {
        return tower;
    }

    public void removeTower(int numT){
        for(int i = 0; i < numT; i++)
                tower.removeLast();
    }
    public void addTower(int numT){
        for(int i = 0; i < numT; i++)
            tower.addLast(new Tower());
    }
}
