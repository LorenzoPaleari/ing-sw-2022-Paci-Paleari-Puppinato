package it.polimi.ingsw.client.gui.scene;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.viewUtilities.IPValidator;
import it.polimi.ingsw.listeners.ViewListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class NicknameController extends ViewListener implements GenericSceneController {

    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinBtn;

    @FXML
    private Label errorLabel;

    private ServerHandler serverHandler;

    public NicknameController(ServerHandler serverHandler1){
        serverHandler = serverHandler1;
    }

    @FXML
    public void initialize() {
        joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);
    }

    /**
     * Handle click on Join button.
     *
     * @param event the mouse click event.
     */
    private void onJoinBtnClick(Event event) {
        String nickname = nicknameField.getText();
        serverHandler.setPlayerInfo(nickname);



    }

    public void errorMessage(String message){
        errorLabel.setText(message);
    }
}
