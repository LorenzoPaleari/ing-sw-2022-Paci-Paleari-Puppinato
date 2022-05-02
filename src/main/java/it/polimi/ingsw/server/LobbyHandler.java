package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LobbyHandler {
    private final Object lock = new Object();
    private List<ClientHandler[]> lobbies;
    private Map clientWaiting;
    private List<Controller> controllers;
    private List<VirtualView> virtualViews;

    public LobbyHandler(){
        clientWaiting = new HashMap<>();
        lobbies = new ArrayList<>();
        controllers = new ArrayList<>();
        virtualViews = new ArrayList<>();
    }

    public synchronized void addClient(int lobbyNumber, ClientHandler clientHandler) {
        ClientHandler[] clients = lobbies.get(lobbyNumber);
        ClientHandler[] clientHandlers = new ClientHandler[clients.length + 1];

        System.arraycopy(clients, 0, clientHandlers, 0, clients.length);
        clientHandlers[clients.length] = clientHandler;

        lobbies.set(lobbyNumber, clientHandlers);

        clientHandler.setFirstPlayer(false);
        clientHandler.setVirtualView(virtualViews.get(lobbyNumber));
        virtualViews.get(lobbyNumber).addClientHandler(clientHandler);

        clientHandler.playerSetUp(false);
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
                String[] temp = new String[2];
                temp[0] = "Starting... ";
                temp[1] = c[0].getPlayerNickname()+ " is choosing Game settings.";
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

    public synchronized void waiting(ClientHandler clientHandler, int numLobby){
        synchronized (lock) {
            ClientHandler[] temp = (ClientHandler[]) clientWaiting.get(numLobby);
            if (temp == null) {
                ClientHandler[] clientHandlers = {clientHandler};
                clientWaiting.put(numLobby, clientHandlers);
            } else {
                int length = temp.length;
                ClientHandler[] clientHandlers = new ClientHandler[length + 1];
                System.arraycopy(temp, 0, clientHandlers, 0, length);

                clientHandlers[length] = clientHandler;
                clientWaiting.replace(numLobby, clientHandlers);
            }
        }
    }

    public synchronized boolean isNicknameUsed(String nickName){
        for (ClientHandler[] c : lobbies)
            for (ClientHandler clientHandler : c)
                if (clientHandler.getPlayerNickname().equals(nickName))
                    return true;

        return false;
    }

    public synchronized ClientHandler[] getWaiting(Controller controller){
        synchronized (lock) {
            return (ClientHandler[]) clientWaiting.get(controllers.indexOf(controller));
        }
    }

    public synchronized int getLobbyIndex(Controller controller){
        return controllers.indexOf(controller);
    }
}
