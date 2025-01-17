package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GameEntity;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class GameLevel {
    private final HashMap<ArrayList<Integer>, GameEntity> gridPos = new HashMap<>();
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

    void createGrid(AnchorPane pane){
        for(int i = 0; i < SceneController.WIDTH; i += 64){
            for(int j = 0; j < SceneController.HEIGHT; j += 64){
                Rectangle r = new Rectangle(i, j, 64, 64);
                r.setFill(Color.TRANSPARENT);
                r.setStroke(Color.BLACK);
                pane.getChildren().add(r);
            }
        }
    }

    void createBattleMap(Image map, AnchorPane pane){
        BackgroundImage background = new BackgroundImage(map, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        pane.setBackground(new Background(background));
    }

    public Scene getScene() {
        return level;
    }

    public AnchorPane getLevelPane() {
        return levelPane;
    }

    public HashMap<ArrayList<Integer>, GameEntity> getGridPos() {
        return gridPos;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
