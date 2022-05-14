package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.viewUtilities.GameInfo;
import it.polimi.ingsw.client.viewUtilities.IPValidator;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.enumerations.TowerColor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.Scanner;

public class GUIView extends Application implements View {
    private GameInfo gameInfo;
    private ServerHandler serverHandler;
    private Scanner scanner;
    @Override
    public void start() {
        String serverIP;
        boolean valid;

        do {
            System.out.print("Enter the server IP [Press enter for default IP]: ");
            serverIP = scanner.nextLine();

            if (serverIP.equals("")){
                valid = true;
                serverIP = IPValidator.getDefaultIP();
                System.out.println(" > Default server IP has been chosen, " + serverIP);
            } else {
                if (!IPValidator.isCorrectIP(serverIP)){
                    valid = false;
                    System.out.println(" > Server IP not valid. Please try again.");
                } else {
                    valid = true;
                }
            }
        } while (!valid);

        serverHandler.initConnection(serverIP);
    }

    @Override
    public void gameSetUp() {
        boolean valid;
        String response;


        System.out.print("Vuoi cominciare una nuova partita? \u001b[31m♥ [Yes/No]:");
        do {
            valid = true;
            response = scanner.nextLine().toUpperCase();
            if (!(response.equals("YES") || response.equals("NO"))){
                valid = false;
                System.out.print("Scelta non valida, prova di nuovo: ");
            }
        } while (!valid);

        if (response.equals("YES"))
            serverHandler.setGame(true, -1);
        else {
            serverHandler.refreshLobbies();
        }
    }

    @Override
    public void refreshLobbies(List<String[]> lobbies, boolean firstLobby) {

    }

    @Override
    public void fullLobby(List<String[]> lobbies) {

    }

    @Override
    public void initialSetUp() {

    }

    @Override
    public void playerSetUp(boolean requestAgain) {

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

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/gameSetup.fxml"));
        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public void buttonClicked(ActionEvent actionEvent) {
    }
}
