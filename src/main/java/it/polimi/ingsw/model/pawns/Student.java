package it.polimi.ingsw.model.pawns;

import it.polimi.ingsw.model.enumerations.PawnColor;
import java.util.*;

public class Student {
    private PawnColor color;

    public Student(int i){
        color = PawnColor.getColor(i);
    }

    public PawnColor getColor() {
        return color;
    }
}
