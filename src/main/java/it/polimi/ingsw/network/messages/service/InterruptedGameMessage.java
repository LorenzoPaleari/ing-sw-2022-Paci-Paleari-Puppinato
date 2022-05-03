package it.polimi.ingsw.network.messages.service;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class InterruptedGameMessage  implements Serializable, ControllerViewMessage {
    private String nickname;
    private String player;
    private MessageType type;

    public InterruptedGameMessage(String nickname, String player){
        this.nickname=nickname;
        this.player=player;
        type = MessageType.ControllerView;
    }

    @Override
    public void action(View view) {
        view.printInterrupt(nickname, player);
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {

    }

    @Override
    public MessageType getType() {
        return type;
    }
}
