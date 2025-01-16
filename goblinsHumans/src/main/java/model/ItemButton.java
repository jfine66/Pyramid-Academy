package model;

import gameLogic.GameLogic;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import view.SceneController;

import java.util.*;

import static model.ITEMS.*;

// This class needs another look
public class ItemButton extends Button {
    private static final Human player = SceneController.getPlayer();
    private static final GameLogic game = SceneController.getGame();
    private final HashMap<ITEMS, Integer> inventory = player.getInventory();
    private final StackPane inventoryDisplay;
    private final AnchorPane currentPane;
    private final DialogueBox playerMsg = new DialogueBox();
    private final DialogueBox itemDesc = new DialogueBox();
    private String msg = "";

    public ItemButton(ITEMS item, StackPane stackPane, AnchorPane levelPane){
        this.inventoryDisplay = stackPane;
        this.currentPane = levelPane;

        setPrefWidth(64);
        setPrefHeight(64);
        String style = "-fx-background-color: transparent; -fx-background-image: url('"+item.getUrl()+"');";
        setStyle(style);
        initializeButtonListeners(item);
    }

    private void initializeButtonListeners(ITEMS item){
        setOnMouseEntered(mouseEvent -> setEffect(new DropShadow()));

        setOnMouseClicked(mouseEvent -> {
            MouseButton button = mouseEvent.getButton();
            if(button == MouseButton.PRIMARY){
                useItem(item);
            } else if(button == MouseButton.SECONDARY){
                itemDescription(item);
            }
        });

        setOnMouseExited(mouseEvent -> setEffect(null));
    }

    private void useItem(ITEMS item){
        playerMsg.getDialogue(msg, Color.BLUE).setLayoutX(320);
        playerMsg.getDialogue(msg, Color.BLUE).setLayoutY(256);

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> currentPane.getChildren().remove(playerMsg.getDialogue(msg, Color.BLUE)));
            }
        }, 1000);

        switch (item){
            case HEALTH_POTION:
                playerMsg.clear();
                useHealthPot();
                break;
            case MAGIC_POTION:
                playerMsg.clear();
                useMagicPot();
                break;
            case BROKEN_ARMOR:
                playerMsg.clear();
                equipArmor(BROKEN_ARMOR);
                break;
            case LIGHT_ARMOR:
                playerMsg.clear();
                equipArmor(LIGHT_ARMOR);
                break;
            case MEDIUM_ARMOR:
                playerMsg.clear();
                equipArmor(MEDIUM_ARMOR);
                break;
            case HEAVY_ARMOR:
                playerMsg.clear();
                equipArmor(HEAVY_ARMOR);
                break;
            case LEGENDARY_ARMOR:
                playerMsg.clear();
                equipArmor(LEGENDARY_ARMOR);
                break;
            case HEALTH_SPELL:
                playerMsg.clear();
                useHealthSpell();
                break;
            case LIFE_STEAL:
                if(player.getMagic() < 10){
                    lackMagic();
                } else {
                    useLifeSteal();
                }
                break;
            case LIGHTING_SPELL:
                if(player.getMagic() < 10){
                   lackMagic();
                } else {
                    game.closeMenu();
                    game.directionMenu();
                }
                break;
            case FIRE_SPELL:
                if(player.getMagic() < 10){
                    lackMagic();
                } else {
                    game.closeMenu();
                    game.fireDirection();
                }
                break;
            default:
        }
    }

    private void itemDescription(ITEMS item){
        itemDesc.getDialogue(msg, Color.BLUE).setLayoutX(320);
        itemDesc.getDialogue(msg, Color.BLUE).setLayoutY(256);

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> currentPane.getChildren().remove(itemDesc.getDialogue(msg, Color.BLUE)));
            }
        }, 3000);

        switch (item){
            case HEALTH_POTION:
                createItemDescription("A vibrant red liquid radiating warmth\nrestores 1-10 points of health\nYou have x" + inventory.get(HEALTH_POTION) +" of this item");
                break;
            case MAGIC_POTION:
                createItemDescription("A memorizing dark blue liquid\nrestores 1-10 points of magic power\nYou have x" + inventory.get(MAGIC_POTION) +" of this item");
                break;
            case BROKEN_ARMOR:
                createItemDescription("Rusted and in disrepair\nit offers little in the way of protection\nplus one to your AC\nLeft click to equip\nYou have x" + inventory.get(BROKEN_ARMOR) + " of this item");
                break;
            case LIGHT_ARMOR:
                createItemDescription("Well kept chain-mail\nThe interwoven links provide protection\nplus two to your AC\nLeft click to equip\nYou have x" + inventory.get(LIGHT_ARMOR) + " of this item");
                break;
            case MEDIUM_ARMOR:
                createItemDescription("Well kept breastplate\nplus three to your AC\nLeft click to equip\nYou have x" + inventory.get(MEDIUM_ARMOR) + " of this item");
                break;
            case HEAVY_ARMOR:
                createItemDescription("Heavy and thick splint mail\nplus four to your AC\nLeft click to equip\nYou have x" + inventory.get(HEAVY_ARMOR) + " of this item");
                break;
            case LEGENDARY_ARMOR:
                createItemDescription("Offers full protection\ndespite being as light as a feather\nplus five to your AC\nLeft click to equip\nYou have x" + inventory.get(LEGENDARY_ARMOR) + " of this item");
                break;
            case HEALTH_SPELL:
                createItemDescription("A vibrant red leather bound book\nPluses with life energy\nLeft click to cast.\nRestores 10HP, Cost 5MP");
                break;
            case LIFE_STEAL:
                createItemDescription("A dark purple bound book\nIt feels has like it hungers for life itself.\nSteal the life of your enemies\nRestores 5HP, Cost 5MP");
                break;
            case LIGHTING_SPELL:
                createItemDescription("The tips of your fingers tingle\nThe magic can barely be contained\nShoot out a lighting bolt\nCost 10MP");
                break;
            case FIRE_SPELL:
                createItemDescription("Hot to the touch\nThe magic is waiting to burst forward\nCast a cone of fire in front you\nCost 10MP");
                break;
            default:
        }
    }

    private void createItemDescription(String message) {
        itemDesc.clear();
        msg = message;
        currentPane.getChildren().add(itemDesc.getDialogue(msg, Color.BLUE));
    }

    private void useHealthPot(){
        int health = (int) Math.floor(Math.random() * 9) + 1;

        if(player.getHealth() == player.getMaxHP()){
            msg = "You already have full health";
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
        } else if(player.getHealth() + health > player.getMaxHP()){
            health = player.getMaxHP() - player.getHealth();
            msg = "You have regained " + health + " health";
            player.setHealth(player.getMaxHP());
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
            inventory.put(HEALTH_POTION, inventory.get(HEALTH_POTION) - 1);
            removeEmpty(HEALTH_POTION,this);
        } else{
            msg = "You have regained " + health + " health";
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
            player.setHealth(player.getHealth() + health);
            inventory.put(HEALTH_POTION, inventory.get(HEALTH_POTION) - 1);
            removeEmpty(HEALTH_POTION, this);
        }
    }

    private void useMagicPot(){
        int magic = (int) Math.floor(Math.random() * 9) + 1;

        if(player.getMagic() == player.getMaxMP()){
            msg = "You already have full magic";
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
        } else if(player.getMagic() + magic > player.getMaxMP()){
            magic = player.getMaxMP() - player.getMagic();
            msg = "You have regained " + magic + " magic power";
            player.setMagic(player.getMaxMP());
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
            inventory.put(MAGIC_POTION, inventory.get(MAGIC_POTION) - 1);
            removeEmpty(MAGIC_POTION, this);
        } else{
            msg = "You have regained " + magic + " magic power";
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
            player.setMagic(player.getMagic() + magic);
            inventory.put(MAGIC_POTION, inventory.get(MAGIC_POTION) - 1);
            removeEmpty(MAGIC_POTION, this);
        }
    }

    private void equipArmor(ITEMS item){
        if(player.equipArmor(item).equals("Armor equipped")){
            inventory.put(item, inventory.get(item) - 1);
            msg = "You put on the armor";
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
        } else if(player.equipArmor(item).equals("Already equipped")){
            msg = "Already equipped";
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
        }

        removeEmpty(item, this);
    }

    private void useHealthSpell(){
        int health = 10;

        if(player.getMagic() < 5){
            lackMagic();
        } else if(player.getHealth() == player.getMaxHP()){
            msg = "You already have full health";
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
        } else {
            player.setMagic(player.getMagic() - 5);

            if(player.getHealth() + health > player.getMaxHP()){
                health = player.getMaxHP() - player.getHealth();
                msg = "You have regained " + health + "HP";
                player.setHealth(player.getMaxHP());
            } else {
                player.setHealth(player.getHealth() + 10);
                msg = "You have regained 10HP";
            }
            currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
            inventory.put(HEALTH_SPELL, inventory.get(HEALTH_SPELL) - 1);
            removeEmpty(HEALTH_SPELL, this);
        }
    }

    private void useLifeSteal(){
        currentPane.getChildren().remove(game.getBack());
        game.closeMenu();
        game.createAttackOptions();
        game.setLifeStealListeners();
        player.setMagic(player.getMagic() - 5);
        inventory.put(LIFE_STEAL, inventory.get(LIFE_STEAL) - 1);
        removeEmpty(LIFE_STEAL, this);
    }

    private void lackMagic(){
        msg = "You don't have enough magic";
        currentPane.getChildren().add(playerMsg.getDialogue(msg, Color.BLUE));
    }

    private void removeEmpty(ITEMS item, ItemButton btn){
        if(inventory.get(item) < 1) {
            inventory.remove(item);
            inventoryDisplay.getChildren().remove(btn);
        }
    }
}
