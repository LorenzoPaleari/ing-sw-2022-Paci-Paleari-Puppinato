package it.polimi.ingsw.client;

public interface View {

    /**
     * Ask the ServerIp, then give in to the ServerHandler which will establish the connection
     */
    void start();

    void initialSetUp(boolean firstPlayer);
}
