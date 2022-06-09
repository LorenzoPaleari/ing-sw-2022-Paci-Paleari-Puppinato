package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.listeners.ModelListener;

public class Turn{
    private Player currentPlayer;
    private int remainingMovements;
    private boolean usedCharacter;
    private ModelListener modelListener=null;

    /**
     * Constructor
     * Sets the current player
     * @param player
     */
    public Turn(Player player){
        usedCharacter = false;
        currentPlayer = player;
    }

    /**
     * Gets current player
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets remaining moves
     * @return
     */
    public int getRemainingMovements() {
        return remainingMovements;
    }

    /**
     * Reset remaining moves
     * @param num
     */
    public void resetRemainingMovements(int num) {
        remainingMovements = num;
    }

    /**
     * Sets used character
     * @param usedCharacter
     */
    public void setUsedCharacter(boolean usedCharacter) {
        this.usedCharacter = usedCharacter;
        if(usedCharacter)
            notifyView();
    }

    /**
     * Returns true if a character has been used
     * @return
     */
    public boolean isUsedCharacter() {
        return usedCharacter;
    }

    /**
     * Sets the new current player
     * @param p
     */
    public void updatePlayer(Player p){
        currentPlayer = p;
    }

    /**
     * Updates the remaining moves
     */
    public void updateRemainingMovements(){
        remainingMovements -= 1;
    }

    /**
     *
     * @param modelListener
     */
    public void attach(ModelListener modelListener){
        this.modelListener=modelListener;
        notifyView();
    }

    /**
     *
     */
    public void notifyView() {
        if (modelListener != null)
            modelListener.update();
    }
}
