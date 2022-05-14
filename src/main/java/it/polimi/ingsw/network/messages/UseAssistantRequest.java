package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
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
    public void action(View view) {

    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
        virtualView.useAssistant(position, playerNickname);
    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {

    }
}
