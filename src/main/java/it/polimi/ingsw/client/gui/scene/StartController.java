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

public class StartController extends ViewListener implements GenericSceneController{
    @FXML
    private TextField serverIpField;

    @FXML
    private Button joinBtn;

    @FXML
    private Label errorLabel;

    private ServerHandler serverHandler;

    public StartController(ServerHandler serverHandler1){
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
        String serverIP = serverIpField.getText();
        boolean valid = false;
        if (serverIP.equals("")){
            serverIP = IPValidator.getDefaultIP();
            valid = true;
        } else {
            if (!IPValidator.isCorrectIP(serverIP)) {
                errorLabel.setText("Server IP not valid. Please try again.");
                errorLabel.setVisible(true);
            }
        }
        if(valid){
            SceneController.setActiveScene(event);
            serverHandler.initConnection(serverIP);
        }


    }

}
