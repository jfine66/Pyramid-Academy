package model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Human extends ImageView {
    private int health;
    private int strength;
    private final ImageView token = new ImageView("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png");
    private final Rectangle playerMenu = new Rectangle(0,0, 128, 192);
    private final StackPane menuPane = new StackPane();


    public Human(){

    }

    public ImageView getToken(){
        token.setFitWidth(64);
        token.setFitHeight(64);
        return token;
    }

    public int getTokenX(){
        return (int) token.getLayoutX();
    }

    public int getTokenY(){
        return (int) token.getLayoutY();
    }

    public void setTokenPos(int x, int y){
        token.setLayoutX(x);
        token.setLayoutY(y);
    }

    public StackPane openMenu(){
        menuPane.getChildren().clear();
        setMenuPos();
        menuPane.getChildren().add(playerMenu);
        addButtons();
        return menuPane;
    }

    private void setMenuPos(){
        int x = (int) token.getLayoutX();
        int y = (int) token.getLayoutY();

        playerMenu.setFill(Color.BLUE);
        playerMenu.setOpacity(0.4);


        if(x > 256){
            menuPane.setLayoutX(x - 128);
            menuPane.setLayoutY(y);
        } else if(x <= 256){
            menuPane.setLayoutX(x + 64);
            menuPane.setLayoutY(y);
        }
    }

    private void addButtons(){
        ActionButton attack = createAttackButton();
        ActionButton move = createMoveButton();
        ActionButton item = createItemButton();


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

}
