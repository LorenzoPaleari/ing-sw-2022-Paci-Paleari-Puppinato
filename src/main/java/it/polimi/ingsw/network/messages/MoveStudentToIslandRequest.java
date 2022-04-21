package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class MoveStudentToIslandRequest implements Serializable, ViewControllerMessage {
    private MessageType type;
    private int islandPosition;
    private PawnColor color;

    public MoveStudentToIslandRequest (int islandPosition, PawnColor color){
        this.islandPosition = islandPosition;
        this.color=color;
        type = MessageType.ViewController;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView) {
        virtualView.moveStudentToIsland(islandPosition, color);
    }
}
