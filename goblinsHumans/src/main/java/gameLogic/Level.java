package gameLogic;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public interface Level {
    int WIDTH = 1024;
    int HEIGHT = 768;


    default void createGrid(AnchorPane pane){
        for(int i = 0; i < 1024; i += 64){
            for(int j = 0; j < 768; j += 64){
                Rectangle r = new Rectangle(i, j, 64, 64);
                r.setFill(Color.TRANSPARENT);
                r.setStroke(Color.BLACK);
                pane.getChildren().add(r);
            }
        }
    }

    default void createBattleMap(Image map, AnchorPane pane){
        BackgroundImage background = new BackgroundImage(map, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        pane.setBackground(new Background(background));
    }
}
