package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

public class Turn{
    private Player currentPlayer;
    private int remainingMovements;
    private boolean usedCharacter;

    public Turn(){
        usedCharacter = false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }       //Getter

    public int getRemainingMovements() {
        return remainingMovements;
    }       //Getter

    public void resetRemainingMovements(int num) {
        remainingMovements = num;
    }       //Serve a resettare le mosse

    public void setUsedCharacter(boolean usedCharacter) {
        this.usedCharacter = usedCharacter;
    }

    public boolean isUsedCharacter() {
        return usedCharacter;
    }

    public void updatePlayer(Player p){
        currentPlayer = p;
    }

    public void updateRemainingMovements(){
        remainingMovements -= 1;
    }
}
