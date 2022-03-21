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

    public Table(int numPlayer,boolean expert)
    {

        cloud=new LinkedList<Cloud>();
        for(int i=1;i<=numPlayer;i++)
            cloud.add(new Cloud(i));
        island=new LinkedList<Island>();
        for(int i=1;i<=numPlayer;i++)
            island.add(new Island(i));


        bag=new Bag();
        for(int i=0;i<5;i++)
            for(int j=0;j<24;j++)


        if(expert)
        {
            generalSupply=20-numPlayer;
            character=new LinkedList<Character>();
        }

    }

    public Bag getBag\ {
        return bag;
    }

    public Island getIsland(int position)
    {
        return island.get(position);
    }


    public int getNumIsland () {return island.size();}
    public void addCoin(int numCoin) {generalSupply=generalSupply+numCoin;}
    public void withdrawCoin(int numCoin) {generalSupply=generalSupply-numCoin;} //TODO: generalSupply<0?
    public Professor findProfessor(PawnColor color)
    {
        for(Professor p: professors)
        {
         if(p.getColor()==color)
         {
          return p;
         }
        }
        return null;
    }

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


