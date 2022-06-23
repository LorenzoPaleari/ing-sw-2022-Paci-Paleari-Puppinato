package it.polimi.ingsw.client.gui.scene;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import it.polimi.ingsw.client.gui.JavaFXInit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class EndController implements GenericSceneController{

    @FXML
    private Button yesBtn, noBtn;
    @FXML
    private Text newGame, winner, draw;

    @FXML
    public void initialize(){
        yesBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            JavaFXInit.setStopped(true);
            JavaFXInit.getPlayer().stop();
            JavaFXInit.setStopped(false);
            JavaFXInit.getStage().close();
            Platform.runLater(JavaFXInit::reStart);
        });
        noBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            System.exit(0);
        });
    }

    public void winner(String winner1, String winner2, String nickname){
        winner.setVisible(true);
        if(winner2==null){
            if(winner1.equals(nickname))
                winner.setText("YOU HAVE WON!!!");
            else
                winner.setText(winner1+" HAS WON");
        }
        else{
            draw.setVisible(true);
            if(winner1.equals(nickname))
                winner.setText("You and " +winner2+ " have won the game");
            else if(winner2.equals(nickname))
                winner.setText("You and " +winner1+ " have won the game");
            else
                winner.setText(winner1 +" and " +winner2+ " have won the game");
        }

        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        double max = 0;
        for (int i = 0; i < winner.getText().length(); i++){
            max += fontLoader.getCharWidth(winner.getText().charAt(i), winner.getFont());
        }
        winner.setLayoutX((1280 - max)/2);
    }

    public void newGame(){
        yesBtn.setVisible(true);
        noBtn.setVisible(true);
        newGame.setVisible(true);
    }
}
