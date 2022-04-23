package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class InitialSetUp implements Serializable, ControllerViewMessage, ViewControllerMessage {
    private MessageType type;
    private boolean firstPlayer;
    private int numPlayer;
    private boolean expert;

    public InitialSetUp(boolean firstPlayer){
        type = MessageType.ControllerView;
        this.firstPlayer = firstPlayer;
    }

    public InitialSetUp(int numPlayer, boolean expert){
        this.numPlayer=numPlayer;
        this.expert=expert;
        type = MessageType.ViewController;

    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(View view) {
        view.initialSetUp(firstPlayer);
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
        virtualView.setUpGameInfo(numPlayer, expert, playerNickname);
    }
}
