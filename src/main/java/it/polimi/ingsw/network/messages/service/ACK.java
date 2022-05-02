package it.polimi.ingsw.network.messages.service;

import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;


public class ACK implements Serializable, ViewControllerMessage {
    private MessageType type;

    public ACK(){
        type = MessageType.ViewController;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
    }
}
