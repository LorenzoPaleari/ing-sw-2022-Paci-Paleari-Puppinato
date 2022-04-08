package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;
import java.util.List;

public class PlayerSetUp implements Serializable, ControllerViewMessage {
    private MessageType type;
    List<TowerColor> towers;

    public PlayerSetUp(List<TowerColor> tower){
        this.towers = tower;
        type = MessageType.ControllerView;
    }

    @Override
    public void action(View view) {

    }

    @Override
    public MessageType getType() {
        return null;
    }
}
