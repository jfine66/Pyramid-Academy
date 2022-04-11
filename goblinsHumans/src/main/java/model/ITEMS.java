package model;

import javafx.scene.image.ImageView;

import java.util.Random;

public enum ITEMS {
    HEALTH_POTION("health_pot_small.png"),
    MAGIC_POTION("magic_pot_small.png");


    private String url;

    ITEMS(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static ITEMS getRandomItem(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
