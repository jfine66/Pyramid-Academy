package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class FirstLevel {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    private final AnchorPane levelOnePane;
    private final Scene levelOne;

    public FirstLevel(){
        levelOnePane = new AnchorPane();
        levelOne = new Scene(levelOnePane, WIDTH, HEIGHT);
        createBackground();
        createGrid();
    }

    public Scene getScene() {
        return levelOne;
    }

    private void createBackground(){
        Image backgroundImage = new Image("test - Unnamed Level.png", 1024,768,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        levelOnePane.setBackground(new Background(background));
    }

    private void createGrid(){
        for(int i = 0; i < WIDTH; i += 64){
            for(int j = 0; j < HEIGHT; j += 64){
                Rectangle r = new Rectangle(i, j, 64, 64);
                r.setFill(Color.TRANSPARENT);
                r.setStroke(Color.BLACK);
                levelOnePane.getChildren().add(r);
            }
        }

    }

    public void setPosition(ImageView object, int x, int y){
        object.setLayoutX(x);
        object.setLayoutY(y);
        object.setFitHeight(64);
        object.setFitWidth(64);
        levelOnePane.getChildren().add(object);
    }


}
