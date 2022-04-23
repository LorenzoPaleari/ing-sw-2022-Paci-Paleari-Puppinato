package it.polimi.ingsw.client;

import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.List;

public interface View {

    /**
     * Ask the ServerIp, then give in to the ServerHandler which will establish the connection
     */
    void start();

    void initialSetUp(boolean firstPlayer);

    void playerSetUp();

    void colorSetUp(List<TowerColor> tower);
}
