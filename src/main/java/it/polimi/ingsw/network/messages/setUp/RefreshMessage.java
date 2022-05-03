package it.polimi.ingsw.network.messages.setUp;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.LobbyMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;
import java.util.List;

public class RefreshMessage implements Serializable, LobbyMessage, ControllerViewMessage {
    MessageType type;
    List<String[]> lobbies;

    boolean firstLobby = false;

    public RefreshMessage(){
        type = MessageType.Lobby;
    }

    public RefreshMessage(List<String[]> lobbies, boolean firstLobby){
        type = MessageType.ControllerView;
        this.firstLobby = firstLobby;
        this.lobbies = lobbies;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(View view) {
        view.refreshLobbies(lobbies, firstLobby);
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {

    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {
        lobbyHandler.refreshLobbies(clientHandler);
    }
}
