package view;

import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class LevelSkeleton implements Level {
    protected final AnchorPane levelPane;
    protected final Scene level;

    public LevelSkeleton(AnchorPane pane){
        this.levelPane = pane;
        level = new Scene(levelPane, SceneController.WIDTH, SceneController.HEIGHT);
        Image backgroundImage = new Image("Cave_level.jpg", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelPane);
        createGrid(levelPane);
    }

    public Scene getScene() {
        return level;
    }

    public AnchorPane getLevelPane() {
        return levelPane;
    }

}
