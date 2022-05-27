package it.polimi.ingsw.client.viewUtilities;

import java.util.List;

public class LobbyValidator {

    public static int validLobbyCount(int numLobby, List<String[]> lobbies){
        int j = 0;
        int i;
        for (i = 0; j!=numLobby; i++)
            if (lobbies.get(i).length < 6)
                j++;

        if (i == 0)
            i++;

        return i - 1;
    }

    public static boolean checkForValidLobby(List<String[]> lobbies){
        for (String[] s : lobbies)
            if (s.length < 6)
                return true;

        return false;
    }
}
