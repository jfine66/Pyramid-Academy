package view;

import gameLogic.GameLogic;
import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;




public class FirstLevel extends GameLogic implements Level {

    private final AnchorPane levelOnePane;
    private final Scene levelOne;

    public FirstLevel(){
        levelOnePane = new AnchorPane();
        levelOne = new Scene(levelOnePane, WIDTH, HEIGHT);
        Image backgroundImage = new Image("test - Unnamed Level.png", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelOnePane);
        createGrid(levelOnePane);
        gameStart(levelOnePane);
    }

    public Scene getScene() {
        return levelOne;
    }





}
