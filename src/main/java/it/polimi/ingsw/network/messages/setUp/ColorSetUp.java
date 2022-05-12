package it.polimi.ingsw.network.messages.setUp;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;
import java.util.List;

public class ColorSetUp implements Serializable, ControllerViewMessage, ViewControllerMessage {
    private MessageType type;
    List<TowerColor> towerColor;
    TowerColor chosenColor;

    public ColorSetUp(List<TowerColor> towerColor){
        type = MessageType.ControllerView;
        this.towerColor = towerColor;
    }

    public ColorSetUp(TowerColor color){
        this.chosenColor=color;
        type = MessageType.ViewController;

    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(View view) {
        view.colorSetUp(towerColor);
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
        virtualView.setUpPlayerColor(chosenColor, playerNickname);
    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {

    }

    @Override
    public Thread getThread() {
        return null;
    }
}