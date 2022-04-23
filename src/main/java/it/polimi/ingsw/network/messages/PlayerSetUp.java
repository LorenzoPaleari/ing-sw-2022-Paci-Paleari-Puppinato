package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;
import java.util.List;

public class PlayerSetUp implements Serializable, ControllerViewMessage, ViewControllerMessage {
    private MessageType type;
    private List<TowerColor> tower;
    private TowerColor color;
    private String nickname;

    public PlayerSetUp(List<TowerColor> tower){
        this.tower = tower;
        type = MessageType.ControllerView;
    }

    public PlayerSetUp(TowerColor color, String nickname){
        this.color=color;
        this.nickname=nickname;
        type = MessageType.ViewController;
    }

    @Override
    public void action(View view) {
        view.playerSetUp(tower);
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
        virtualView.setUpPlayerInfo(color, nickname, playerNickname);
    }
}
