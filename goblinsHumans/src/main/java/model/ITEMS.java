package model;

import java.util.Random;

public enum ITEMS {
    HEALTH_POTION("health_potion_small.png"),
    MAGIC_POTION("magic_potion__small.png"),
    BROKEN_ARMOR("broken_armor_.png");


    private String url;

    ITEMS(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static ITEMS getRandomItem(){
        Random random = new Random();
        return BROKEN_ARMOR;
        //return values()[random.nextInt(values().length)];
    }

    @Override
    public String toString() {
        return url.substring(0, 13).replace("_", " ");
    }
}
