package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.gui.scene.*;
import it.polimi.ingsw.client.viewUtilities.GameInfo;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.model.enumerations.TowerColor;

import javafx.application.Platform;

import java.util.List;

public class GUIView implements View {
    private ServerHandler serverHandler;

    public GUIView(ServerHandler serverHandler){
        this.serverHandler = serverHandler;
    }

    @Override
    public void start() {
    }

    @Override
    public void gameSetUp() {
        GameSetupController gameSetupController = getGameSetupController();
    }

    @Override
    public void refreshLobbies(List<String[]> lobbies, boolean firstLobby) {
        GameSetupController gameSetupController = getGameSetupController();
        if (firstLobby){
            serverHandler.setGame(true, -1);
            Platform.runLater(() -> SceneController.showError("SETUP WARNING", "There isn't any lobby, we have created one for you"));
        } else {
            Platform.runLater(() -> gameSetupController.lobbySelection(lobbies));
        }
    }

    @Override
    public void fullLobby() {
        Platform.runLater(() -> SceneController.showError("LOBBY SELECTION", "The lobby is full, try again."));
        serverHandler.refreshLobbies();
    }

    @Override
    public void initialSetUp() {
        GameSetupController gameSetupController = getGameSetupController();
        Platform.runLater(gameSetupController::newGame);
    }

    @Override
    public void playerSetUp(boolean requestAgain) {
        if (!requestAgain) {
            NicknameController nicknameController = new NicknameController(serverHandler);
            Platform.runLater(() -> SceneController.changeRootPane(nicknameController, "nickname.fxml"));
        } else
            Platform.runLater(() -> SceneController.showError("NICKNAME SELECTION ERROR", "This nickname has already been taken, please try again"));
    }

    @Override
    public void colorSetUp(List<TowerColor> tower, boolean requestAgain) {
        ColorSetUpContoller colorSetUpContoller = getColorSetUpController();
        if (requestAgain)
            Platform.runLater(() -> SceneController.showError("COLOR SELECTION", "This color has already been taken."));
        Platform.runLater(() -> colorSetUpContoller.setUp(tower));
    }

    @Override
    public void printGameBoard(GameInfo gameInfo) {
        MainController mainController = getMainController(gameInfo);
        Platform.runLater(mainController::update);
    }

    @Override
    public void choseAction() {
        return;
    }

    @Override
    public void printWinner(String winner1, String winner2, String nickname) {
        EndController endController = getEndController();
        serverHandler.endConnection();
        Platform.runLater(() -> endController.winner(winner1, winner2, nickname));
        newGame("");
    }

    @Override
    public void printInterrupt(String nickname, boolean notEntered) {
        if (notEntered) {
            Platform.runLater(() -> SceneController.showError("LOBBY SELECTION", "The game was interrupted just before your entrance, please choose another Lobby."));
            Platform.runLater(() -> serverHandler.refreshLobbies());
        } else {
            Platform.runLater(() -> SceneController.showError("PLAYER DISCONNECTION", nickname + " disconnected from the server. Game ended!"));
            newGame("");
        }
    }

    @Override
    public void newGame(String nickname) {
        EndController endController = getEndController();
        Platform.runLater(() -> endController.newGame());
    }

    @Override
    public void printServerDown() {
        Platform.runLater(() -> SceneController.showError("SERVER DISCONNECTED", "Server is down, the game will close."));
    }

    @Override
    public void stopClearer() {}

    @Override
    public void bufferClearer() {}

    @Override
    public void printError(ClientException exception) {
        if (exception.getErrorType().equals(ErrorType.NOT_ENOUGH_MONEY))
            Platform.runLater(() -> SceneController.showError(exception.getErrorType().toString(), exception.getErrorType().getErrorText() + exception.getPrice() + ")"));
        else if (exception.getErrorType().equals(ErrorType.MAX_STUDENT_REACHED))
            Platform.runLater(() -> SceneController.showError(exception.getErrorType().toString(), exception.getErrorType().getErrorText() + exception.getPawnColor()));
        else
            Platform.runLater(() -> SceneController.showError(exception.getErrorType().toString(), exception.getErrorType().getErrorText()));
    }

    private GameSetupController getGameSetupController() {
        GameSetupController controller;
        try {
            controller = (GameSetupController) SceneController.getActiveController();
        } catch (ClassCastException e) {
            controller = new GameSetupController(serverHandler);
            GameSetupController finalController = controller;
            Platform.runLater(() -> SceneController.changeRootPane(finalController, "gameSetup.fxml"));
        }
        return controller;
    }

    private MainController getMainController(GameInfo gameInfo) {
        MainController controller;
        try {
            controller = (MainController) SceneController.getActiveController();
            MainController finalController1 = controller;
            Platform.runLater(() -> finalController1.setGameInfo(gameInfo));
        } catch (ClassCastException e) {
            controller = new MainController(serverHandler);
            MainController finalController = controller;
            Platform.runLater(() -> finalController.setGameInfo(gameInfo));
            Platform.runLater(() -> SceneController.changeRootPane(finalController, "main.fxml"));
        }
        return controller;
    }

    private ColorSetUpContoller getColorSetUpController() {
        ColorSetUpContoller controller;
        try {
            controller = (ColorSetUpContoller) SceneController.getActiveController();
        } catch (ClassCastException e) {
            controller = new ColorSetUpContoller(serverHandler);
            ColorSetUpContoller finalController = controller;
            Platform.runLater(() -> SceneController.changeRootPane(finalController, "colorSetUp.fxml"));
        }
        return controller;
    }

    private EndController getEndController(){
        EndController controller;
        try {
            controller = (EndController) SceneController.getActiveController();
        } catch (ClassCastException e) {
            controller = new EndController();
            EndController finalController = controller;
            Platform.runLater(() -> SceneController.changeRootPane(finalController, "end.fxml"));
        }
        return controller;
    }

}
