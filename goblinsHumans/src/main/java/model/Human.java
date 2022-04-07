package model;

import gameLogic.Level;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Human extends ImageView{
    private int health;
    private int strength;
    private final ImageView token = new ImageView("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png");
    Rectangle playerMenu = new Rectangle(0,0, 128, 192);
    StackPane menuPane = new StackPane();

    ArrayList<Rectangle> recList = new ArrayList<>();
    List<Rectangle> moveGrid = new ArrayList<>();


    public Human(){

    }

    public void attack(){
        
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

    //TURN MENU
    public void openMenu(AnchorPane pane){
        menuPane.getChildren().clear();
        setMenuPos(this);
        menuPane.getChildren().add(playerMenu);
        addButtons(pane);
       pane.getChildren().add(menuPane);
    }

    private void closeMenu(AnchorPane pane){
        menuPane.getChildren().clear();
        pane.getChildren().remove(menuPane);
    }

    private void setMenuPos(Human token){
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

    private void addButtons(AnchorPane pane){
        ActionButton attack = new ActionButton("ATTACK");
        ActionButton move = new ActionButton("MOVE");
        ActionButton item = new ActionButton("ITEMS");
        ActionButton back = new ActionButton("BACK");

        back.setOnMouseClicked(mouseEvent -> {
            clearAttackGrid(pane);
            clearMovementGrid(pane);
            openMenu(pane);
            pane.getChildren().remove(back);
        });

        attack.setOnMouseClicked(mouseEvent -> {
            attackGrid(pane, back);
            closeMenu(pane);
        });

        move.setOnMouseClicked(mouseEvent -> {
            closeMenu(pane);
            getPossibleMoves(pane);
            back.setLayoutX(getTokenX() - 64);
            back.setLayoutY(getTokenY() + 128);
            pane.getChildren().add(back);
        });

        attack.setTranslateY(-64);
        item.setTranslateY(64);

        menuPane.getChildren().add(attack);
        menuPane.getChildren().add(move);
        menuPane.getChildren().add(item);
    }

    //ATTACK MENU
    public void attackGrid(AnchorPane pane, ActionButton back){
        createAttackGrid();
        int x = getTokenX();
        int y = getTokenY();
        setAttackGrid(x,y, pane, back);
    }

    private void createAttackGrid(){
        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.2);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);
            recList.add(r);
        }
    }

    private void setAttackGrid(int x, int y, AnchorPane pane, ActionButton back){
        int upAndLeft = -64;
        int rightAndDown = 64;
        back.setLayoutX(x - 128);
        back.setLayoutY(y + rightAndDown);
        pane.getChildren().add(back);

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

    private void clearAttackGrid(AnchorPane pane){
        for (Rectangle rectangle : recList) {
            pane.getChildren().remove(rectangle);
        }
    }

    private void clearMovementGrid(AnchorPane pane){
        for (Rectangle rectangle : moveGrid) {
            pane.getChildren().remove(rectangle);
        }
    }

    //PLAYER MOVE GRID
    public void getPossibleMoves(AnchorPane pane){
        int x = getTokenX();
        int y = getTokenY();
        int startX = x - 64;
        int startY = y - 64;
        int maxRight = x + 128;
        int maxDown = y + 128;

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
            r.setOnMouseClicked(mouseEvent -> setTokenPos((int) r.getX(), (int) r.getY()));

            pane.getChildren().add(r);
        }
    }

}
