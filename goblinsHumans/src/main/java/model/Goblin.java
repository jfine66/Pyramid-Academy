package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Goblin extends Circle {
    private int health;
    private int strength;
    private int ac = 5;
    private final ImageView token = new ImageView("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png");
    Circle testCircle = new Circle(250,250,120);

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
}
