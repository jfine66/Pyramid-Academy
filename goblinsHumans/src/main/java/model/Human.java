package model;


import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class Human extends ImageView{
    private int health = 5;
    private int strength = 200;
    private  int ac = 100;
    private final ImageView token = new ImageView("test_player_token.png");
//    private ArrayList<String> inventory;
    private HashMap<String, Integer> inventory;
    MediaPlayer mediaPlayer;



    public Human(){
        inventory = new HashMap<>();
    }

   public String toHit(Goblin goblin){
        int toHit = (int) Math.floor(Math.random() * 10);
        int ac = goblin.getAC();
        if(toHit > ac){
            return damage(goblin);
        } else {
            return "Attack Missed";
        }
   }

   private String damage(Goblin goblin){
        humanAttackSound();
        int attack =  (int) Math.floor(Math.random() * strength);
        goblin.setHealth(goblin.getHealth() - attack);
       System.out.println("Hit Goblin for " + attack + " damage");
        return "Hit Goblin for " + attack + " damage";
   }

    public ImageView getToken(){
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

    public int getHealth() {
        return health;
    }

    public int getAc() {
        return ac;
    }

    public void setTokenPos(int x, int y){
        token.setLayoutX(x);
        token.setLayoutY(y);
    }

    public void addToInventory(String text){
        if(text.equals("no drop")) return;
        addGoldToInventory(text);
        addItemToInventory(text);
    }

    private void addGoldToInventory(String text){
        int numPieces = (int) Math.floor((Math.random()) * 10) + 1;

        if(text.equals("Gold") && inventory.containsKey("Gold")){
            inventory.put("Gold", inventory.get("Gold") + numPieces);
            System.out.println(numPieces + " pieces of Gold have been added to your inventory");
        } else if(text.equals("Gold")){
            inventory.put("Gold", numPieces);
            System.out.println(numPieces + " pieces of Gold have been added to your inventory");
        }
    }

    private void addItemToInventory(String text){
        if(text.equals("Gold")) return;
        if(inventory.containsKey(text)){
            inventory.put(text, inventory.get(text) + 1);
        } else {
            inventory.put(text, 1);
        }

        System.out.println(text + " has been added to your inventory.");
    }


    public HashMap<String, Integer> getInventory(){
        return inventory;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private void humanAttackSound(){
        String url = "src/main/resources/524215__magnuswaker__schwing-1.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
}
