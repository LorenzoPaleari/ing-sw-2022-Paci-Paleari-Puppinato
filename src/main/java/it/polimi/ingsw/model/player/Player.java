package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.pawns.Student;

import java.util.*;

public class Player {
    private String nickname;
    private TowerColor towerColor;
    private Board board;
    private Deck deck;
    private PlayerState state;
    private Assistant lastUsed;
    private int numCoin;

    public Player(String nickname, TowerColor towerColor)
    {
        this.nickname=nickname;
        this.towerColor=towerColor;
    }

    public void setUp(LinkedList<Student> student, boolean expert, int numPlayer)
    {
        board=new Board(student, expert,numPlayer );
        deck=new Deck();
        state=PlayerState.WAIT;
        lastUsed=null;
        if(expert) numCoin=1;
    }
    public Board getBoard() {
        return board;
    }

    public String getNickname() {
        return nickname;
    }

    public Assistant getLastUsed() {
        return lastUsed;
    }

    public void changeState(PlayerState state)
    {
        this.state=state;
    }

    public void addAssistant (int position)
    {
       lastUsed=deck.removeAssistant(position);
    }
}
