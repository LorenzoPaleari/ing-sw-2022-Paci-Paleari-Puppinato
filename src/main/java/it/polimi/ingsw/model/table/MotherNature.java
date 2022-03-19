package it.polimi.ingsw.model.table;

public class MotherNature {
    private static MotherNature instance = null;
    private int positionID;

    private MotherNature(){}
    public static MotherNature getInstance(){ //...but allow clients to get an instance via a static method
        if(instance == null){
            instance = new MotherNature();
        }
        return instance;
    }

    public int getPositionID() {
        return positionID;
    }
}
