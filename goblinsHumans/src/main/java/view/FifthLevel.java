package view;

import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class FifthLevel implements Level {
    protected final AnchorPane levelPane;
    protected final Scene level;
    private MediaPlayer mediaPlayer;

    public FifthLevel(AnchorPane pane){
        this.levelPane = pane;
        level = new Scene(levelPane, SceneController.WIDTH, SceneController.HEIGHT);
        Image backgroundImage = new Image("level_five_sized.png", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelPane);
        createGrid(levelPane);
        BackgroundMusic();
    }

    public Scene getScene() {
        return level;
    }

    public AnchorPane getLevelFivePane() {
        return levelPane;
    }

    protected void BackgroundMusic(){
        String url = "src/main/resources/workout-10823.mp3";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
