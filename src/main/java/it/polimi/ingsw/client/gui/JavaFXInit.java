package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.scene.InitController;
import it.polimi.ingsw.client.gui.scene.SceneController;
import it.polimi.ingsw.client.gui.scene.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXInit extends Application {

    @Override
    public void start(Stage stage) {
        ServerHandler serverHandler = new ServerHandler();
        GUIView view = new GUIView(serverHandler);
        serverHandler.setView(view);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/init.fxml"));
        loader.setController(new InitController(serverHandler));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            System.exit(1);
        }

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setWidth(1280d);
        stage.setHeight(720d);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setTitle("Eriantys");
        stage.show();

    }
}
