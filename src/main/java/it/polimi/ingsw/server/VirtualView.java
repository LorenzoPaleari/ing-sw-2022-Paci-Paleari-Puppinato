package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ViewUtilities.GameInfo;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class VirtualView {
    private ReentrantLock lock = new ReentrantLock();
    private LobbyHandler lobbyHandler;
    private Controller controller;
    private List<ClientHandler> clientHandlers;

    public VirtualView(Controller controller, LobbyHandler lobbyHandler){
        this.controller=controller;
        this.lobbyHandler = lobbyHandler;
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
        /*synchronized (controller) {
            controller.notifyAll();
        }*/
        ClientHandler[] clientWaiting = lobbyHandler.getWaiting(controller);
        if (clientWaiting != null) {
            for (int i = 0; i < numPlayer - 1; i++)
                clientWaiting[i].gameSetUpWake(false, null, lobbyHandler.getLobbyIndex(controller));

            try {
                synchronized (this) {
                    this.wait(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = numPlayer - 1; i < clientWaiting.length; i++)
                clientWaiting[i].gameSetUpWake(true, lobbyHandler.getLobbies(), lobbyHandler.getLobbyIndex(controller));
        }

        clientHandlers.get(0).playerSetUp(false);
    }

    public void setUpPlayerInfo(String nickname, String playerNickname){
        if (!lobbyHandler.isNicknameUsed(nickname)) {
            getClientHandlerByNickname(playerNickname).setPlayerNickname(nickname);

            lock.lock();
            getClientHandlerByNickname(nickname).colorSetUp();
        } else {
            getClientHandlerByNickname(playerNickname).playerSetUp(true);
        }
    }

    public void setUpPlayerColor(TowerColor color, String playerNickname){
        controller.addPlayer(new Player(playerNickname, color));
        lock.unlock();
    }

    public ClientHandler getClientHandlerByNickname(String nickname){
        for (ClientHandler cl: clientHandlers){
            if(nickname.equals(cl.getPlayerNickname()))
                return cl;
        }
        return null;
    }

    public void printGameBoard(Game game){
        for (ClientHandler c : clientHandlers)
            c.printGameBoard(new GameInfo(game, c.getPlayerNickname()));
    }

    public void printError(ClientException exception, String player){
        if (getClientHandlerByNickname(player) != null)
            getClientHandlerByNickname(player).printError(exception);
    }

    public void printInterrupt(String nickname){
        for (ClientHandler c : clientHandlers) {
            if (nickname.equals(c.getPlayerNickname())) ;
            else
                c.printInterrupt(nickname, c.getPlayerNickname());
        }
    }

    public void printWinner(String winner1, String winner2){
        for (ClientHandler c : clientHandlers)
            c.printWinner(winner1, winner2, c.getPlayerNickname());
    }

}
