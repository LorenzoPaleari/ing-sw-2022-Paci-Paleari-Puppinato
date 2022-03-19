package it.polimi.ingsw.model.table;

import java.util.List;

public class Table {
    private int generalSupply;
    private List<Cloud> cloud;
    private List<Island> island;
    private List<Character> character;

    public int getNumIsland () {return island.size();}
    public void addCoin(int numCoin) {generalSupply=generalSupply+numCoin;}
    public void withdrawCoin(int numCoin) {generalSupply=generalSupply-numCoin;} //TODO: generalSupply<0?


    public void updateIsland() {}
    public Island mergeIsland (int islandID){}
    public int removeIsland (int islandID){}

}
