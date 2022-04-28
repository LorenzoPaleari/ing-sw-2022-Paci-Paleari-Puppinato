package it.polimi.ingsw.network;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;

public interface LobbyMessage extends GenericMessage{

    void action(LobbyHandler lobbyHandler, ClientHandler clientHandler);
}
