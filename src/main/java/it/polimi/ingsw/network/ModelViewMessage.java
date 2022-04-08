package it.polimi.ingsw.network;

import it.polimi.ingsw.client.View;

public interface ModelViewMessage extends GenericMessage{

    void action (View view);
}
