package it.polimi.ingsw.client.gui.scene;

import it.polimi.ingsw.client.ServerHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class CreateNewGameController implements GenericSceneController{
    private ServerHandler serverHandler;
    @FXML
    private Button joinBtn;



    private ToggleGroup toggleGroupNumPlayers;

    @FXML
    private RadioButton radio2;

    @FXML
    private RadioButton radio3;

    private ToggleGroup toggleGroupMode;

    @FXML
    private RadioButton expertYes;

    @FXML
    private RadioButton expertNo;

    private Label infoLabel;

    public CreateNewGameController(ServerHandler serverHandler1){
        serverHandler = serverHandler1;
    }


    @FXML
    public void initialize(){
        joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);

        toggleGroupNumPlayers = new ToggleGroup();
        radio2.setToggleGroup(toggleGroupNumPlayers);
        radio3.setToggleGroup(toggleGroupNumPlayers);

        toggleGroupMode = new ToggleGroup();
        expertNo.setToggleGroup(toggleGroupMode);
        expertYes.setToggleGroup(toggleGroupMode);


    }

    @FXML
    private void onJoinBtnClick(Event event) {

    }


    public void errorMessage(String message){
        infoLabel.setText(message);
    }
}
