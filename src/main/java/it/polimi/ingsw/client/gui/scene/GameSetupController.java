package it.polimi.ingsw.client.gui.scene;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.viewUtilities.LobbyValidator;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class GameSetupController implements GenericSceneController{

    private ServerHandler serverHandler;
    @FXML
    private Button yesBtn;

    @FXML
    private Button noBtn;

    @FXML
    private ChoiceBox lobbySelector;

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

    public GameSetupController(ServerHandler serverHandler1){
        serverHandler = serverHandler1;
    }


    @FXML
    public void initialize(){
        yesBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onYesBtnClick);
        noBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNoBtnClick);
        toggleGroupNumPlayers = new ToggleGroup();
        radio2.setToggleGroup(toggleGroupNumPlayers);
        radio3.setToggleGroup(toggleGroupNumPlayers);

        toggleGroupMode = new ToggleGroup();
        expertNo.setToggleGroup(toggleGroupMode);
        expertYes.setToggleGroup(toggleGroupMode);

        radio2.setVisible(false);
        radio3.setVisible(false);
        expertYes.setVisible(false);
        expertNo.setVisible(false);

    }

    @FXML
    private void onYesBtnClick(Event event) {
        serverHandler.setGame(true, -1);
        radio2.setVisible(true);
        radio3.setVisible(true);
        expertYes.setVisible(true);
        expertNo.setVisible(true);
        lobbySelector.setVisible(false);
    }

    @FXML
    private void onNoBtnClick(Event event) {
        lobbySelector.setVisible(true);
        radio2.setVisible(false);
        radio3.setVisible(false);


        serverHandler.refreshLobbies();
    }

    public void errorMessage(String message){
        infoLabel.setText(message);
    }

    public void fillChoiceBox(List<String[]> lobbies){
        int lobbySize = lobbyCounter(lobbies);


        //serverHandler.setGame(false, LobbyValidator.validLobbyCount(numLobby, lobbies)); //ok
        serverHandler.refreshLobbies(); //refresh


        int k = 0;
        int i = LobbyValidator.validLobbyCount(0, lobbies);
        StringBuilder base = new StringBuilder();
        while(k < lobbyCounter(lobbies)) {
            if (lobbies.get(i).length < 6) {
                k++;
                base.delete(0, base.length());
                base.append("  > Lobby-").append(k).append(" : ");
                for (int j = 0; j < lobbies.get(i).length - 2; j++)
                    if (j == 0) {
                        base.append(lobbies.get(i)[j]);
                    } else {
                        base.append(", ").append(lobbies.get(i)[j]);
                    }
                String expert = "Yes";
                if (lobbies.get(i)[lobbies.get(i).length - 1].equals("false"))
                    expert = "No";
                lobbySelector.getItems().add(base + "       NumPlayers: "+lobbies.get(i)[lobbies.get(i).length - 2]+" - ExpertMode: " + expert);
            }
            i++;
        }

    }

    private int lobbyCounter(List<String[]> lobbies){
        int count = 0;
        for (String[] s : lobbies)
            if (s.length < 6)
                count++;

        return count;
    }
}
