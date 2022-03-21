package it.polimi.ingsw.model.player;

public class Assistant {

    private final int weight;
    private final int numMovement;

    public Assistant(int weight)
    {
        this.weight=weight;
        numMovement=(weight/2 + weight%2);
    }

    public int getNumMovement() {
        return numMovement;
    }

    public int getWeight() {
        return weight;
    }
}
