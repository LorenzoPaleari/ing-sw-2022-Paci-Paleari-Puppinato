package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class VirtualView {
    private ReentrantLock lock = new ReentrantLock();;
    private Controller controller;
    private List<ClientHandler> clientHandlers;

    public VirtualView(Controller controller){
        this.controller=controller;
        clientHandlers= new LinkedList<>();
    }

    public void addClientHandler(ClientHandler clientHandler){
        clientHandlers.add(clientHandler);
    }

    public List<TowerColor> getAvailableColor(){
        return controller.getAvailableColor();
    }

    public void cloudChosen(int cloudPosition, String playerNickname){
        controller.chooseCloud(controller.getPlayerByNickname(playerNickname),cloudPosition);
    }

    public void moveMotherNature(int endPosition, String playerNickname){
        controller.moveMotherNature(controller.getPlayerByNickname(playerNickname),endPosition);
    }

    public void moveStudentToIsland(int islandPosition, PawnColor color, String playerNickname){
        controller.useStudentIsland(controller.getPlayerByNickname(playerNickname), color, islandPosition);
    }
    public void moveStudentToDining(PawnColor color, String playerNickname){
        controller.useStudentDining(controller.getPlayerByNickname(playerNickname), color);
    }

    public void useAssistant(int position, String playerNickname){
        controller.useAssistant(position, controller.getPlayerByNickname(playerNickname));
    }

    public void setUpGameInfo(int numPlayer, boolean expert, String playerNickname){
        Server.setMaxPlayer(numPlayer);
        controller.setExpertMode(expert);
        controller.setNumPlayer(numPlayer);
        synchronized (this) {
            this.notifyAll();
        }
        clientHandlers.get(0).playerSetUp(false);
    }

    public void setUpPlayerInfo(String nickname, String playerNickname){
        if (!controller.isNicknameUsed(nickname)) {
            getClientHandlerByNickname(playerNickname).setPlayerNickname(nickname);
            controller.addPlayer(new Player(nickname));
            lock.lock();
            getClientHandlerByNickname(nickname).colorSetUp();
        } else {
            getClientHandlerByNickname(playerNickname).playerSetUp(true);
        }
    }

    public void setUpPlayerColor(TowerColor color, String playerNickname){
        controller.getPlayerByNickname(playerNickname).setTowerColor(color);
        lock.unlock();
    }

    public ClientHandler getClientHandlerByNickname(String nickname){
        for (ClientHandler cl: clientHandlers){
            if(nickname.equals(cl.getPlayerNickname()))
                return cl;
        }
        return null;
    }

}
