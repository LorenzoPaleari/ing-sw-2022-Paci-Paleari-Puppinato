package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class LobbyHandler {
    private List<ClientHandler> allClientHandlers;
    private List<ClientHandler[]> lobbies;
    private List<Controller> controllers;
    private List<VirtualView> virtualViews;

    public LobbyHandler(){
        allClientHandlers = new ArrayList<>();
        lobbies = new ArrayList<>();
        controllers = new ArrayList<>();
        virtualViews = new ArrayList<>();
    }

    public synchronized void addClient(int lobbyNumber, ClientHandler clientHandler) {
        int lobby = count(lobbyNumber);
        if (controllers.get(lobby).getGame().isGameEnded()){
            clientHandler.printInterrupt("");
        } else if (controllers.get(lobby).getGame().getNumPlayer() == lobbies.get(lobby).length) {
            clientHandler.gameSetUp(true);
        } else {
            ClientHandler[] clients = lobbies.get(lobby);
            ClientHandler[] clientHandlers = new ClientHandler[clients.length + 1];

            System.arraycopy(clients, 0, clientHandlers, 0, clients.length);
            clientHandlers[clients.length] = clientHandler;

            lobbies.set(lobby, clientHandlers);

            clientHandler.setVirtualView(virtualViews.get(lobby));
            virtualViews.get(lobby).addClientHandler(clientHandler);

            virtualViews.get(lobby).lockColorSetUp(clientHandler.getPlayerNickname());
        }
    }

    private int count(int lobbyNumber) {
        int j = 0;
        int i;
        for (i = 0; j!=lobbyNumber; i++)
            if (!(controllers.get(i).getGame().isGameEnded() || lobbies.get(i).length == controllers.get(i).getGame().getNumPlayer()))
                j++;

        return i - 1;
    }

    public synchronized void newClient(ClientHandler clientHandler){
        ClientHandler[] clientHandlers = {clientHandler};
        lobbies.add(clientHandlers);
        controllers.add(new Controller());
        virtualViews.add(new VirtualView(controllers.get(controllers.size() - 1)));
        clientHandler.setVirtualView(virtualViews.get(virtualViews.size() - 1));
        controllers.get(controllers.size()-1).setVirtualView(virtualViews.get(virtualViews.size() - 1));
        virtualViews.get(virtualViews.size()-1).addClientHandler(clientHandler);

        (new Thread(() -> {
            try {
                controllers.get(controllers.size() - 1).gameStart();
            } catch (Exception ignored) {
            }
        })).start();

        clientHandler.initialSetUp();
    }

    public synchronized List<String[]> getLobbies(){
        List<String[]> lobbiesModified = new ArrayList<>();
        for (ClientHandler[] c : lobbies){
            if (controllers.get(lobbies.indexOf(c)).getGame().getNumPlayer() == -1 && !controllers.get(lobbies.indexOf(c)).getGame().isGameEnded()) {
                String[] temp = new String[1];
                temp[0] = "Starting... ";
                lobbiesModified.add(temp);
            } else if (!(controllers.get(lobbies.indexOf(c)).getGame().isGameEnded() || c.length == controllers.get(lobbies.indexOf(c)).getGame().getNumPlayer())){
                String[] temp = new String[c.length + 2];

                for (int i = 0; i < c.length; i++)
                    temp[i] = c[i].getPlayerNickname();

                temp[c.length] = "" + controllers.get(lobbies.indexOf(c)).getGame().getNumPlayer();
                temp[c.length + 1] = "" + controllers.get(lobbies.indexOf(c)).getGame().isExpertMode();

                lobbiesModified.add(temp);
            }
        }
        return lobbiesModified;
    }

    public synchronized boolean isNicknameUsed(String nickName){
        for (ClientHandler c : allClientHandlers)
                if (c.getPlayerNickname().equals(nickName))
                    return true;

        return false;
    }

    public synchronized void refreshLobbies(ClientHandler clientHandler) {
        if (getLobbies().size() == 0) {
            clientHandler.refreshLobbies(getLobbies(), true);
            return;
        }

        clientHandler.refreshLobbies(getLobbies(), false);
    }

    public synchronized void setPlayerNickname(String nickname, ClientHandler clientHandler) {
        allClientHandlers.add(clientHandler);
        if (!isNicknameUsed(nickname)) {
            clientHandler.setPlayerNickname(nickname);

            clientHandler.gameSetUp();
        } else {
            clientHandler.playerSetUp(true);
        }
    }

    public synchronized void terminateLobby(VirtualView virtualView) {
        int index = virtualViews.indexOf(virtualView);
        controllers.get(index).getGame().endGame();
        allClientHandlers.removeAll((List.of(lobbies.get(index))));
    }
}
