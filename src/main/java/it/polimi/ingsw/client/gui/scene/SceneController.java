package it.polimi.ingsw.client.gui.scene;

import javafx.scene.Scene;

public class SceneController {

    private static Scene activeScene;

    private static GenericSceneController activeController;

    public static Scene getActiveScene(){return activeScene;}

    public static GenericSceneController getActiveController(){return activeController;}
}
