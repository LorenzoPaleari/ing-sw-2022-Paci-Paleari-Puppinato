package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.service.ACK;

public class ACKControl extends Thread {
    private static boolean sendACK;
    private ServerHandler serverHandler;
    public ACKControl(ServerHandler serverHandler){
        sendACK=true;
        this.serverHandler=serverHandler;
    }
    public void run (){
        while (sendACK){
            serverHandler.send(new ACK());
            try {
                ACKControl.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void setSendACK(){
        sendACK=false;
    }
}
