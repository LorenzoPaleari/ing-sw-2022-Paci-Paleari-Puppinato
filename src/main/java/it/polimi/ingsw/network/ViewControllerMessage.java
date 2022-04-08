package it.polimi.ingsw.network;

import it.polimi.ingsw.network.GenericMessage;

public interface ViewControllerMessage extends GenericMessage {

    void action(VirtualView virtualView);
}
