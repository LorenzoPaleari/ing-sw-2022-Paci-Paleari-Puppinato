package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class MoveMotherNatureRequest implements Serializable, ViewControllerMessage {
    private MessageType type;
    private int endPosition;

    public MoveMotherNatureRequest (int endPosition){
        this.endPosition= endPosition;
        type = MessageType.ViewController;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView) {
        virtualView.moveMotherNature(endPosition);
    }
}
