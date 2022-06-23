package it.polimi.ingsw.client.gui.scene;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.viewUtilities.GameInfo;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class CharacterSceneController implements GenericSceneController {

    private final Stage stage;

    private double xOffset;
    private double yOffset;
    private GameInfo gameInfo;
    private int number, islandSelected;
    private boolean playable = false;
    private CharacterType characterType;
    private ServerHandler serverHandler;
    private MainController mainController;
    private String color;
    private int[] colorSelected = {0,0,0,0,0,0,0,0,0,0};
    @FXML
    private StackPane rootPane;
    @FXML
    private Button close, play;
    @FXML
    private Text request, characterName, ok;
    @FXML
    private TextArea description;
    @FXML
    private Pane character;
    @FXML
    private ListView<String> colors;
    @FXML
    private MenuButton green1, red1, yellow1, pink1, blue1, green2, red2, yellow2, pink2, blue2;

    @FXML
    public void initialize() {
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onRootPaneMousePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onRootPaneMouseDragged);
        close.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            mainController.setCharacterPlayed(false);
            mainController.setIslandSelector(false);
            stage.close();
        });
        play.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onPlayClicked);
        String[] color = {"Green", "Red", "Yellow", "Pink", "Blue"};
        colors.getItems().addAll(color);
        MenuButton[] selection = {green1, red1, yellow1, pink1, blue1, green2, red2, yellow2, pink2, blue2};
        for (MenuButton i : selection)
            for (MenuItem node : i.getItems())
                node.setOnAction(event -> {
                    int multi = Integer.parseInt(i.getId().substring(i.getId().charAt(i.getId().length() - 1)));
                    int colorIndex = PawnColor.lookup(i.getId().substring(0, i.getId().length() - 1)).getIndex();
                    colorSelected[multi*colorIndex] = Integer.parseInt(node.getText());
                });
    }

    private <T extends Event> void onPlayClicked(T t) {
        int sum1 = 0, sum2 = 0;
        boolean valid = true;
        List<Integer> tempColor = new ArrayList<>();
        List<Integer> tempColor2 = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            if (colorSelected[i] > 0) {
                tempColor.add(0, i);
                if (gameInfo.getEntranceStudents(gameInfo.getFrontPlayer())[i] < colorSelected[i])
                    valid = false;
            }
            if (colorSelected[i+5] > 0) {
                tempColor2.add(i);
                if (characterType.equals(CharacterType.THIEF) && gameInfo.getCharacterInfo(number)[i] < colorSelected[i+5])
                    valid = false;
                if (characterType.equals(CharacterType.MINSTREL) && gameInfo.getDiningStudents(gameInfo.getFrontPlayer())[i] < colorSelected[i+5])
                    valid = false;
            }
            sum1 += colorSelected[i];
            sum2 += colorSelected[5 + i];
        }
        int[] colorListFinal;
        colorListFinal = new int[characterType.equals(CharacterType.MINSTREL) ? 4 : 6];
        for (int i = 0; i < Math.min(Math.min(characterType.equals(CharacterType.MINSTREL) ? 2 : 3, tempColor.size()), tempColor2.size()); i++){
            colorListFinal[i] = tempColor.get(i);
            colorListFinal[i+(characterType.equals(CharacterType.MINSTREL) ? 2 : 3)] = tempColor2.get(i);
        }
        for (int i = Math.min(Math.min(characterType.equals(CharacterType.MINSTREL) ? 2 : 3, tempColor.size()), tempColor2.size()); i < (characterType.equals(CharacterType.MINSTREL) ? 2 : 3); i++){
            colorListFinal[i] = -1;
            colorListFinal[i+(characterType.equals(CharacterType.MINSTREL) ? 2 : 3)] = -1;
        }


        if (characterType.equals(CharacterType.FARMER) || characterType.equals(CharacterType.MAGIC_DELIVERY_MAN) || characterType.equals(CharacterType.CENTAUR) || characterType.equals(CharacterType.KNIGHT))
            serverHandler.useCharacterRequest(number);
        else if ((characterType.equals(CharacterType.HERALD) || characterType.equals(CharacterType.GRANDMOTHER_HERBS))&& islandSelected!=-1)
            serverHandler.useCharacterRequest(islandSelected, number);
        else if ((characterType.equals(CharacterType.MONK)) && islandSelected!= -1 && color != null)
            serverHandler.useCharacterRequest(islandSelected, PawnColor.lookup(color), number);
        else if (((characterType.equals(CharacterType.SPOILED_PRINCESS)) && color!= null) || ((characterType.equals(CharacterType.MUSHROOM_HUNTER) || characterType.equals(CharacterType.THIEF)) && colors.getSelectionModel().getSelectedItem() != null))
            serverHandler.useCharacterRequest(color != null ? PawnColor.lookup(color) : PawnColor.lookup(colors.getSelectionModel().getSelectedItem()) , number);
        else if ((characterType.equals(CharacterType.MINSTREL) || (characterType.equals(CharacterType.THIEF))) && valid) {
            if (characterType.equals(CharacterType.MINSTREL) && sum1 <= 2 && sum1 == sum2)
                serverHandler.useCharacterRequest(colorListFinal, number);
            else if (characterType.equals(CharacterType.MINSTREL))
                Platform.runLater(() -> SceneController.showError("CHARACTER PLAY", "You have to choose at max 2 students, also you must move the same amount"));
            if (characterType.equals(CharacterType.THIEF) && sum1 <= 3 && sum1 == sum2)
                serverHandler.useCharacterRequest(colorListFinal, number);
            else if (characterType.equals(CharacterType.THIEF))
                Platform.runLater(() -> SceneController.showError("CHARACTER PLAY", "You have to choose at max 3 students, also you must move the same amount"));
        }
        else if (characterType.equals(CharacterType.MINSTREL) || (characterType.equals(CharacterType.THIEF)))
            Platform.runLater(() -> SceneController.showError("CHARACTER PLAY", "You don't have enough students"));
        else
            Platform.runLater(() -> SceneController.showError("CHARACTER PLAY", "Some actions are required in order to play this card, Please fulfill them."));

        mainController.setCharacterPlayed(false);
        mainController.setIslandSelector(false);
        stage.close();
    }

    public CharacterSceneController(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
        stage = new Stage();
        stage.initOwner(SceneController.getActiveScene().getWindow());
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        xOffset = 0;
        yOffset = 0;
    }

    public void displayAlert() {
        stage.showAndWait();
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    private void onRootPaneMousePressed(MouseEvent event) {
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    private void onRootPaneMouseDragged(MouseEvent event) {
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    public void setInfo(GameInfo gameInfo, int number) {
        ok.setVisible(false);
        MenuButton[] selection = {green1, red1, yellow1, pink1, blue1, green2, red2, yellow2, pink2, blue2};
        for (MenuButton i : selection)
            i.setVisible(false);

        colorSelected = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        islandSelected = -1;
        color = null;
        playable = false;
        colors.setVisible(false);
        this.gameInfo = gameInfo;
        this.number = number;
        characterType = gameInfo.getCharacter(number);
        character.getStyleClass().clear();
        character.getStyleClass().add(gameInfo.getCharacter(number).getText().toLowerCase());
        description.setText(gameInfo.getCharacter(number).getDescription());
        characterName.setText(gameInfo.getCharacter(number).getText().replaceAll("_", " "));
        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        double max = 0;
        for (int i = 0; i < characterName.getText().length(); i++){
            max += fontLoader.getCharWidth(characterName.getText().charAt(i), characterName.getFont());
        }
        characterName.setLayoutX((600-max)/2);
        if (characterType.equals(CharacterType.FARMER) || characterType.equals(CharacterType.MAGIC_DELIVERY_MAN) || characterType.equals(CharacterType.CENTAUR) || characterType.equals(CharacterType.KNIGHT)){
            setText("");
            playable = true;
        }
        if ((characterType.equals(CharacterType.HERALD)))
            setText("Select an Island");
        if (characterType.equals(CharacterType.GRANDMOTHER_HERBS))
            setText("Move a No Entry Tile on an Island");
        if ((characterType.equals(CharacterType.MONK)))
            setText("Move a Student on an Island");
        if ((characterType.equals(CharacterType.SPOILED_PRINCESS)))
            setText("Move a Student on the Board");
        if (characterType.equals(CharacterType.MUSHROOM_HUNTER) || characterType.equals(CharacterType.THIEF)) {
            setText("Select a Color");
            colors.setVisible(true);
        }
        if (characterType.equals(CharacterType.MINSTREL) || characterType.equals(CharacterType.JESTER)){
            if (characterType.equals(CharacterType.MINSTREL))
                setText("Entrance - Dining");
            else
                setText("Entrance - Card");
            for (MenuButton i : selection)
                i.setVisible(true);
        }
    }

    public void setMain(MainController mainController) {
        this.mainController = mainController;
    }

    public void setIslandSelected(int i) {
        islandSelected = i;
    }

    public void setColor(String string) {
        color = string;
    }

    public void setText(String s){
        request.setText(s);
        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        double max = 0;
        for (int i = 0; i < request.getText().length(); i++){
            max += fontLoader.getCharWidth(request.getText().charAt(i), request.getFont());
        }
        request.setLayoutX(225 + (335-max)/2);
    }

    public void setOk(){
        ok.setVisible(true);
    }
}
