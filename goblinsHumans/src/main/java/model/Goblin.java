package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.nio.file.Paths;

public class Goblin extends Circle {
    private int health = 10;
    private int strength = 5;
    private int ac = 3;
    private final ImageView token = new ImageView("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png");
    Circle testCircle = new Circle(250,250,120);
    MediaPlayer mediaPlayer;

    public Goblin(){
        makeSprite();
    }

    private void makeSprite(){
        testCircle.setStroke(Color.SEAGREEN);
        Image im = new Image("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png");
        testCircle.setFill(new ImagePattern(im));
    }

    public ImageView getToken() {
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

    public int getAC() {
        return ac;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void toHit(Human player){
        int toHit = (int) Math.floor(Math.random() * 10);
        if(toHit > player.getAc()){
            damage(player);
        } else {
            System.out.println("Goblin attacked missed");
        }

    }

    private void damage(Human player){
        goblinAttackSound();
        int attack = (int) Math.floor(Math.random() * strength);
        System.out.println("Goblin hit you for " + attack + " damage.");
        player.setHealth(player.getHealth() - attack);
    }

    private void goblinAttackSound(){
        String url = "src/main/resources/238316__sonidotv__attack-3.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
}
