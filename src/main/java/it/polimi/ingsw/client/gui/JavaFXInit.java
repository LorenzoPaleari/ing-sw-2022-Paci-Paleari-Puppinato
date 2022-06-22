package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.scene.InitController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXInit extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) {
        JavaFXInit.stage = stage;
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

        stage.setOnCloseRequest(windowEvent -> {
            System.exit(0);
        });

        stage.setScene(scene);
        stage.setWidth(1280d);
        stage.setHeight(747d);
        stage.setResizable(true);
        stage.setTitle("Eriantys");
        stage.show();
    }

    public static void reStart(){
        JavaFXInit.stage = new Stage();
        ServerHandler serverHandler = new ServerHandler();
        GUIView view = new GUIView(serverHandler);
        serverHandler.setView(view);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(JavaFXInit.class.getResource("/fxml/init.fxml"));
        loader.setController(new InitController(serverHandler));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            System.exit(1);
        }

        Scene scene = new Scene(root);

        stage.setOnCloseRequest(windowEvent -> {
            System.exit(0);
        });

        stage.setScene(scene);
        stage.setWidth(1280d);
        stage.setHeight(747d);
        stage.setResizable(true);
        stage.setTitle("Eriantys");
        stage.show();
    }

    public static Stage getStage(){
        return stage;
    }
}
