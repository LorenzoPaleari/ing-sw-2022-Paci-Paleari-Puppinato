package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.Listener.ModelListener;

public class Turn{
    private Player currentPlayer;
    private int remainingMovements;
    private boolean usedCharacter;
    private ModelListener modelListener=null;

    public Turn(Player player){
        usedCharacter = false;
        currentPlayer = player;
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
        if(usedCharacter)
            notifyView();
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

    public void attach(ModelListener modelListener){
        this.modelListener=modelListener;
        notifyView();
    }
    public void notifyView() {
        if (modelListener != null)
            modelListener.update();
    }
}
