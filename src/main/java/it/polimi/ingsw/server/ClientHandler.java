package it.polimi.ingsw.server;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.messages.InitialSetUp;
import it.polimi.ingsw.network.messages.PlayerSetUp;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;


public class ClientHandler extends Thread{
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private VirtualView virtualView;
    private boolean isConnected = false;
    private boolean firstPlayer;

    public ClientHandler(Socket socket, boolean firstPlayer, VirtualView virtualView){
        this.socket=socket;
        this.firstPlayer=firstPlayer;
        isConnected = true;
        this.virtualView=virtualView;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
        }
    }

    public void run(){
        initialSetUp();

        while (isConnected) {
            try {
                GenericMessage message = (GenericMessage) input.readObject();
                if (message.getType() == MessageType.ViewController) {
                    ViewControllerMessage vcMessage = (ViewControllerMessage) message;
                    vcMessage.action(virtualView);
                }

            } catch (IOException | ClassNotFoundException e) {
                isConnected = false;
            }
        }
    }

    public void send(GenericMessage message) {
        try{
            output.writeUnshared(message);
            output.flush();
            output.reset();
        }
        catch(IOException e){
            System.out.println("Errore");
        }
    }

    public void initialSetUp(){
        if (firstPlayer)
            send(new InitialSetUp(firstPlayer));

        playerSetUp();
    }

    public void playerSetUp(){
        List<TowerColor> towerColor;
        towerColor = virtualView.getAvailableColor();

        send(new PlayerSetUp(towerColor));
    }

    public void islandPawnsRequest(int islandPosition){
        int[] students;
        TowerColor towerColor;
        int towers;
        boolean motherNature;
        students=virtualView.getIslandStudents(islandPosition);
        towers=virtualView.getNumberIslandTowers(islandPosition);
        towerColor=virtualView.getColorIslandTowers(islandPosition);
        motherNature= virtualView.isMotherNature(islandPosition);
        //send(new IslandPawns(students,towers,towerColor, motherNature));

    }
    public void lastCharacterUsedOtherPlayerRequest(Player player){
       int card;
       card=virtualView.getLastCharacterUsedOtherPlayer(player);
       //send(new LastUsedCharacter(card));
    }

}


