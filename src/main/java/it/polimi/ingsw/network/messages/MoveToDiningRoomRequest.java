package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class MoveToDiningRoomRequest implements Serializable, ViewControllerMessage {
    private MessageType type;
    private PawnColor color;

    public MoveToDiningRoomRequest (PawnColor color){
        this.color=color;
        type = MessageType.ViewController;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView) {
       virtualView.moveStudentToDining(color);
    }
}
