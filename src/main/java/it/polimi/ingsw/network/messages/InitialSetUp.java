package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;

public class InitialSetUp implements Serializable, ControllerViewMessage {
    private MessageType type;
    private boolean firstPlayer;

    public InitialSetUp(boolean firstPlayer){
        type = MessageType.ControllerView;
        this.firstPlayer = firstPlayer;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(View view) {
        view.initialSetUp(firstPlayer);
    }

}
