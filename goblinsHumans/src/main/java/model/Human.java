package model;


import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Human extends ImageView{
    private int health;
    private int strength = 10;
    private final ImageView token = new ImageView("test_player_token.png");
    Rectangle playerMenu = new Rectangle(0,0, 128, 192);
    StackPane menuPane = new StackPane();

    ArrayList<Rectangle> recList = new ArrayList<>();
    ArrayList<Rectangle> moveGrid = new ArrayList<>();


    public Human(){

    }

   private void toHit(Goblin goblin){
        int toHit = (int) Math.floor(Math.random() * 10);
        int ac = goblin.getAC();
        if(toHit > ac){
            damage(goblin);
        } else {
            System.out.println("Attacked missed");
        }

   }

   private void damage(Goblin goblin){
        int attack = (int) Math.floor(Math.random() * strength);
       System.out.println("You hit goblin for " + attack + " damage.");
        goblin.setHealth(goblin.getHealth() - attack);
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
    public void openMenu(AnchorPane pane, Goblin goblin){
        menuPane.getChildren().clear();
        setMenuPos(this);
        menuPane.getChildren().add(playerMenu);
        addButtons(pane, goblin);
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

    private void addButtons(AnchorPane pane, Goblin goblin){
        ActionButton attack = new ActionButton("ATTACK");
        ActionButton move = new ActionButton("MOVE");
        ActionButton item = new ActionButton("ITEMS");
        ActionButton back = new ActionButton("BACK");

        back.setOnMouseClicked(mouseEvent -> {
            clearMovementGrid(pane);
            clearAttackGrid(pane);
            openMenu(pane, goblin);
            pane.getChildren().remove(back);
        });

        attack.setOnMouseClicked(mouseEvent -> {
            attackGrid(pane, back, goblin);
            closeMenu(pane);
        });

        move.setOnMouseClicked(mouseEvent -> {
            closeMenu(pane);
            getPossibleMoves(pane, goblin);
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
    public void attackGrid(AnchorPane pane, ActionButton back, Goblin goblin){
        createAttackGrid();
        int x = getTokenX();
        int y = getTokenY();
        setAttackGrid(x,y, pane, back, goblin);
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

    private void setAttackGrid(int x, int y, AnchorPane pane, ActionButton back, Goblin goblin){
        int upAndLeft = -64;
        int rightAndDown = 64;
        int goblinX = goblin.getTokenX();
        int goblinY = goblin.getTokenY();

        back.setLayoutX(x - 128);
        back.setLayoutY(y + rightAndDown);
        pane.getChildren().add(back);

        for(int i = 0; i < 4; i++){
            switch (i){
                case 0:
                    recList.get(i).setLayoutX(x);
                    recList.get(i).setLayoutY(y + upAndLeft);
                    if(goblinX == recList.get(i).getLayoutX() && goblinY == recList.get(i).getLayoutY()) setAttackListeners(recList.get(i), goblin);
                    pane.getChildren().add(recList.get(i));
                    break;
                case 1:
                    recList.get(i).setLayoutX(x + rightAndDown);
                    recList.get(i).setLayoutY(y);
                    if(goblinX == recList.get(i).getLayoutX() && goblinY == recList.get(i).getLayoutY()) setAttackListeners(recList.get(i), goblin);
                    pane.getChildren().add(recList.get(i));
                    break;
                case 2:
                    recList.get(i).setLayoutX(x);
                    recList.get(i).setLayoutY(rightAndDown + y);
                    if(goblinX == recList.get(i).getLayoutX() && goblinY == recList.get(i).getLayoutY()) setAttackListeners(recList.get(i), goblin);
                    pane.getChildren().add(recList.get(i));
                    break;
                case 3:
                    recList.get(i).setLayoutX(upAndLeft + x);
                    recList.get(i).setLayoutY(y);
                    if(goblinX == recList.get(i).getLayoutX() && goblinY == recList.get(i).getLayoutY()) setAttackListeners(recList.get(i), goblin);
                    pane.getChildren().add(recList.get(i));
                    break;
                default:
            }
        }
    }

    private void setAttackListeners(Rectangle r, Goblin goblin){
       r.setOnMouseClicked(mouseEvent -> System.out.println("hello"));
       r.setOnMouseClicked(mouseEvent -> toHit(goblin));
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
        moveGrid = new ArrayList<>();
    }

    //PLAYER MOVE GRID
    public void getPossibleMoves(AnchorPane pane, Goblin goblin){
        int x = getTokenX();
        int y = getTokenY();
        int goblinX = goblin.getTokenX();
        int goblinY = goblin.getTokenY();

        int startX = x - 64;
        int startY = y - 64;
        int maxRight = x + 128;
        int maxDown = y + 128;

        for(int i = startX; i < maxRight; i += 64){
            for(int j = startY; j < maxDown; j += 64){
                if(goblinX == i && goblinY == j){
                    continue;
                }
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

            r.setOnMouseClicked(mouseEvent -> {
                setTokenPos((int) r.getX(), (int) r.getY());
            });

            pane.getChildren().add(r);
        }
    }

}
