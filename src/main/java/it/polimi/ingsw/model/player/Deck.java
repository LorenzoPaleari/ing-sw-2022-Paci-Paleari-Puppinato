package it.polimi.ingsw.model.player;

import java.util.*;

public class Deck {
    private List<Assistant> assistant;
    private int size;

    public Deck () {
        assistant = new ArrayList<Assistant>(10);
        for(int i=1; i<=10; i++)
            assistant.add(new Assistant(i));

        size = 10;
    }

    public Assistant removeAssistant(int position)
    {
        size -= 1;
        return assistant.remove(position);
    }

    public int getSize() {
        return size;
    }

    public Assistant getAssistant(int position){
        return assistant.get(position);
    }

    public List<Assistant> getAssistant(){
        return assistant;
    }

    public boolean isEmpty(){
        return assistant.size() == 0;
    }

}
