package it.polimi.ingsw.model.player;

import java.util.*;

public class Deck {
    private List<Assistant> assistant;

    public Deck () {
        assistant = new ArrayList<Assistant>(10);

    }

    public Assistant removeAssistant(int weight)
    {
       return assistant.remove(weight);
    }

    public boolean isEmpty(){
        return assistant.size() == 0;
    }

}
