package model;

import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class GameEntity extends ImageView {

    protected int health;
    protected int ac;
    protected int strength;
    protected int magic;

    protected String attackSoundUrl;

    protected ImageView token;
    protected MediaPlayer mediaPlayer;

    public GameEntity(String tokenUrl, String attackSoundUrl) {
        this.token = new ImageView(tokenUrl);
        this.attackSoundUrl = attackSoundUrl;
    }

    protected void attackSound(){
        String url = attackSoundUrl;
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }

    protected String damage(GameEntity gameEntity){
        attackSound();
        int attack = (int) Math.floor(Math.random() * strength);
        gameEntity.setHealth(gameEntity.getHealth() - attack);
        String entityName = getEntityName();
        return entityName.equals("Human") ? "Hit Goblin for " + attack + " damage" :
            "Goblin you for " + attack + " damage";
    }

    public String toHit(GameEntity gameEntity){
        int toHit = (int) Math.floor(Math.random() * 20) + 1;
        if(toHit > gameEntity.getAC()){
            return damage(gameEntity);
        } else {
            return "Attacked missed";
        }
    }

    public int getAC() {
        return ac;
    }

    public int getHealth() {
        return health;
    }

    public int getMagic() {
        return magic;
    }

    public int getTokenX(){
        return (int) token.getLayoutX();
    }

    public int getTokenY(){
        return (int) token.getLayoutY();
    }

    public ImageView getToken() {
        token.setFitWidth(64);
        token.setFitHeight(64);
        return token;
    }

    private String getEntityName() {
       return getClass().getSimpleName();
    }

    public void setTokenPos(int x, int y){
        token.setLayoutX(x);
        token.setLayoutY(y);
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
