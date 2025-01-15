package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Goblin extends GameEntity {
    private final ArrayList<ITEMS> drops = new ArrayList<>();

    public Goblin(){
        super("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png",
                "src/main/resources/238316__sonidotv__attack-3.wav");
        this.strength = 8;
        this.ac = 12;
        this.magic = 5;
        this.health = (int)Math.floor(Math.random() * 12) + 1;
        drops.add(ITEMS.getRandomItem());
    }

    public String toHit(Human player){
        int toHit = (int) Math.floor(Math.random() * 20) + 1;
        if(toHit > player.getAC()){
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
        attackSound();
        int attack = (int) Math.floor(Math.random() * strength);
        player.setHealth(player.getHealth() - attack);
        return "Goblin hit you for " + attack + " damage.";
    }

}
