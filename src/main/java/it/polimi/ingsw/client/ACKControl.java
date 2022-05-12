package it.polimi.ingsw.client;

import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.messages.service.ACK;

public class ACKControl extends Thread {
    private NetworkHandler networkHandler;
    private boolean isServer;
    public ACKControl(NetworkHandler networkHandler, boolean isServer){
        this.networkHandler=networkHandler;
        this.isServer=isServer;
    }
    public void run (){
        while (networkHandler.connectionAlive()){
            networkHandler.send(new ACK(isServer));
            try {
                ACKControl.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
