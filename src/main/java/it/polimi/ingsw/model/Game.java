package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;


import java.lang.reflect.Method;
import java.util.*;

public class Game {
    private boolean expertMode;
    private boolean gameEnded;
    private int numPlayer = -1;
    private Table table;
    private Round round;
    private List<Player> player;

    private Method updateIsland;
    private Method checkProfessor;
    private IslandController islandController;

    public Game(){  //We make the constructor private, so no one else can create new instances...
        player = new LinkedList<>();
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
        table = new Table(numPlayer, expertMode, updateIsland, checkProfessor, islandController);
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

    public void endGame(){
        for (Player p: player)
            p.changeState(PlayerState.WAIT);

        setGameEnded();
    }

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

    public boolean isGameEnded() {
        return gameEnded;
    }

    private void setGameEnded() {
        gameEnded = true;
    }

    public void setMethodTable(Method updateIsland) {
        this.updateIsland = updateIsland;
    }
    public void setMethodBoard(Method checkProfessor) {
        this.checkProfessor = checkProfessor;
    }
    public void setIslandController(IslandController islandController){this.islandController = islandController;}

}

