package view;

import gameLogic.GameLogic;
import gameLogic.Level;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Human;

import java.util.ArrayList;
import java.util.List;


public class FirstLevel implements Level {
    private static final Human player = SceneController.getPlayer();
    private final GameLogic game = new GameLogic();


    private final AnchorPane levelOnePane;
    private final Scene levelOne;

    public FirstLevel(){
        levelOnePane = new AnchorPane();
        levelOne = new Scene(levelOnePane, WIDTH, HEIGHT);
        Image backgroundImage = new Image("test - Unnamed Level.png", 1024, 768, false, true);
        createBattleMap(backgroundImage, levelOnePane);
        createGrid(levelOnePane);
        player.setTokenPos(512,256);
        player.addButtons();
        levelOnePane.getChildren().add(player.getToken());
        levelOnePane.getChildren().add(openMenu(player));
    }

    public Scene getScene() {
        return levelOne;
    }


    private void getPossibleMoves(){
        int x = player.getTokenX();
        int y = player.getTokenY();
        int startX = x - 64;
        int startY = y - 64;
        int maxRight = x + 128;
        int maxDown = y + 128;
        List<Rectangle> moveGrid = new ArrayList<>();

        for(int i = startX; i < maxRight; i += 64){
            for(int j = startY; j < maxDown; j += 64){
                Rectangle r = new Rectangle(i, j, 64,64);
                r.setOpacity(0.2);
                r.setFill(Color.BLUE);
                r.setStroke(Color.RED);
                moveGrid.add(r);
            }
        }

        for(Rectangle r : moveGrid){
           r.setOnMouseEntered(mouseEvent -> r.setStroke(Color.WHITE));
           r.setOnMouseExited(mouseEvent -> r.setStroke(Color.RED));
           r.setOnMouseClicked(mouseEvent -> player.setTokenPos((int) r.getX(), (int) r.getY()));

           levelOnePane.getChildren().add(r);
        }

    }
}
