package model;


import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.HashMap;


public class Human extends ImageView{
    private int conMod;
    private int intMod;
    private int strengthMod;
    private int dexMod;

    private int health = 10;
    private final int maxHP;
    private final int maxMP;
    private int intel = 10;
    private int magic = 5;
    private int dex = 10;
    private int strength = 10;
    private int ac;
    private final ImageView token = new ImageView("test_player_token.png");
    private final HashMap<ITEMS, Integer> inventory;
    private final HashMap<String, ITEMS> equipment = new HashMap<>();
    MediaPlayer mediaPlayer;


    public Human(){
        inventory = new HashMap<>();
        inventory.put(ITEMS.HEALTH_POTION, 10);
        inventory.put(ITEMS.MAGIC_POTION, 10);
        inventory.put(ITEMS.BROKEN_ARMOR, 2);

        this.conMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.intMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.strengthMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.dexMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.maxHP = health + conMod;
        health = maxHP;
        this.maxMP = intel + intMod;
        magic = maxMP;
        this.ac = dex + dexMod;
        equipment.put("ARMOR", ITEMS.HEALTH_POTION);
        System.out.println("Max HP : " + maxHP + " Max MP: " + maxMP + " current health " + health + " current AC " + ac);
        System.out.println("con : " + conMod + " dex: " + dexMod + " str : " + strengthMod + " int : " + intMod);
    }

   public String toHit(Goblin goblin){
        int toHit = (int) Math.floor(Math.random() * (20 + dexMod));
        int ac = goblin.getAC();
        if(toHit > ac){
            return damage(goblin);
        } else {
            return "Attack Missed";
        }
   }

   private String damage(Goblin goblin){
        humanAttackSound();
        int attack =  (int) Math.floor(Math.random() * (strength + strengthMod));
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

    public int getMaxHP() {
        return maxHP;
    }

    public int getMaxMP() {
        return maxMP;
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

    public String equipArmor(ITEMS item){
        if(equipment.get("ARMOR") != ITEMS.HEALTH_POTION){
            return "Already equipped";
        } else {
            equipment.put("ARMOR", item);
            checkArmor();
            return "Armor equipped";
        }
    }

    private void checkArmor(){
        if(equipment.get("ARMOR") == ITEMS.BROKEN_ARMOR){
            ac += 1;
        }
    }

    public void setConMod(int conMod) {
        this.conMod = conMod;
    }

    public void setStrengthMod(int strengthMod) {
        this.strengthMod = strengthMod;
    }

    public void setDexMod(int dexMod) {
        this.dexMod = dexMod;
    }

    public void setIntMod(int intMod) {
        this.intMod = intMod;
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

    public void playerStartPos(AnchorPane currentPane){
        currentPane.getChildren().add(token);
        setTokenPos(512,448);
    }

    private void humanAttackSound(){
        String url = "src/main/resources/524215__magnuswaker__schwing-1.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
}
