package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
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
    public void action(View view) {

    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
        virtualView.cloudChosen(cloudPosition, playerNickname);
    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {

    }
}
