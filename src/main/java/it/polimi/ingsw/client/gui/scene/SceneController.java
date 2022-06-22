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
    public static void showError(String type, String message) {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/errorScene.fxml"));

        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ErrorSceneController controller = loader.getController();
        Scene alertScene = new Scene(parent);
        controller.setScene(alertScene);
        controller.setErrorType(type);
        controller.setErroreMessage(message);
        controller.displayAlert();
    }

    public static void setActiveScene(Scene activeScene1){
        activeScene = activeScene1;
    }

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static Object getActiveController() {
        return activeController;
    }
}