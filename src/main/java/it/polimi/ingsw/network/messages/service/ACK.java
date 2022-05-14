package it.polimi.ingsw.network.messages.service;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;


public class ACK implements Serializable, ViewControllerMessage, ControllerViewMessage {
    private MessageType type;

    public ACK(boolean isServer){
        if(isServer)
            type = MessageType.ControllerView;
        else
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
    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {

    }
}
