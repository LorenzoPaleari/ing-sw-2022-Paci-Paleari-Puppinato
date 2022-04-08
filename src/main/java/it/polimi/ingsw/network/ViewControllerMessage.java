package it.polimi.ingsw.network;

import it.polimi.ingsw.server.VirtualView;

public interface ViewControllerMessage extends GenericMessage {

    void action(VirtualView virtualView);
}
