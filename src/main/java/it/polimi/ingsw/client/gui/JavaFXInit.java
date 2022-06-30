package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.gui.scene.InitController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JavaFXInit extends Application {

    private static Stage stage;
    private static MediaPlayer player;
    private static boolean muted, stopped = false;
    private static int current = 0;
    private static List<Media> playlist;

    @Override
    public void start(Stage stage) {
        JavaFXInit.stage = stage;
        ServerHandler serverHandler = new ServerHandler();
        GUIView view = new GUIView(serverHandler);
        serverHandler.setView(view);
        loading(serverHandler);
    }

    /**
     *If a player wants to start a new game reset all the variables and creates new one
     */
    public static void reStart(){
        JavaFXInit.stage = new Stage();
        ServerHandler serverHandler = new ServerHandler();
        GUIView view = new GUIView(serverHandler);
        serverHandler.setView(view);
        loading(serverHandler);
    }

    /**
     * loads the first scene and create the stage
     * @param serverHandler the reference to the {@link ServerHandler} instance.
     */
    private static void loading(ServerHandler serverHandler){
        System.setProperty("prism.allowhidpi", "false");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(JavaFXInit.class.getResource("/fxml/init.fxml"));
        loader.setController(new InitController(serverHandler));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            System.exit(1);
        }

        Scene scene = new Scene(root);

        stage.setOnCloseRequest(windowEvent -> {
            System.exit(0);
        });

        stage.setScene(scene);
        stage.setWidth(1280d);
        stage.setHeight(747d);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setFullScreenExitKeyCombination(null);
        stage.setTitle("Eriantys");

        List<Media> musics = new ArrayList<>();
        musics.add(new Media(String.valueOf(JavaFXInit.class.getClassLoader()
                .getResource("media/Lobby/Lobby.mp3"))));

        changeMusic(musics);

        muted = false;
        stage.show();
    }

    /**
     * Returns the stage
     * @return the stage
     */
    public static Stage getStage(){
        return stage;
    }

    /**
     *set the stopped variable to true or false
     * @param stopped
     */
    public static void setStopped(boolean stopped) {
        JavaFXInit.stopped = stopped;
    }

    /**
     * Returns the MediaPlayer (music)
     * @return the MediaPlayer (music)
     */
    public static MediaPlayer getPlayer(){
        return player;
    }

    /**
     * Returns true only if the music is muted
     * @return true only if the music is muted
     */
    public static boolean isMuted() {
        return muted;
    }

    /**
     *set the muted variable to true or false
     * @param muted
     */
    public static void setMuted(boolean muted) {
        JavaFXInit.muted = muted;
    }

    /**
     * add eventHandlers to the music image in the scene
     * @param music the pane representing the music in the scene
     */
    public static void musicEffect(Pane music){
        music.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setHeight(10.0);
            dropShadow.setWidth(10.0);
            dropShadow.setSpread(0.5);
            dropShadow.setBlurType(BlurType.GAUSSIAN);
            dropShadow.setColor(Color.LIGHTBLUE);
            music.setEffect(dropShadow);
        });
        music.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            music.setEffect(null);
        });
        music.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (JavaFXInit.isMuted()) {
                JavaFXInit.getPlayer().play();
                JavaFXInit.setMuted(false);
                music.getStyleClass().clear();
                music.getStyleClass().add("audio");
            } else {
                JavaFXInit.getPlayer().pause();
                JavaFXInit.setMuted(true);
                music.getStyleClass().clear();
                music.getStyleClass().add("noAudio");
            }
        });
    }

    /**
     * Changes the music playing
     * @param musics the list of music to cycle throw
     */
    public static void changeMusic(List<Media> musics) {
        muted = false;
        playlist = musics;
        current = 0;

        player = new MediaPlayer(playlist.get(current));
        player.play();
        player.setVolume(0.1);
        playerEffect();
    }

    /**
     * Make the player to cycle throw the list of music
     */
    private static void playerEffect(){
        player.setOnEndOfMedia(() -> {
            if (!stopped) {
                player.stop();
                if (current < playlist.size() - 1){
                    current++;
                } else {
                    current = 0;
                }
                player = new MediaPlayer(playlist.get(current));
                playerEffect();
                player.setVolume(0.1);
                player.seek(Duration.ZERO);
                player.play();
            }
        });
    }
}
