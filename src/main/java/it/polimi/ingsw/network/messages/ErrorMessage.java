package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.network.ControllerViewMessage;
import it.polimi.ingsw.network.MessageType;

import java.io.Serializable;

public class ErrorMessage implements Serializable, ControllerViewMessage {
    private MessageType type;
    private ClientException clientException;

    public ErrorMessage(ClientException exception){
        type = MessageType.ControllerView;
        clientException = exception;
    }

    @Override
    public void action(View view) {
        view.printError(clientException);
    }

    @Override
    public MessageType getType() {
        return type;
    }
}
