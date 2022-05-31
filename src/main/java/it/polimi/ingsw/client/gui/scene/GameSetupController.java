package it.polimi.ingsw.client.gui.scene;

import it.polimi.ingsw.client.ServerHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GameSetupController implements GenericSceneController{

    private ServerHandler serverHandler;
    @FXML
    private Button yesBtn;

    @FXML
    private Button noBtn;

    public GameSetupController(ServerHandler serverHandler1){
        serverHandler = serverHandler1;
    }


    @FXML
    public void initialize(){
        yesBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onYesBtnClick);
        noBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNoBtnClick);

    }

    @FXML
    private void onYesBtnClick(Event event) {
        serverHandler.setGame(true, -1);

    }

    @FXML
    private void onNoBtnClick(Event event) {
        serverHandler.refreshLobbies();
    }
}
