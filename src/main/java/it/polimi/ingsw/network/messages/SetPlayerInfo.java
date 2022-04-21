package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class SetPlayerInfo implements Serializable, ViewControllerMessage {
    private MessageType type;
    private TowerColor color;
    private String nickname;

    public SetPlayerInfo (TowerColor color, String nickname){
        this.color=color;
        this.nickname=nickname;
        type = MessageType.ViewController;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView) {
        virtualView.setUpPlayerInfo(color, nickname);
    }
}
