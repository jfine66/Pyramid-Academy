package view;

import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;


public class FirstLevel implements Level {
    private final HashMap<ArrayList<Integer>, Object> gridPos = new HashMap<>();
    private final AnchorPane levelOnePane;
    private final Scene levelOne;

    public FirstLevel(AnchorPane pane){
        this.levelOnePane = pane;
        levelOne = new Scene(levelOnePane, SceneController.WIDTH, SceneController.HEIGHT);
        Image backgroundImage = new Image("Cave_level.jpg", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelOnePane);
        createGrid(levelOnePane);
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

}
