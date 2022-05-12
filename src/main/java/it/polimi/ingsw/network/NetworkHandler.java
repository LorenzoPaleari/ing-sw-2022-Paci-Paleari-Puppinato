package it.polimi.ingsw.network;

public interface NetworkHandler {
    void send(GenericMessage message);
    boolean connectionAlive();
}
