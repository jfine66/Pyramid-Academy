package model;



import javafx.scene.image.ImageView;

public class Human extends ImageView {
    private int health;
    private int strength;
    private final ImageView token = new ImageView("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png");

    public Human(){

    }

    public ImageView getToken(){
        token.setFitWidth(64);
        token.setFitHeight(64);
        token.setLayoutX(448);
        token.setLayoutY(384);
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


}
