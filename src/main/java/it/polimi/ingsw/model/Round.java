package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.player.Player;
import java.util.*;

public class Round {
    private List<Player> playerSequence;
    private List<Player> nextSequence;
    private int numTurnDone;
    private Turn turn;
    private Boolean lastRound;

    public void setLastRound() {
        lastRound = true;
    }

    public Round(List<Player> player) {
        turn = new Turn();
        playerSequence = new LinkedList<>(player);
        nextSequence = new LinkedList<>(player);
        numTurnDone = 0;
    }

    public Turn getTurn(){
        return turn;
    }

    public Boolean getLastRound() {
        return lastRound;
    }

    public int getNumTurnDone() {
        return numTurnDone;
    }

    public boolean nextActionTurn(){
        int index = playerSequence.indexOf(turn.getCurrentPlayer());
        turn.getCurrentPlayer().changeState(PlayerState.WAIT);
        turn.setUsedCharacter(false);

        if (index == playerSequence.size() - 1)
            return false;
        else{
            Player p = playerSequence.get(index + 1);
            turn.updatePlayer(p);
            turn.resetRemainingMovements(playerSequence.size() + 1);
            p.changeState(PlayerState.ACTION);
            numTurnDone += 1;
            return true;
        }
    }

    public void endPlanningPhase(){
        int assistenteBasso = 11;
        for (Player p : playerSequence){
            if (p.getLastUsed().getWeight() < assistenteBasso){
                nextSequence.set(0, p);
            }
        }

        for (int i = 1; i <= 2; i++) {
            if (i <= playerSequence.size()) {
                nextSequence.set(i, getNextPlayer(nextSequence.get(i - 1)));
            }
        }

        playerSequence = nextSequence;
        playerSequence.get(0).changeState(PlayerState.ACTION);
        numTurnDone = playerSequence.size();
    }

    public boolean nextPlanningTurn(){
        int index = playerSequence.indexOf(turn.getCurrentPlayer());
        turn.getCurrentPlayer().changeState(PlayerState.WAIT);

        if (index == playerSequence.size() - 1)
            return false;
        else{
            Player p = playerSequence.get(index + 1);
            turn.updatePlayer(p);
            p.changeState(PlayerState.PLANNING);
            numTurnDone += 1;
            return true;
        }
    }

    public void endRound(){
            playerSequence.get(0).changeState(PlayerState.PLANNING);
            numTurnDone = playerSequence.size();
    }

    public Player getNextPlayer(Player p){
        if (playerSequence.indexOf(p) == playerSequence.size())
            return playerSequence.get(0);
        else
            return playerSequence.get(1 + playerSequence.indexOf(p));
    }

}
