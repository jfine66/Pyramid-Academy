package view;

import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class GameLevel implements Level {
    private final HashMap<ArrayList<Integer>, Object> gridPos = new HashMap<>();
    private final AnchorPane levelPane;
    private final Scene level;
    private MediaPlayer mediaPlayer;
    private String bgMusic;

    public GameLevel(AnchorPane pane, String levelPath, String bgMusic){
        this.levelPane = pane;
        level = new Scene(levelPane, SceneController.WIDTH, SceneController.HEIGHT);
        Image backgroundImage = new Image(levelPath, 1024, 768, false, true);
        createBattleMap(backgroundImage, levelPane);
        createGrid(levelPane);
        BackgroundMusic(bgMusic);
    }

    protected void BackgroundMusic(String bgMusic){
        Media h = new Media(Paths.get(bgMusic).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public Scene getScene() {
        return level;
    }

    public AnchorPane getLevelPane() {
        return levelPane;
    }

    public HashMap<ArrayList<Integer>, Object> getGridPos() {
        return gridPos;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
