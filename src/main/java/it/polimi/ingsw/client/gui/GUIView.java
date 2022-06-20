package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.gui.scene.GameSetupController;
import it.polimi.ingsw.client.gui.scene.NicknameController;
import it.polimi.ingsw.client.gui.scene.SceneController;
import it.polimi.ingsw.client.gui.scene.StartController;
import it.polimi.ingsw.client.viewUtilities.AnsiGraphics;
import it.polimi.ingsw.client.viewUtilities.GameInfo;
import it.polimi.ingsw.client.viewUtilities.IPValidator;
import it.polimi.ingsw.client.viewUtilities.LobbyValidator;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.enumerations.TowerColor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.desktop.AppForegroundListener;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GUIView implements View {

    private GameInfo gameInfo;
    private ServerHandler serverHandler;
    private GameSetupController gameSetupController;

    private NicknameController nicknameController;
    private Scanner scanner;

    private SceneController sceneController;

    public GUIView(ServerHandler serverHandler){
        this.serverHandler = serverHandler;
    }

    @Override
    public void start() {


    }

    @Override
    public void gameSetUp() {
        gameSetupController = new GameSetupController(serverHandler);
        Platform.runLater(() -> SceneController.changeRootPane(gameSetupController, "gameSetup.fxml"));
        /*
        if (response.equals("YES"))
            serverHandler.setGame(true, -1);
        else {
            serverHandler.refreshLobbies();
        }*/
    }

    @Override
    public void refreshLobbies(List<String[]> lobbies, boolean firstLobby) {
        /*if (firstLobby) {
            System.out.println("There isn't any lobby, we will create one just for you");

            }
            serverHandler.setGame(true, -1);
            //} else{
            //  lobbySelection(lobbies);
        }*/
    }

    @Override
    public void fullLobby(List<String[]> lobbies) {
        gameSetupController.errorMessage("Errore");
        serverHandler.refreshLobbies();
    }

    @Override
    public void initialSetUp() {

    }

    @Override
    public void playerSetUp(boolean requestAgain) {
        if (!requestAgain) {
            nicknameController = new NicknameController(serverHandler);
            Platform.runLater(() -> SceneController.changeRootPane(nicknameController, "nickname.fxml"));
        } else
            nicknameController.errorMessage("This nickname has already been taken, please try again");
    }

    @Override
    public void colorSetUp(List<TowerColor> tower, boolean requestAgain) {

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
    public void printInterrupt(String nickname, boolean notEntered) {

    }

    @Override
    public void newGame(String nickname) {

    }

    @Override
    public void printServerDown() {

    }

    @Override
    public void stopClearer() {

    }

    @Override
    public void bufferClearer() {

    }

    @Override
    public void printError(ClientException exception) {

    }

    private int lobbyCounter(List<String[]> lobbies){
        int count = 0;
        for (String[] s : lobbies)
            if (s.length < 6)
                count++;

        return count;
    }
}
