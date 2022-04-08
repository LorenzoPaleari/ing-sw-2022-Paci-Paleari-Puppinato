package it.polimi.ingsw.network;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.GenericMessage;

public interface ControllerViewMessage extends GenericMessage {

    void action(View view);
}
