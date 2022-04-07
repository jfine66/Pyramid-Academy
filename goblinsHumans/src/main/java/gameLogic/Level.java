package gameLogic;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Human;

import java.util.ArrayList;


public interface Level {
    int WIDTH = 1024;
    int HEIGHT = 768;
    ArrayList<Rectangle> recList = new ArrayList<>();

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

    default void attackGrid(Human token, AnchorPane pane){
        createAttackGrid();
        int x = token.getTokenX();
        int y = token.getTokenY();
        setAttackGrid(x, y, pane);
    }

    default void createAttackGrid(){
        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.2);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);
            recList.add(r);
        }
    }

    default void setAttackGrid(int x, int y, AnchorPane pane){
        int upAndLeft = -64;
        int rightAndDown = 64;

        for(int i = 0; i < 4; i++){
            if(i == 0){
                recList.get(i).setLayoutX(x);
                recList.get(i).setLayoutY(y + upAndLeft);
                pane.getChildren().add(recList.get(i));
            } else if(i == 1){
                recList.get(i).setLayoutX(x + rightAndDown);
                recList.get(i).setLayoutY(y);
                pane.getChildren().add(recList.get(i));
            } else if(i == 2){
                recList.get(i).setLayoutX(x);
                recList.get(i).setLayoutY(rightAndDown + y);
                pane.getChildren().add(recList.get(i));
            } else if(i == 3){
                recList.get(i).setLayoutX(upAndLeft + x);
                recList.get(i).setLayoutY(y);
                pane.getChildren().add(recList.get(i));
            }
        }
    }

}
