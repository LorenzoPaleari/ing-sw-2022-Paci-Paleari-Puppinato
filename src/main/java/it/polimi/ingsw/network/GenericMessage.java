package it.polimi.ingsw.network;


import it.polimi.ingsw.client.View;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

public interface GenericMessage {

    MessageType getType();

    void action(View view);

    void action(VirtualView virtualView, String playerNickname);

    void action(LobbyHandler lobbyHandler, ClientHandler clientHandler);

    Thread getThread();
}
