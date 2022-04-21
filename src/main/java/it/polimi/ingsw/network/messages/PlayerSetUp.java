package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;
import java.util.List;

public class PlayerSetUp implements Serializable, ControllerViewMessage {
    private MessageType type;
    private List<TowerColor> tower;

    public PlayerSetUp(List<TowerColor> tower){
        this.tower = tower;
        type = MessageType.ControllerView;
    }

    @Override
    public void action(View view) {
        view.playerSetUp(tower);
    }

    @Override
    public MessageType getType() {
        return type;
    }
}
