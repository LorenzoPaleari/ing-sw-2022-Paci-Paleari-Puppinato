package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.ACKMessage;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;


public class ACK implements Serializable, ACKMessage {
    private MessageType type;

    public ACK(){
        type = MessageType.ACK;
    }

    @Override
    public MessageType getType() {
        return type;
    }
}
