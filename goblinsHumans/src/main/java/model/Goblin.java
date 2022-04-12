package model;

import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Goblin extends ImageView {
    private int health;
    private int strength = 8;
    private int ac = 12;
    private int magic = 5;
    private final ImageView token = new ImageView("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png");
    MediaPlayer mediaPlayer;
    private final ArrayList<ITEMS> drops = new ArrayList<>();

    public Goblin(){
        this.health = (int)Math.floor(Math.random() * 12) + 1;
        drops.add(ITEMS.getRandomItem());
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

    public int getMagic() {
        return magic;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String toHit(Human player){
        int toHit = (int) Math.floor(Math.random() * 20) + 1;
        if(toHit > player.getAc()){
            return damage(player);
        } else {
            return "Goblin attacked missed";
        }

    }

    public ITEMS didDrop(){
        boolean anyDrop;

        int dropChance = (int) Math.floor(Math.random() * 9);
        anyDrop = dropChance <= drops.size() && dropChance != drops.size();

        return anyDrop ? drops.get(dropChance) : null;
    }


    private String damage(Human player){
        goblinAttackSound();
        int attack = (int) Math.floor(Math.random() * strength);
        player.setHealth(player.getHealth() - attack);
        return "Goblin hit you for " + attack + " damage.";
    }

    private void goblinAttackSound(){
        String url = "src/main/resources/238316__sonidotv__attack-3.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
}
