package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

public class Turn{
    private Player currentPlayer;
    private int remainingMovements;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }       //Getter

    public int getRemainingMovements() {
        return remainingMovements;
    }       //Getter

    public void resetRemainingMovements(int num) {
        remainingMovements = num;
    }       //Serve a resettare le mosse

    public void updatePlayer(Player p){
        currentPlayer = p;
    }

    public void updateRemainingMovements(){
        remainingMovements -= 1;
    }
}
