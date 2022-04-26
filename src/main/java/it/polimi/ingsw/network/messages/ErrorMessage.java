package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;

public class ErrorMessage implements Serializable, ControllerViewMessage {
    private MessageType type;
    private String errorText;

    public ErrorMessage(String text){
        type = MessageType.ControllerView;
        errorText = text;
    }

    @Override
    public void action(View view) {
        view.printError(errorText);
    }

    @Override
    public MessageType getType() {
        return type;
    }
}
