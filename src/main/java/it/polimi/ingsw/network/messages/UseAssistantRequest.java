package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class UseAssistantRequest implements Serializable, ViewControllerMessage {
    private MessageType type;
    private int position;

    public UseAssistantRequest (int position){
        this.position= position;
        type = MessageType.ViewController;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
        virtualView.useAssistant(position, playerNickname);
    }
}
