package model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Human extends ImageView {
    private int health;
    private int strength;

    public Human(){
        makeSprite();
    }

    private void makeSprite(){
        Image im = new Image("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png");
    }


}
