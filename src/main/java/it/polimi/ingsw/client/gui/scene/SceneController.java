package it.polimi.ingsw.client.gui.scene;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneController {

    private static Scene activeScene;
    private static GenericSceneController activeController;

    public static void setActiveScene(Event event){
        activeScene = ((Node) event.getSource()).getScene();

    }
    public static void changeRootPane(GenericSceneController controller, String fxml) {

        changeRootPane(controller, activeScene, fxml);
    }

    public static void changeRootPane(GenericSceneController controller, Scene scene, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            loader.setController(controller);
            activeController = controller;
            Parent root = loader.load();
            activeScene = scene;
            activeScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setActiveScene(Scene activeScene1){
        activeScene = activeScene1;
    }
}