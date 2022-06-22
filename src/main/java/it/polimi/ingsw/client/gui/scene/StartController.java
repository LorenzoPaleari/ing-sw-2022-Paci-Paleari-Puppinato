package it.polimi.ingsw.client.gui.scene;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.viewUtilities.IPValidator;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class StartController implements GenericSceneController{
    @FXML
    private TextField serverIpField;
    @FXML
    private TextField serverIpField1;
    @FXML
    private Button joinBtn;
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
        String serverPort = serverIpField1.getText();
        boolean valid = false;
        if (serverPort.equals(""))
            serverPort = IPValidator.getDefaultPort();

        if (serverIP.equals(""))
            serverIP = IPValidator.getDefaultIP();

        if (!IPValidator.isCorrectIP(serverIP) || !IPValidator.isCorrectPort(serverPort)) {
                SceneController.showError("NETWORK SETTING", "The inserted IP or PORT are not valid.");
        } else
            valid = true;

        if(valid){
            String finalServerIP = serverIP;
            String finalServerPort = serverPort;
            (new Thread(() -> {
                try {
                    serverHandler.initConnection(finalServerIP, finalServerPort);
                } catch (Exception ignored) {
                }
            })).start();
        }


    }

}
