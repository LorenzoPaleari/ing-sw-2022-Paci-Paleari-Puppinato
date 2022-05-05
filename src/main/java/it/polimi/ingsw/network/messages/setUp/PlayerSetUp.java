package it.polimi.ingsw.network.messages.setUp;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.LobbyMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class PlayerSetUp implements Serializable, ControllerViewMessage, LobbyMessage {
    private MessageType type;
    private String nickname;
    private boolean requestAgain;

    public PlayerSetUp(boolean requestAgain){
        this.requestAgain = requestAgain;
        type = MessageType.ControllerView;
    }

    public PlayerSetUp(String nickname){
        this.nickname=nickname;
        type = MessageType.Lobby;
    }

    @Override
    public void action(View view) {
        view.playerSetUp(requestAgain);
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {;
    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {
        lobbyHandler.setPlayerNickname(nickname, clientHandler);
    }
}
