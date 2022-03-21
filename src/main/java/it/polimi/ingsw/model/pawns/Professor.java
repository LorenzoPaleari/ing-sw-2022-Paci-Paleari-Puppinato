package it.polimi.ingsw.model.pawns;

import it.polimi.ingsw.model.enumerations.PawnColor;

public class Professor {
    private PawnColor color;
    private int numStudent;

    public Professor(){
        color = null;
        numStudent = 0;
    }

    public PawnColor getColor() {
        return color;
    }

    public int getNumStudent() {
        return numStudent;
    }
}
