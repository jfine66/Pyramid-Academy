package gameLogic;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.ActionButton;
import model.Human;

import java.util.ArrayList;


public interface Level {
    int WIDTH = 1024;
    int HEIGHT = 768;

    Rectangle playerMenu = new Rectangle(0,0, 128, 192);
    StackPane menuPane = new StackPane();

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

    default StackPane openMenu(Human token){
        menuPane.getChildren().clear();
        setMenuPos(token);
        menuPane.getChildren().add(playerMenu);
        return menuPane;
    }

    default void closeMenu(){
        menuPane.getChildren().clear();
    }

    default void backMenu(Human token){
        ActionButton back = createBackButton();
        setMenuPos(token);
        back.setTranslateY(64);
        menuPane.getChildren().add(back);
        back.setOnMouseClicked(mouseEvent -> {
            openMenu(token);
        });
    }

    default void setMenuPos(Human token){
        int x = token.getTokenX();
        int y = token.getTokenY();

        playerMenu.setFill(Color.BLUE);
        playerMenu.setOpacity(0.3);

        if(x > 256){
            menuPane.setLayoutX(x - 128);
            menuPane.setLayoutY(y);
        } else if(x <= 256){
            menuPane.setLayoutX(x + 64);
            menuPane.setLayoutY(y);
        }
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


    default void addButtons(Human token, AnchorPane pane){
        ActionButton attack = createAttackButton();
        ActionButton move = createMoveButton();
        ActionButton item = createItemButton();

        attack.setOnMouseClicked(mouseEvent -> {
            attackGrid(token, pane);
            closeMenu();
            backMenu(token);
        });
//        move.setOnMouseClicked(mouseEvent -> moveGrid());
//        item.setOnMouseClicked(mouseEvent -> itemsScreen());

        attack.setTranslateY(-64);
        item.setTranslateY(64);

        menuPane.getChildren().add(attack);
        menuPane.getChildren().add(move);
        menuPane.getChildren().add(item);
    }


    //CREATE MENU BUTTONS
    private ActionButton createAttackButton(){
        return new ActionButton("ATTACK");
    }

    private ActionButton createMoveButton(){
        return new ActionButton("MOVE");
    }

    private ActionButton createItemButton(){
        return new ActionButton("ITEMS");
    }

    private ActionButton createBackButton(){
        return new ActionButton("BACK");
    }

}
