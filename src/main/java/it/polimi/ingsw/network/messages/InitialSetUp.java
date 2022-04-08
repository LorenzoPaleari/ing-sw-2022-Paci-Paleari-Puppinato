package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;
import java.util.List;

public class InitialSetUp implements Serializable, ControllerViewMessage {
    private MessageType type;
    private boolean firstPlayer;
    private List<TowerColor> colorsEvailable;

    public InitialSetUp(boolean firstPlayer, List<TowerColor> colorsEvailable){
        type = MessageType.ControllerView;
        this.firstPlayer = firstPlayer;
        this.colorsEvailable = colorsEvailable;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(View view) {
        view.initialSetUp(firstPlayer);
    }

}
