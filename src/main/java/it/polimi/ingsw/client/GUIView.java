package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ViewUtilities.GameInfo;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.List;

public class GUIView implements View {
    @Override
    public void start() {

    }

    @Override
    public void gameSetUp(List<String[]> lobbies) {

    }

    @Override
    public void refreshLobbies(List<String[]> lobbies, boolean firstLobby) {

    }

    @Override
    public void fullLobby(List<String[]> lobbies) {

    }

    @Override
    public void initialSetUp(boolean firstPlayer) {

    }

    @Override
    public void playerSetUp(boolean requestAgain) {

    }

    @Override
    public void colorSetUp(List<TowerColor> tower) {

    }

    @Override
    public void printGameBoard(GameInfo gameInfo) {

    }

    @Override
    public void choseAction() {

    }

    @Override
    public void printWinner(String winner1, String winner2, String nickname) {

    }

    @Override
    public void printInterrupt(String nickname) {

    }

    @Override
    public void printError(ClientException exception) {

    }

}
