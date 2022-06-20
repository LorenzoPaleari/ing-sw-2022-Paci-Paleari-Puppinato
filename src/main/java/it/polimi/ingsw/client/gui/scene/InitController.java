package it.polimi.ingsw.client.gui.scene;

import it.polimi.ingsw.client.ServerHandler;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class InitController implements GenericSceneController{

    private ServerHandler serverHandler;
    @FXML
    private Button startBtn;

    @FXML
    private Button exitBtn;

    public InitController(ServerHandler serverHandler1){
        serverHandler = serverHandler1;
    }


    @FXML
    public void initialize(){
        startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onStartBtnClick);
        exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onExitBtnClick);

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
