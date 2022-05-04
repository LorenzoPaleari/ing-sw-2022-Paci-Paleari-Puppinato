package it.polimi.ingsw.network;

import it.polimi.ingsw.client.View;

public interface ControllerViewMessage extends GenericMessage {

    void action(View view);
}
