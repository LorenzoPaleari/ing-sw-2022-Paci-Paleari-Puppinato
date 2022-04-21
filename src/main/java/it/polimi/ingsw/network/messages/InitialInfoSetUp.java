package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class InitialInfoSetUp implements Serializable, ViewControllerMessage {
    private MessageType type;
    private int numPlayer;
    private boolean expert;

    public InitialInfoSetUp (int numPlayer, boolean expert){
        this.numPlayer=numPlayer;
        this.expert=expert;
        type = MessageType.ViewController;

    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView) {
        virtualView.setUpGameInfo(numPlayer, expert);
    }
}
