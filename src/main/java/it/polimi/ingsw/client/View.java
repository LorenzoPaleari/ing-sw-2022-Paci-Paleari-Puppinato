package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ViewUtilities.GameInfo;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.List;

public interface View {

    /**
     * Ask the ServerIp, then give in to the ServerHandler which will establish the connection
     */
    void start();

    void initialSetUp(boolean firstPlayer);

    void playerSetUp(boolean requestAgain);

    void colorSetUp(List<TowerColor> tower);

    void printGameBoard(GameInfo gameInfo);

    void choseAction();

    void printError(ClientException clientException);

    void printWinner(String winner1, String winner2, String nickname);
}

