package model;


import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.HashMap;


public class Human extends ImageView{
    private int health = 5;
    private int magic = 5;
    private int strength = 200;
    private  int ac = 100;
    private final ImageView token = new ImageView("test_player_token.png");
    private HashMap<ITEMS, Integer> inventory;
    MediaPlayer mediaPlayer;



    public Human(){
        inventory = new HashMap<>();
        inventory.put(ITEMS.HEALTH_POTION, 2);
       inventory.put(ITEMS.MAGIC_POTION, 1);

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

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setTokenPos(int x, int y){
        token.setLayoutX(x);
        token.setLayoutY(y);
    }

    public void addToInventory(ITEMS item){
        //if(text.equals("no drop")) return;
        //addGoldToInventory(text);
        addItemToInventory(item);
    }

//    private void addGoldToInventory(String text){
//        int numPieces = (int) Math.floor((Math.random()) * 10) + 1;
//
//        if(text.equals("Gold") && inventory.containsKey("Gold")){
//            inventory.put("Gold", inventory.get("Gold") + numPieces);
//            System.out.println(numPieces + " pieces of Gold have been added to your inventory");
//        } else if(text.equals("Gold")){
//            inventory.put("Gold", numPieces);
//            System.out.println(numPieces + " pieces of Gold have been added to your inventory");
//        }
//    }

    private void addItemToInventory(ITEMS item){
        //if(text.equals("Gold")) return;
        if(inventory.containsKey(item)){
            inventory.put(item, inventory.get(item) + 1);
        } else {
            inventory.put(item, 1);
        }

        System.out.println(item + " has been added to your inventory.");
    }


    public HashMap<ITEMS, Integer> getInventory(){
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
