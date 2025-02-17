package model;


import java.util.ArrayList;

public class Goblin extends GameEntity {
    private final ArrayList<ITEMS> drops = new ArrayList<>();

    public Goblin(){
        super("pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png",
                "src/main/resources/238316__sonidotv__attack-3.wav");
        this.strength = 8;
        this.ac = 8;
        this.magic = 5;
        this.health = (int)Math.floor(Math.random() * 12) + 1;
        drops.add(ITEMS.getRandomItem());
    }

    public ITEMS didDrop(){
        boolean anyDrop;

        int dropChance = (int) Math.floor(Math.random() * 9);
        anyDrop = dropChance <= drops.size() && dropChance != drops.size();

        return anyDrop ? drops.get(dropChance) : null;
    }

}
