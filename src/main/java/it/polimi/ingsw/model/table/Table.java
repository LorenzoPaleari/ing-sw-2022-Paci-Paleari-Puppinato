package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Table {
    private int generalSupply;
    private List<Cloud> cloud;
    private List<Island> island;
    private List<Character> character;
    private Bag bag;
    private List<Professor> professor;

    public int getNumIsland () {return island.size();}
    public void addCoin(int numCoin) {generalSupply=generalSupply+numCoin;}
    public void withdrawCoin(int numCoin) {generalSupply=generalSupply-numCoin;} //TODO: generalSupply<0?

    public void mergeIsland (int position)
    {
        int positionNext;
        int positionPrev;

        if(position==0)
        {
            positionPrev=getNumIsland()-1;
            positionNext=1;
        }
        else if(position==getNumIsland()-1)
        {
            positionPrev=position-1;
            positionNext=0;
        }
        else
        {
            positionPrev=position-1;
            positionNext=position+1;
        }

        Island islandPrev= island.get(positionPrev);
        Island islandCurr= island.get(position);
        Island islandNext= island.get(positionNext);

        updateIsland(islandPrev, islandCurr);
        updateIsland(islandNext, islandCurr);
        island.add(islandCurr);

    }

    public void updateIsland(Island islandNotCurr, Island islandCurr) {
        if(islandNotCurr.getIslandTower().getTowerColor()==islandCurr.getIslandTower().getTowerColor() )
        {
            islandCurr.setWeight(islandNotCurr.getWeight()+islandCurr.getWeight());
            islandCurr.getIslandStudent().addAll(islandNotCurr.getIslandStudent());
            //no entry tiles?
            islandCurr.getIslandTower() .addAll(islandNotCurr.getIslandTower());
            island.remove(islandNotCurr);
        }
    }

}


