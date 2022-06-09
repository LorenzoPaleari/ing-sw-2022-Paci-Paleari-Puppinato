package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Tower;
import java.util.*;

public class TowerCourt {
    private LinkedList<Tower> tower;

    /**
     * Constructor
     * Initialize the number of towers and the color
     * @param numTower
     * @param towerColor
     */
    public TowerCourt(int numTower, TowerColor towerColor) {
        tower = new LinkedList<>();
        for (int i = 0; i < numTower; i++)
            tower.add(new Tower(towerColor));
    }

    /**
     * returns true if the tower list is empty
     * @return
     */
    public boolean isEmpty(){
        return tower.isEmpty();
    }

    /**
     * returns the list of towers
     * @return
     */
    public LinkedList<Tower> getTower() {
        return tower;
    }

    /**
     * removes the indicated number of towers from the list
     * @param numT
     * @return
     */
    public LinkedList<Tower> removeTower(int numT){
        LinkedList<Tower> drawOut = new LinkedList<>();
        try{
            drawOut.addAll(tower.subList(0, numT));
            tower.subList(0, numT).clear();
        } catch (IndexOutOfBoundsException e) {
            drawOut.addAll(tower);
            tower.clear();
        }

        return drawOut;
    }

    /**
     * adds a list of towers to the list
     * @param tower
     */
    public void addTower(List<Tower> tower){
        this.tower.addAll(tower);
    }
}
