package it.polimi.ingsw.client.gui.scene;

import it.polimi.ingsw.listeners.ViewListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GameSetupController extends ViewListener implements GenericSceneController{
    @FXML
    private TextField serverIpField;

    @FXML
    private Button joinBtn;

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
        System.out.println(serverIpField.getText());
        joinBtn.setText(serverIpField.getText());
        //serverHandler.initConnection(serverIpField.getText());
        //new Thread(() -> notifyObserver(obs -> obs.onUpdateNickname(nickname))).start();
    }

}
