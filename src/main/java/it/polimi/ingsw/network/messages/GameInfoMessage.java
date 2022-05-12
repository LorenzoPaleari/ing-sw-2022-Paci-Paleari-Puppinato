package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.viewUtilities.GameInfo;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ModelViewMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class GameInfoMessage implements Serializable, ModelViewMessage, Runnable {
    private MessageType type;
    private GameInfo gameInfo;
    private View view;
    public Thread thread = null;

    public GameInfoMessage(GameInfo gameInfo){
        this.gameInfo = gameInfo;
        type = MessageType.ModelView;
    }

    @Override
    public void action(View view) {
        this.view = view;
        thread = new Thread(this);
        thread.start();
    }

    public void run(){
        view.printGameBoard(gameInfo);
    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {

    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {

    }

    @Override
    public MessageType getType() {
        return type;
    }

}