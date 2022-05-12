package it.polimi.ingsw.network.messages.setUp;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class InitialSetUp implements Serializable, ControllerViewMessage, ViewControllerMessage {
    private MessageType type;
    private int numPlayer;
    private boolean expert;

    public InitialSetUp(){
        type = MessageType.ControllerView;
    }

    public InitialSetUp(int numPlayer, boolean expert){
        this.numPlayer=numPlayer;
        this.expert=expert;
        type = MessageType.ViewController;

    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(View view) {
        view.initialSetUp();
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
        virtualView.setUpGameInfo(numPlayer, expert, playerNickname);
    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {

    }
}