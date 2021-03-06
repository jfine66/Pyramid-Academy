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
    private int maxMP;
    private int intel = 10;
    private int magic = 5;
    private int dex = 10;
    private int strength = 10;
    private int ac;
    private final ImageView token = new ImageView("test_player_token.png");
    private final HashMap<ITEMS, Integer> inventory;
    private final HashMap<ITEMS, Integer> goldBag;
    private final HashMap<String, ITEMS> equipment = new HashMap<>();
    MediaPlayer mediaPlayer;


    public Human(){
        inventory = new HashMap<>();
        goldBag = new HashMap<>();

        inventory.put(ITEMS.FIRE_SPELL, 1);
        inventory.put(ITEMS.HEALTH_SPELL, 1);
        inventory.put(ITEMS.HEALTH_POTION, 3);
        inventory.put(ITEMS.MAGIC_POTION, 3);

        goldBag.put(ITEMS.GOLD, 30);

        this.conMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.intMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.strengthMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.dexMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.maxHP = health + conMod;
        health = maxHP;
        this.maxMP = intel + intMod;
        magic = maxMP;
        this.ac = dex + dexMod;
        equipment.put("ARMOR", ITEMS.BROKEN_ARMOR);
    }

   public String toHit(Goblin goblin){
        int toHit = (int) Math.floor(Math.random() * (20 + dexMod)) + 1;
        int ac = goblin.getAC();
        if(toHit > ac){
            return damage(goblin);
        } else {
            return "Attack Missed";
        }
   }

   private String damage(Goblin goblin){
        humanAttackSound();
        int attack =  (int) Math.floor(Math.random() * (strength + strengthMod) + 1);
        goblin.setHealth(goblin.getHealth() - attack);
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

    public int getConMod() {
        return conMod;
    }

    public int getStrengthMod() {
        return strengthMod;
    }

    public int getDexMod() {
        return dexMod;
    }

    public int getIntMod() {
        return intMod;
    }

    public HashMap<ITEMS, Integer> getInventory(){
        return inventory;
    }

    public HashMap<ITEMS, Integer> getGoldBag() {
        return goldBag;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setTokenPos(int x, int y){
        token.setLayoutX(x);
        token.setLayoutY(y);
    }

    public String equipArmor(ITEMS item){
        if(equipment.get("ARMOR") == item){
            return "Already equipped";
        } else {
            removeArmor();
            inventory.put(equipment.get("ARMOR"), 1);
            equipment.put("ARMOR", item);
            checkArmor();
            return "Armor equipped";
        }
    }

    private void checkArmor(){
        switch (equipment.get("ARMOR")){
            case BROKEN_ARMOR:
                ac += 1;
                break;
            case LIGHT_ARMOR:
                ac += 2;
                maxMP -= 1;
                break;
            case MEDIUM_ARMOR:
                ac += 3;
                maxMP -= 2;
                break;
            case HEAVY_ARMOR:
                ac += 4;
                maxMP -= 3;
                break;
            case LEGENDARY_ARMOR:
                ac += 5;
                strength -= 2;
                maxMP -= 2;
                break;
            default:
        }
    }

    private void removeArmor(){
        switch (equipment.get("ARMOR")){
            case BROKEN_ARMOR:
                ac -= 1;
                break;
            case LIGHT_ARMOR:
                ac -= 2;
                maxMP += 1;
                break;
            case MEDIUM_ARMOR:
                ac -= 3;
                maxMP += 2;
                break;
            case HEAVY_ARMOR:
                ac -= 4;
                maxMP += 3;
                break;
            case LEGENDARY_ARMOR:
                ac -= 5;
                strength += 2;
                maxMP += 2;
                break;
            default:
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
        if(item == ITEMS.GOLD) {
            int numPieces = (int) Math.floor(Math.random() * 10) + 1;
            goldBag.put(ITEMS.GOLD, goldBag.get(ITEMS.GOLD) + numPieces);
        } else {
            addItemToInventory(item);
        }
    }

    private void addItemToInventory(ITEMS item){
        if(inventory.containsKey(item)){
            inventory.put(item, inventory.get(item) + 1);
        } else {
            inventory.put(item, 1);
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void playerStartPos(AnchorPane currentPane){
        currentPane.getChildren().remove(token);
        currentPane.getChildren().add(token);
        setTokenPos(448,320);
    }

    private void humanAttackSound(){
        String url = "src/main/resources/524215__magnuswaker__schwing-1.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
}
