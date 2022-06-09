package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.islandStrategy.IslandStrategy;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Table;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Main class of the model.
 * Contains the references to the Players, Table, Round and Turn
 */
public class Game {
    private boolean expertMode;
    private boolean gameEnded;
    private int numPlayer = -1;
    private Table table;
    private Round round;
    private final List<Player> player;
    private Method updateIsland;
    private Method checkProfessor;
    private IslandStrategy islandStrategy;

    /**
     * Constructor.
     * initialize  only the List of players.
     *
     * Full initialization of the class take place, when all
     * the players joined the game, in function startGame()
     */
    public Game(){  //We make the constructor private, so no one else can create new instances...
        player = new LinkedList<>();
    }

    /**
     * Sets the number of players for the current Game.
     * @param num
     */
    public void setNumPlayer(int num){
        numPlayer = num;
    }

    /**
     * Gets the number of players for the current Game.
     * @return
     */
    public int getNumPlayer() {
        return numPlayer;
    }

    /**
     * Gets true if the game is in expert mode, otherwise false
     * @return
     */
    public boolean isExpertMode() {
        return expertMode;
    }

    /**
     * Sets the game mode
     * @param expertMode
     */
    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    /**
     * Initialize the game, setting the quantity of towers for each player
     */
    public void startGame() {
        table = new Table(numPlayer, expertMode, updateIsland, checkProfessor, islandStrategy);
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

    /**
     * Adds a new player
     * @param player
     * @return
     */
    public int addPlayer(Player player) {
        this.player.add(player);
        return (numPlayer - this.player.size());
    }

    /**
     * Gets the current round
     * @return
     */
    public Round getRound(){
        return round;
    }

    /**
     * Gets the list of players
     * @return
     */
    public List<Player> getPlayers() {
        return player;
    }

    /**
     * Gets the table
     * @return
     */
    public Table getTable() {
        return table;
    }

    /**
     * Sets the end of the game
     */
    public void endGame(){
        for (Player p: player)
            p.changeState(PlayerState.WAIT);

        setGameEnded();
    }

    /**
     * Checks if the nickname is already used
     * @param nick
     * @return
     */
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

    /**
     * Gets true if the game is ended
     * @return
     */
    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * Sets gameEnded
     */
    private void setGameEnded() {
        gameEnded = true;
    }

    /**
     *
     * @param updateIsland
     */
    public void setMethodTable(Method updateIsland) {
        this.updateIsland = updateIsland;
    }

    /**
     *
     * @param checkProfessor
     */
    public void setMethodBoard(Method checkProfessor) {
        this.checkProfessor = checkProfessor;
    }

    /**
     *
     * @param islandStrategy
     */
    public void setIslandController(IslandStrategy islandStrategy){this.islandStrategy = islandStrategy;}

}

