package model;

import javafx.scene.image.ImageView;

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
}
