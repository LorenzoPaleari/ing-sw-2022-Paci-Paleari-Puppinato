package it.polimi.ingsw.client.gui.scene;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.JavaFXInit;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class InitController implements GenericSceneController{

    private ServerHandler serverHandler;
    @FXML
    private Button startBtn;
    @FXML
    private Pane music;
    @FXML
    private Button exitBtn;

    /**
     * Constructor
     * Initializes this controller with the server handler associated
     * @param serverHandler1 the server handler to associate
     */
    public InitController(ServerHandler serverHandler1){
        serverHandler = serverHandler1;
    }

    /**
     * Registers the start and exit buttons for the event MOUSE_CLICKED, then set the music
     */
    @FXML
    public void initialize(){
        startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onStartBtnClick);
        exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onExitBtnClick);
        JavaFXInit.musicEffect(music);
    }

    @FXML
    private void onStartBtnClick(Event event) {
        SceneController.setActiveScene(event);
        Platform.runLater(() -> SceneController.changeRootPane(new StartController(serverHandler), "start.fxml"));
    }

    @FXML
    private void onExitBtnClick(Event event) {
        System.exit(0);
    }
}
