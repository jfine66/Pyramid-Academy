package view;

import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class SecondLevel implements Level {
    private final AnchorPane levelPane;
    private final Scene level;
    private MediaPlayer mediaPlayer;

    public SecondLevel(AnchorPane pane) {
        this.levelPane = pane;
        level = new Scene(levelPane, SceneController.WIDTH, SceneController.HEIGHT);
        Image backgroundImage = new Image("level_two_sized.png", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelPane);
        createGrid(levelPane);
        BackgroundMusic();
    }

    public Scene getScene() {
        return level;
    }

    public AnchorPane getLevelTwoPane() {
        return levelPane;
    }

    protected void BackgroundMusic(){
        String url = "src/main/resources/modern-rnb-all-your-base-15484.mp3";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
