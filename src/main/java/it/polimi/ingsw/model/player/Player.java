package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.board.Board;

public class Player {
    private String nickname;
    private TowerColor towerColor;
    private Board board;
    private Deck deck;
    private PlayerState state;
    private Assistant lastUsed;

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
