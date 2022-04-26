package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;

public class WinnerMessage implements Serializable, ControllerViewMessage {
    private String winner1;
    private String winner2;
    private String nickname;
    private MessageType type;

    public WinnerMessage(String winner1, String winner2, String nickname){
        this.winner1=winner1;
        this.winner2=winner2;
        this.nickname=nickname;
        type = MessageType.ControllerView;
    }

    @Override
    public void action(View view) {
        view.printWinner(winner1, winner2, nickname);
    }

    @Override
    public MessageType getType() {
        return type;
    }
}
