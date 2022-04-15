package view;

import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class FirstLevel implements Level {
    private final HashMap<ArrayList<Integer>, Object> gridPos = new HashMap<>();
    private final AnchorPane levelOnePane;
    private final Scene levelOne;
    private MediaPlayer mediaPlayer;

    public FirstLevel(AnchorPane pane){
        this.levelOnePane = pane;
        levelOne = new Scene(levelOnePane, SceneController.WIDTH, SceneController.HEIGHT);
        Image backgroundImage = new Image("Cave_level.jpg", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelOnePane);
        createGrid(levelOnePane);
        BackgroundMusic();
    }

    public HashMap<ArrayList<Integer>, Object> getGridPos() {
        return gridPos;
    }

    public Scene getScene() {
        return levelOne;
    }

    public AnchorPane getLevelOnePane() {
        return levelOnePane;
    }

    protected void BackgroundMusic(){
        String url = "src/main/resources/my-first-walk-13852.mp3";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
