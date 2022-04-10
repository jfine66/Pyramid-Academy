package model;


import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;


public class Human extends ImageView{
    private int health = 5;
    private int strength = 200;
    private  int ac = 100;
    private final ImageView token = new ImageView("test_player_token.png");
    MediaPlayer mediaPlayer;



    public Human(){

    }

   public String toHit(Goblin goblin){
        int toHit = (int) Math.floor(Math.random() * 10);
        int ac = goblin.getAC();
        if(toHit > ac){
            return damage(goblin);
        } else {
            return "Attack Missed";
        }
   }

   private String damage(Goblin goblin){
        humanAttackSound();
        int attack =  (int) Math.floor(Math.random() * strength);
        goblin.setHealth(goblin.getHealth() - attack);
       System.out.println("Hit Goblin for " + attack + " damage");
        return "Hit Goblin for " + attack + " damage";
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
