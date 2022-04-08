package model;


import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Human extends ImageView{
    private int health = 5;
    private int strength = 10;
    private  int ac = 0;
    private final ImageView token = new ImageView("test_player_token.png");
    MediaPlayer mediaPlayer;



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
        humanAttackSound();
        int attack = 30; //(int) Math.floor(Math.random() * strength);
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

    public int getHealth() {
        return health;
    }

    public int getAc() {
        return ac;
    }

    public void setTokenPos(int x, int y){
        token.setLayoutX(x);
        token.setLayoutY(y);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private void humanAttackSound(){
        String url = "src/main/resources/524215__magnuswaker__schwing-1.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
}
