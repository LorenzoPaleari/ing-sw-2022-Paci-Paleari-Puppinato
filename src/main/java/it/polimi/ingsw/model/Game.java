package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;


import java.util.*;

public class Game {
    private static Game instance = null;
    private boolean expertMode;
    private int numPlayer;
    private Table table;
    private Round round;
    private List<Player> player;

    private Game(){  //We make the constructor private, so no one else can create new instances...
        player = new LinkedList<>();
    }

    public static Game getInstance(){  //...but allow clients to get an instance via a static method
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    public void setNumPlayer(int num){
        numPlayer = num;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public void startGame() {
        table = new Table(numPlayer, expertMode);
        round = new Round(player);
        player.get(0).changeState(PlayerState.PLANNING);

        LinkedList<Student> student;
        int quantity;
        if (numPlayer == 2)
            quantity = 7;
        else
            quantity = 9;

        for (Player p :player) {
            student = table.getBag().initialSetup(quantity);
            p.setUp(student, expertMode, numPlayer);
        }
    }

    public int addPlayer(Player player) {
        this.player.add(player);
        return (numPlayer - this.player.size());
    }

    public Round getRound(){
        return round;
    }

    public List<Player> getPlayers() {
        return player;
    }

    public Table getTable() {
        return table;
    }

    public void endGame(){}

    public boolean isNicknameUsed(String nick){
        boolean value = false;
        for (Player p : player){
            if (nick.equals(p.getNickname())){
                value = true;
                break;
            }
        }
        return value;
    }


}

