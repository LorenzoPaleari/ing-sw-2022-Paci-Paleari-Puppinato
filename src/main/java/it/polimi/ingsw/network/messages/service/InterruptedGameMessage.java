package it.polimi.ingsw.network.messages.service;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class InterruptedGameMessage  implements Serializable, ControllerViewMessage {
    private String nickname;
    private String player;
    private MessageType type;

    public InterruptedGameMessage(String nickname){
        this.nickname=nickname;
        type = MessageType.ControllerView;
    }

    @Override
    public void action(View view) {
        view.printInterrupt(nickname);
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {
    }

    @Override
    public MessageType getType() {
        return type;
    }
}
