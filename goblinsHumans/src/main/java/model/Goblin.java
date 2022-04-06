package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Goblin extends Circle {
    private int health;
    private int strength;
    Circle testCircle = new Circle(250,250,120);

    public Goblin(){
        makeSprite();
    }

    private void makeSprite(){
        testCircle.setStroke(Color.SEAGREEN);
        Image im = new Image("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png");
        testCircle.setFill(new ImagePattern(im));
    }
}
