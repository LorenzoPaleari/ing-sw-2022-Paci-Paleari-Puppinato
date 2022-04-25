package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class PlayerSetUp implements Serializable, ControllerViewMessage, ViewControllerMessage {
    private MessageType type;
    private String nickname;
    private boolean requestAgain;

    public PlayerSetUp(boolean requestAgain){
        this.requestAgain = requestAgain;
        type = MessageType.ControllerView;
    }

    public PlayerSetUp(String nickname){
        this.nickname=nickname;
        type = MessageType.ViewController;
    }

    @Override
    public void action(View view) {
        view.playerSetUp(requestAgain);
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
        virtualView.setUpPlayerInfo(nickname, playerNickname);
    }
}
