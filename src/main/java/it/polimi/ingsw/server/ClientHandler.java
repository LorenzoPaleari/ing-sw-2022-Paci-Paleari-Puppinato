package it.polimi.ingsw.server;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.messages.InitialSetUp;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientHandler {
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
    }

    public void start(){
        try{
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

        }
        catch (IOException e){

        }

    }

    public void listen(){
        while (isConnected) {
            try {
                GenericMessage message = (GenericMessage) input.readObject();
                if (message.getType() == MessageType.ViewController) {
                    ViewControllerMessage vmMessage = (ViewControllerMessage) message;
                    vmMessage.action(virtualView);
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
        send(new InitialSetUp(firstPlayer));
    }

    public void playerSetUp(){
        List<TowerColor> towerColor= new ArrayList<>();
        towerColor=virtualView.getAvailableColor();
    }

}


