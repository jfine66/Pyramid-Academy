package view;

import gameLogic.GameLogic;
import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.Human;




public class FirstLevel extends GameLogic implements Level {
    private static final Human player = SceneController.getPlayer();


    private final AnchorPane levelOnePane;
    private final Scene levelOne;

    public FirstLevel(){
        levelOnePane = new AnchorPane();
        levelOne = new Scene(levelOnePane, WIDTH, HEIGHT);
        Image backgroundImage = new Image("test - Unnamed Level.png", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelOnePane);
        createGrid(levelOnePane);
        player.setTokenPos(512,256);
        player.openMenu(levelOnePane);
        levelOnePane.getChildren().add(player.getToken());
    }

    public Scene getScene() {
        return levelOne;
    }



}
