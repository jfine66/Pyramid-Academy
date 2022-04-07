package model;


import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Human extends ImageView{
    private int health = 30;
    private int strength = 10;
    private final ImageView token = new ImageView("test_player_token.png");



    public Human(){

    }

   public void toHit(Goblin goblin){
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

    public int getHealth() {
        return health;
    }
}
