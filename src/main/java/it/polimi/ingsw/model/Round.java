package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.player.Player;
import java.util.*;

public class Round {
    private List<Player> playerSequence;
    private List<Player> playerSequenceAscend;
    private List<Player> nextSequence;
    private List<Integer> cloudChosen;
    private int numTurnDone;
    private Turn turn;
    private Boolean lastRound;

    public void setLastRound() {
        lastRound = true;
    }

    public Round(List<Player> player) {
        turn = new Turn(player.get(0));
        playerSequence = new LinkedList<>(player);
        nextSequence = new LinkedList<>(player);
        playerSequenceAscend = new LinkedList<>(player);
        cloudChosen = new ArrayList<>();
        numTurnDone = 0;
        lastRound = false;
    }

    public Turn getTurn(){
        return turn;
    }

    public List<Integer> getCloudChosen() {
        return cloudChosen;
    }

    public void setCloudChosen(int cloudChosen) {
        this.cloudChosen.add(cloudChosen);
    }

    public Boolean getLastRound() {
        return lastRound;
    }

    public int getNumTurnDone() {
        return numTurnDone;
    }

    public boolean nextActionTurn(){
        int index = playerSequenceAscend.indexOf(turn.getCurrentPlayer());
        turn.getCurrentPlayer().changeState(PlayerState.WAIT);
        turn.setUsedCharacter(false);
        numTurnDone += 1;

        if (index == playerSequence.size() - 1)
            return false;
        else{
            Player p = playerSequenceAscend.get(index + 1);
            turn.updatePlayer(p);
            turn.resetRemainingMovements(playerSequenceAscend.size() + 1);
            p.changeState(PlayerState.ACTION);
            return true;
        }
    }

    public void endPlanningPhase(){
        int assistenteBasso = 11;
        playerSequenceAscend.clear();

        for (Player p : playerSequence){
            if (p.getLastUsed().getWeight() < assistenteBasso){
                nextSequence.set(0, p);
                assistenteBasso = p.getLastUsed().getWeight();
            }
        }

        for (int i = 1; i < playerSequence.size(); i++) {
            nextSequence.set(i, getNextPlayer(nextSequence.get(i - 1)));
        }

        playerSequenceAscend.add(nextSequence.get(0));
        playerSequenceAscend.add(nextSequence.get(1));

        if (playerSequence.size() == 3 && nextSequence.get(1).getLastUsed().getWeight() < nextSequence.get(2).getLastUsed().getWeight())
            playerSequenceAscend.add(nextSequence.get(2));
        else if (playerSequence.size() == 3) {
            playerSequenceAscend.add(1, nextSequence.get(2));
        }

        playerSequence.clear();
        playerSequence.addAll(nextSequence);
        playerSequence.get(0).changeState(PlayerState.ACTION);
        turn.updatePlayer(playerSequence.get(0));
        turn.resetRemainingMovements(playerSequence.size()+ 1);

        numTurnDone = 0;
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
        numTurnDone = 0;
        cloudChosen.clear();
        turn.updatePlayer(playerSequence.get(0));
        playerSequence.get(0).changeState(PlayerState.PLANNING);
    }

    public Player getNextPlayer(Player p){
        if (playerSequence.indexOf(p) + 1 == playerSequence.size())
            return playerSequence.get(0);
        else
            return playerSequence.get(1 + playerSequence.indexOf(p));
    }

}
