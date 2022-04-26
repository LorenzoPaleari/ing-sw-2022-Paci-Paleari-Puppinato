package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.ViewUtilities.GameInfo;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;

public class GameInfoMessage implements Serializable, ControllerViewMessage {
    private MessageType type;
    private GameInfo gameInfo;

    public GameInfoMessage(GameInfo gameInfo){
        this.gameInfo = gameInfo;
        type = MessageType.ControllerView;
    }

    @Override
    public void action(View view) {
        view.printGameBoard(gameInfo);
    }

    @Override
    public MessageType getType() {
        return type;
    }

}