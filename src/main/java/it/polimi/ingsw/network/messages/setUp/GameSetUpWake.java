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
import java.util.List;

public class GameSetUpWake implements Serializable, LobbyMessage, ControllerViewMessage {
    MessageType type;
    int numLobby;
    boolean fullGame;

    List<String[]> lobbies;

    public GameSetUpWake(boolean fullGame,List<String[]> lobbies, int numLobby){
        this.fullGame = fullGame;
        this.lobbies = lobbies;
        this.type = MessageType.ControllerView;
        this.numLobby = numLobby;
    }

    public GameSetUpWake(int numLobby){
        this.type = MessageType.Lobby;
        this.numLobby = numLobby;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {
        lobbyHandler.waiting(clientHandler, numLobby);
    }

    @Override
    public void action(View view) {
        view.wakeUp(fullGame, lobbies, numLobby);
    }
}
