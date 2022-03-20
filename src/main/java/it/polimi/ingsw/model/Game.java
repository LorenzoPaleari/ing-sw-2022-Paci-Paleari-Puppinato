package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;


import java.util.*;

public class Game {
    private static Game instance = null;
    private int numPlayer;
    private Table table;
    private Round round;
    private List<Player> player;

    private Game(){  //We make the constructor private, so no one else can create new instances...
        player = new LinkedList<Player>();
        table = new Table();
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

    public void startGame(){
        round = new Round(player);
        player.get(0).changeState(PlayerState.PLANNING);
    }

    public void endGame(){}

    public boolean isNicknameUsed(String nick){
        boolean value = false;
        for (Player p : player){
            if (nick.equals(p.getNickname()))
                value = true;
        }
        return value;
    }


}

