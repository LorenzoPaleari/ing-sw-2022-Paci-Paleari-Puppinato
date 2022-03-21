package it.polimi.ingsw.model.player;

import java.util.*;

public class Deck {
    private List<Assistant> assistant;

    public Deck () {
        assistant = new ArrayList<Assistant>(10);
        for(int i=1; i<=10; i++)
            assistant.add(new Assistant(i));
    }

    public Assistant removeAssistant(int position)
    {
       return assistant.remove(position);
    }

    public boolean isEmpty(){
        return assistant.size() == 0;
    }

}
