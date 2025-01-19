package model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.HashMap;

public class Human extends GameEntity{
    private int conMod;
    private int intMod;
    private int strengthMod;
    private int dexMod;

    private int maxHP;
    private int maxMP;
    private int intel = 10;
    private int dex = 10;

    private final HashMap<ITEMS, Integer> inventory;
    private final HashMap<ITEMS, Integer> goldBag;
    private final HashMap<String, ITEMS> equipment = new HashMap<>();

    private boolean hasMoved;
    private boolean hasAttacked;

    public Human(){
        super("test_player_token.png",
                "src/main/resources/524215__magnuswaker__schwing-1.wav");
        this.strength = 10;
        this.magic = 5;
        this.health = 10;

        this.dexMod = (int) (Math.floor(Math.random() * 6) + 1);
//        this.ac = dex + dexMod;
        this.ac = 10;

        inventory = new HashMap<>();
        goldBag = new HashMap<>();

        fillStarterInv();
        setStarterStats();

        equipment.put("ARMOR", ITEMS.BROKEN_ARMOR);
    }

    private void fillStarterInv() {
        inventory.put(ITEMS.FIRE_SPELL, 1);
        inventory.put(ITEMS.LIFE_STEAL, 1);
        inventory.put(ITEMS.LIGHTING_SPELL, 1);

        inventory.put(ITEMS.HEALTH_SPELL, 1);
        inventory.put(ITEMS.HEALTH_POTION, 3);
        inventory.put(ITEMS.MAGIC_POTION, 3);

        goldBag.put(ITEMS.GOLD, 30);
    }

    private void setStarterStats() {
        this.conMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.intMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.strengthMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.dexMod = (int) (Math.floor(Math.random() * 6) + 1);
        this.maxHP = health + conMod;
        health = maxHP;
        this.maxMP = intel + intMod;
        magic = maxMP;
    }

    public String equipArmor(ITEMS item){
        if(equipment.get("ARMOR") == item){
            return "Already equipped";
        } else {
            toggleArmor(false);
            inventory.put(equipment.get("ARMOR"), 1);
            equipment.put("ARMOR", item);
            toggleArmor(true);
            return "Armor equipped";
        }
    }

    private void toggleArmor(boolean isEquipping) {
        switch (equipment.get("ARMOR")) {
            case BROKEN_ARMOR:
                ac += (isEquipping ? 1 : -1);
                break;
            case LIGHT_ARMOR:
                ac += (isEquipping ? 2 : -2);
                maxMP -= (isEquipping ? 1 : -1);
                break;
            case MEDIUM_ARMOR:
                ac += (isEquipping ? 3 : -3);
                maxMP -= (isEquipping ? 2 : -2);
                break;
            case HEAVY_ARMOR:
                ac += (isEquipping ? 4 : -4);
                maxMP -= (isEquipping ? 3 : -3);
                break;
            case LEGENDARY_ARMOR:
                ac += (isEquipping ? 5 : -5);
                strength -= (isEquipping ? 2 : -2);
                maxMP -= (isEquipping ? 2 : -2);
                break;
            default:
        }
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

    public void playerStartPos(AnchorPane currentPane){
        currentPane.getChildren().remove(token);
        currentPane.getChildren().add(token);
        setTokenPos(448,320);
    }

    // SOUNDS
    public void playPlayerDeathSound() {
        String url = "src/main/resources/553724__maxim-nick__sad-defeat-emotion-maxim-nick.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }

    public void playClearLevelSound() {
        String url = "src/main/resources/462250__silverillusionist__victory-sound-1.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMaxMP() {
        return maxMP;
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

    public boolean hasMoved() {
        return hasMoved;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public void setMagic(int magic) {
        this.magic = magic;
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

}
