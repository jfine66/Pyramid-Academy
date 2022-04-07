package view;

import gameLogic.GameLogic;
import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.Goblin;
import model.Human;




public class FirstLevel extends GameLogic implements Level {
    private static final Human player = SceneController.getPlayer();
    private final Goblin testGoblin = new Goblin();


    private final AnchorPane levelOnePane;
    private final Scene levelOne;

    public FirstLevel(){
        levelOnePane = new AnchorPane();
        levelOne = new Scene(levelOnePane, WIDTH, HEIGHT);
        Image backgroundImage = new Image("test - Unnamed Level.png", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelOnePane);
        createGrid(levelOnePane);
        player.setTokenPos(512,256);
       testGoblin.setTokenPos(512, 320);
        levelOnePane.getChildren().add(testGoblin.getToken());
        levelOnePane.getChildren().add(player.getToken());
        player.openMenu(levelOnePane, testGoblin);
    }

    public Scene getScene() {
        return levelOne;
    }





}
