package it.polimi.ingsw.client.gui.scene;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.viewUtilities.GameInfo;
import it.polimi.ingsw.model.enumerations.PawnColor;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainController implements GenericSceneController{

    @FXML
    private StackPane rootPane;
    @FXML
    private Group root;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Pane board1, board2, board3;
    @FXML
    private Group bd1Entrance, bd1Students, bd1Professors, bd1Towers;
    @FXML
    private Group bd2Entrance, bd2Students, bd2Professors, bd2Towers;
    @FXML
    private Group bd3Entrance, bd3Students, bd3Professors, bd3Towers;
    @FXML
    private Label name1, name2, name3;
    @FXML
    private Pane character1, character2, character3;
    @FXML
    private Pane lastAssistant1, lastAssistant2, lastAssistant3;
    @FXML
    private Pane cloud1, cloud2, cloud3;
    @FXML
    private Group assistants, tableProfessors;
    @FXML
    private ImageView coin1, coin2, coin3;
    @FXML
    private Label coinNumber1, coinNumber2, coinNumber3;
    @FXML
    private Label info, info1;
    @FXML
    private Pane island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11, island12;
    private GameInfo gameInfo;
    private ServerHandler serverHandler;

    public MainController(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @FXML
    public void initialize() {
        Pane[] clouds = {cloud1, cloud2, cloud3};
        Integer[] assistantNumbers = gameInfo.getAssistants();
        ImageView[] coins = {coin1, coin2, coin3};
        Label[] numCoins = {coinNumber1, coinNumber2, coinNumber3};
        Pane[] characters = {character1, character2, character3};
        Pane[] islands = {island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11, island12};
        int numPlayer = gameInfo.getNumPlayer();

        info.setText("It's "+gameInfo.getCurrentPlayer()+" Turn!");
        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        double max = 0;
        for (int i = 0; i < info.getText().length(); i++){
            max += fontLoader.getCharWidth(info.getText().charAt(i), info.getFont());
        }
        info.setLayoutX(387.5 - max/2);
        info1.setText("Student Moves Remaining: "+gameInfo.getRemainingMoves());

        if (numPlayer == 3) {
            board3.setVisible(true);
            name3.setVisible(true);
            cloud3.setVisible(true);
        }

        if (gameInfo.isExpertMode()){
            for (int i = 0; i < numPlayer; i++) {
                coins[i].setVisible(true);
                numCoins[i].setVisible(true);
            }
            for (int i = 0; i < 3; i++)
                characterEffect(characters[i], i);
        }

        int index = 0;
        for (Node i : assistants.getChildren()) {
            if (i.getId() == null && index < assistantNumbers.length) {
                i.getStyleClass().add("assistant"+assistantNumbers[index]);
                assistantEffect(i);
                index++;
            }
        }

        for (int i = 0; i < numPlayer; i++)
            cloudEffect(clouds[i], i);

        index = 0;
        for (String s : gameInfo.getPlayersName()) {
            if (s.equals(gameInfo.getFrontPlayer()))
                name1.setText(s);
            else if (index == 0) {
                name2.setText(s);
                index++;
            } else
                name3.setText(s);
        }

        for (Pane pane : islands)
            islandEffect(pane);

        islands[0].getChildren().get(7).setVisible(true);

        cloud1.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = cloud1.startDragAndDrop(TransferMode.MOVE);

                ClipboardContent content = new ClipboardContent();
                content.putString(cloud1.getId());
                db.setContent(content);

                event.consume();
            }
        });
    }

    private void cloudEffect(Pane pane, int number){
        int index = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < gameInfo.getCloudStudents(number)[i]; j++) {
                pane.getChildren().get(index).getStyleClass().add(PawnColor.getColor(i).toString().toLowerCase());
                index++;
            }
        }

        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            System.out.print("CLICKED" + pane.getId().substring(5));
        });
    }
    private void assistantEffect(Node node1){
        double value = (70 * 125) / 82.5;
        Pane node = (Pane) node1;

        node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            node.setLayoutY(109 - value);
            node.setPrefHeight(value);
        });
        node.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            node.setLayoutY(110 - 20);
            node.setPrefHeight(20);
        });
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            System.out.print("Assistant"+node.getStyleClass().get(0).substring(9)+" Clicked");
        });
    }

    private void characterEffect(Pane pane, int number){
        pane.getStyleClass().add(gameInfo.getCharacter(number).getText().toLowerCase());
        for (int j = 0; j < gameInfo.getCharacterInfo(number)[5]; j++)
            pane.getChildren().get(j).getStyleClass().add("doNotEnter");
        for (int j = 0; j < 5; j++){
            if (gameInfo.getCharacterInfo(number)[j] > 0) {
                Pane tempPane = (Pane) pane.getChildren().get(j);
                tempPane.getStyleClass().add(PawnColor.getColor(j).toString().toLowerCase());
                Label label = (Label) tempPane.getChildren().get(0);
                label.setVisible(true);
                label.setText("x" + gameInfo.getCharacterInfo(number)[j]);
            }
        }

        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            System.out.print("Character" + pane.getId().substring(9));
        });
    }

    private void islandEffect(Pane pane){
        pane.setOnDragOver(event -> {
            if (event.getGestureSource() != pane &&
                    event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        pane.setOnDragEntered(event -> {
            if (event.getGestureSource() != pane &&
                    event.getDragboard().hasString()) {
                pane.setStyle("-fx-background-color: green");
            }

            event.consume();
        });

        pane.setOnDragExited(event -> {
            pane.setStyle("-fx-background-color: none");

            event.consume();
        });

        pane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                System.out.print(db.getString() + " - "+ pane.getId().substring(6));
                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    public void update(){

    }

    public void setGameInfo(GameInfo gameInfo){
        this.gameInfo = gameInfo;
    }
}
