package it.polimi.ingsw.model.table;

public class MotherNature {
    private static MotherNature instance = null;
    private int position;

    private MotherNature(){}

    public static MotherNature getInstance(){ //...but allow clients to get an instance via a static method
        if(instance == null){
            instance = new MotherNature();
        }
        return instance;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
