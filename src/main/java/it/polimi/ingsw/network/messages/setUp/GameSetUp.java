package it.polimi.ingsw.network.messages.setUp;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.LobbyMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;

import java.io.Serializable;
import java.util.List;

public class GameSetUp implements Serializable, LobbyMessage, ControllerViewMessage {
    MessageType type;
    List<String[]> lobbies;

    boolean newGame;
    int lobby;

    public GameSetUp(List<String[]> lobbies){
        this.lobbies = lobbies;
        type = MessageType.ControllerView;
    }

    public GameSetUp(boolean newGame, int lobby){
        type = MessageType.Lobby;
        this.newGame = newGame;
        this.lobby = lobby;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(View view) {
        view.gameSetUp(lobbies);
    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {
        if (newGame)
            lobbyHandler.newClient(clientHandler);
        else
            lobbyHandler.addClient(lobby, clientHandler);
    }
}
