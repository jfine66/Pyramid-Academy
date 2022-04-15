package view;

import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class FourthLevel implements Level {
    private final AnchorPane levelPane;
    private final Scene level;
    private MediaPlayer mediaPlayer;

    public FourthLevel(AnchorPane pane){
        this.levelPane = pane;
        level = new Scene(levelPane, SceneController.WIDTH, SceneController.HEIGHT);
        Image backgroundImage = new Image("level_four_sized.jpg", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelPane);
        createGrid(levelPane);
        BackgroundMusic();
    }

    public Scene getScene() {
        return level;
    }

    public AnchorPane getLevelFourPane() {
        return levelPane;
    }

    protected void BackgroundMusic(){
        String url = "src/main/resources/let-the-games-begin-21858.mp3";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
