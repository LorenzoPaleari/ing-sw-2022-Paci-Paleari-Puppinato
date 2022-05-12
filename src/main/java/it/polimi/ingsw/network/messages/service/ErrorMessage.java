package it.polimi.ingsw.network.messages.service;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class ErrorMessage implements Serializable, ControllerViewMessage {
    private MessageType type;
    private ClientException clientException;

    public ErrorMessage(ClientException exception){
        type = MessageType.ControllerView;
        clientException = exception;
    }

    @Override
    public void action(View view) {
        view.printError(clientException);
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {

    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {

    }

    @Override
    public Thread getThread() {
        return null;
    }

    @Override
    public MessageType getType() {
        return type;
    }
}
