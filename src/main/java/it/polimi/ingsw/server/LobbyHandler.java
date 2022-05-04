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
        if (controllers.get(lobbyNumber).getGame().getNumPlayer() == lobbies.get(lobbyNumber).length) {
            clientHandler.gameSetUp(true);
        } else {
            ClientHandler[] clients = lobbies.get(lobbyNumber);
            ClientHandler[] clientHandlers = new ClientHandler[clients.length + 1];

            System.arraycopy(clients, 0, clientHandlers, 0, clients.length);
            clientHandlers[clients.length] = clientHandler;

            lobbies.set(lobbyNumber, clientHandlers);

            clientHandler.setFirstPlayer(false);
            clientHandler.setVirtualView(virtualViews.get(lobbyNumber));
            virtualViews.get(lobbyNumber).addClientHandler(clientHandler);

            virtualViews.get(lobbyNumber).lockColorSetUp(clientHandler.getPlayerNickname());
        }
    }

    public synchronized void newClient(ClientHandler clientHandler){
        ClientHandler[] clientHandlers = {clientHandler};
        lobbies.add(clientHandlers);
        controllers.add(new Controller());
        virtualViews.add(new VirtualView(controllers.get(controllers.size() - 1), this));
        clientHandler.setVirtualView(virtualViews.get(virtualViews.size() - 1));
        clientHandler.setFirstPlayer(true);
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
        int j = 0;
        for (ClientHandler[] c : lobbies){
            if (c.length == controllers.get(j).getGame().getNumPlayer()) {
                String[] temp = new String[1];
                temp[0] = "FULL";
                lobbiesModified.add(temp);
            } else if (controllers.get(j).getGame().getNumPlayer() == -1) {
                String[] temp = new String[1];
                temp[0] = "Starting... ";
                lobbiesModified.add(temp);
            } else {
                String[] temp = new String[c.length];

                for (int i = 0; i < c.length; i++)
                    temp[i] = c[i].getPlayerNickname();

                lobbiesModified.add(temp);
            }
            j++;
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
        boolean firstLobby;
        if (getLobbies().size() == 0)
            firstLobby = true;
        else
            firstLobby = false;
        clientHandler.refreshLobbies(getLobbies(), firstLobby);
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
}
