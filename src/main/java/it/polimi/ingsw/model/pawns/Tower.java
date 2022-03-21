package it.polimi.ingsw.model.pawns;

import it.polimi.ingsw.model.enumerations.TowerColor;

public class Tower {
    private TowerColor color;

    public Tower(TowerColor towerColor){
        color = towerColor;
    }

    public TowerColor getColor() {
        return color;
    }
}
