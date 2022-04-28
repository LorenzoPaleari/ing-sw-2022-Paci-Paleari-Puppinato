package it.polimi.ingsw.client;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.ViewUtilities.GameInfo;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.List;

public class GUIView implements View {
    @Override
    public void start() {

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
    public void printInterrupt(String nickname, String player) {

    }

    @Override
    public void printError(ClientException exception) {

    }

}
