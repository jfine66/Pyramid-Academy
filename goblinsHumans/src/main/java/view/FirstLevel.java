package view;

import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class FirstLevel implements Level {
    private final HashMap<ArrayList<Integer>, Object> gridPos = new HashMap<>();

    private final AnchorPane levelOnePane;
    private final Scene levelOne;

    public FirstLevel(AnchorPane pane){
        this.levelOnePane = pane;
        levelOne = new Scene(levelOnePane, WIDTH, HEIGHT);
        Image backgroundImage = new Image("Cave_level.jpg", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelOnePane);
        createGrid(levelOnePane);
        createMapBounds();
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

    private void createMapBounds(){
        int x = 0;
        int y = 0;


        for(int i = 0; i < 705; i+= 64){
            gridPos.put(new ArrayList<>(Arrays.asList(x, i)), new Circle());
        }

        x = 128;

        for(int i = 128; i < 321; i+= 64){
            gridPos.put(new ArrayList<>(Arrays.asList(x, i)), new Circle());
        }

        gridPos.put(new ArrayList<>(Arrays.asList(128, 64)), new Circle());
        gridPos.put(new ArrayList<>(Arrays.asList(192, 384)), new Circle());
        gridPos.put(new ArrayList<>(Arrays.asList(256, 448)), new Circle());

        for(int i = 320; i < 897; i += 64){
            gridPos.put(new ArrayList<>(Arrays.asList(i, 64)), new Circle());
        }

        gridPos.put(new ArrayList<>(Arrays.asList(320, 512)), new Circle());
        gridPos.put(new ArrayList<>(Arrays.asList(320, 576)), new Circle());
        gridPos.put(new ArrayList<>(Arrays.asList(768, 128)), new Circle());
        gridPos.put(new ArrayList<>(Arrays.asList(832, 192)), new Circle());
        gridPos.put(new ArrayList<>(Arrays.asList(832, 256)), new Circle());

        for(int i = 768; i < 961; i+=64){
            gridPos.put(new ArrayList<>(Arrays.asList(i, 320)), new Circle());
        }
        for(int i = 768; i < 961; i+=64){
            gridPos.put(new ArrayList<>(Arrays.asList(i, 384)), new Circle());
        }
        for(int i = 768; i < 961; i+=64){
            gridPos.put(new ArrayList<>(Arrays.asList(i, 448)), new Circle());
        }
    }
}
