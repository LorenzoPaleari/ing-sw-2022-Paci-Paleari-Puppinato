package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class CloudChosenRequest implements Serializable, ViewControllerMessage {
    private MessageType type;
    private int cloudPosition;
    public CloudChosenRequest(int cloudPosition){
        type = MessageType.ViewController;
        this.cloudPosition=cloudPosition;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView) {
        virtualView.cloudChosen(cloudPosition);
    }
}