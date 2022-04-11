package model;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import view.SceneController;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static model.ITEMS.*;

public class ItemButton extends Button {
    private static final Human player = SceneController.getPlayer();
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
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> currentPane.getChildren().remove(playerMsg.getPlayerDialogue(msg)));
            }
        }, 1000);

        switch (item){
            case HEALTH_POTION:
                playerMsg.clear();
                useHealthPot();
                playerMsg.getPlayerDialogue(msg).setLayoutX(320);
                playerMsg.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case MAGIC_POTION:
                playerMsg.clear();
                useMagicPot();
                playerMsg.getPlayerDialogue(msg).setLayoutX(320);
                playerMsg.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case BROKEN_ARMOR:
                playerMsg.clear();
                equipArmor(BROKEN_ARMOR);
                playerMsg.getPlayerDialogue(msg).setLayoutX(320);
                playerMsg.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case LIGHT_ARMOR:
                playerMsg.clear();
                equipArmor(LIGHT_ARMOR);
                playerMsg.getPlayerDialogue(msg).setLayoutX(320);
                playerMsg.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case MEDIUM_ARMOR:
                playerMsg.clear();
                equipArmor(MEDIUM_ARMOR);
                playerMsg.getPlayerDialogue(msg).setLayoutX(320);
                playerMsg.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case HEAVY_ARMOR:
                playerMsg.clear();
                equipArmor(HEAVY_ARMOR);
                playerMsg.getPlayerDialogue(msg).setLayoutX(320);
                playerMsg.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case LEGENDARY_ARMOR:
                playerMsg.clear();
                equipArmor(LEGENDARY_ARMOR);
                playerMsg.getPlayerDialogue(msg).setLayoutX(320);
                playerMsg.getPlayerDialogue(msg).setLayoutY(256);
                break;
            default:
        }
    }

    private void itemDescription(ITEMS item){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> currentPane.getChildren().remove(itemDesc.getPlayerDialogue(msg)));
            }
        }, 3000);

        switch (item){
            case HEALTH_POTION:
                itemDesc.clear();
                msg = "A vibrant red liquid radiating warmth\nrestores 1-10 points of health";
                currentPane.getChildren().add(itemDesc.getPlayerDialogue(msg));
                itemDesc.getPlayerDialogue(msg).setLayoutX(320);
                itemDesc.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case MAGIC_POTION:
                itemDesc.clear();
                msg = "A memorizing dark blue liquid\nrestores 1-10 points of magic power";
                currentPane.getChildren().add(itemDesc.getPlayerDialogue(msg));
                itemDesc.getPlayerDialogue(msg).setLayoutX(320);
                itemDesc.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case BROKEN_ARMOR:
                itemDesc.clear();
                msg = "Rusted and in disrepair\nit offers little in the way of protection\nplus one to your AC\nLeft click to equip";
                currentPane.getChildren().add(itemDesc.getPlayerDialogue(msg));
                itemDesc.getPlayerDialogue(msg).setLayoutX(320);
                itemDesc.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case LIGHT_ARMOR:
                itemDesc.clear();
                msg = "Well kept chain-mail\nThe interwoven links provide protection\nplus two to your AC\nLeft click to equip";
                currentPane.getChildren().add(itemDesc.getPlayerDialogue(msg));
                itemDesc.getPlayerDialogue(msg).setLayoutX(320);
                itemDesc.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case MEDIUM_ARMOR:
                itemDesc.clear();
                msg = "Well kept breastplate\nplus three to your AC\nLeft click to equip";
                currentPane.getChildren().add(itemDesc.getPlayerDialogue(msg));
                itemDesc.getPlayerDialogue(msg).setLayoutX(320);
                itemDesc.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case HEAVY_ARMOR:
                itemDesc.clear();
                msg = "Heavy and thick splint mail\nplus four to your AC\nLeft click to equip";
                currentPane.getChildren().add(itemDesc.getPlayerDialogue(msg));
                itemDesc.getPlayerDialogue(msg).setLayoutX(320);
                itemDesc.getPlayerDialogue(msg).setLayoutY(256);
                break;
            case LEGENDARY_ARMOR:
                itemDesc.clear();
                msg = "Offers full protection\ndespite being as light as a feather\nplus five to your AC\nLeft click to equip";
                currentPane.getChildren().add(itemDesc.getPlayerDialogue(msg));
                itemDesc.getPlayerDialogue(msg).setLayoutX(320);
                itemDesc.getPlayerDialogue(msg).setLayoutY(256);
                break;
            default:
        }
    }

    private void useHealthPot(){
        int health = (int) Math.floor(Math.random() * 9) + 1;

        if(player.getHealth() == player.getMaxHP()){
            msg = "You already have full health";
            currentPane.getChildren().add(playerMsg.getPlayerDialogue(msg));
        } else if(player.getHealth() + health > player.getMaxHP()){
            health = player.getMaxHP() - player.getHealth();
            msg = "You have regained " + health + " health";
            player.setHealth(player.getMaxHP());
            currentPane.getChildren().add(playerMsg.getPlayerDialogue(msg));
            inventory.put(HEALTH_POTION, inventory.get(HEALTH_POTION) - 1);
            if(inventory.get(HEALTH_POTION) < 1) {
                inventory.remove(HEALTH_POTION);
                inventoryDisplay.getChildren().remove(this);
            }
        } else{
            msg = "You have regained " + health + " health";
            currentPane.getChildren().add(playerMsg.getPlayerDialogue(msg));
            player.setHealth(player.getHealth() + health);
            inventory.put(HEALTH_POTION, inventory.get(HEALTH_POTION) - 1);
            if(inventory.get(HEALTH_POTION) < 1) {
                inventory.remove(HEALTH_POTION);
                inventoryDisplay.getChildren().remove(this);
            }
        }
    }

    private void useMagicPot(){
        int magic = (int) Math.floor(Math.random() * 9) + 1;

        if(player.getMagic() == player.getMaxMP()){
            msg = "You already have full magic";
            currentPane.getChildren().add(playerMsg.getPlayerDialogue(msg));
        } else if(player.getMagic() + magic > player.getMaxMP()){
            magic = player.getMaxMP() - player.getMagic();
            msg = "You have regained " + magic + " magic power";
            player.setMagic(player.getMaxMP());
            currentPane.getChildren().add(playerMsg.getPlayerDialogue(msg));
            inventory.put(MAGIC_POTION, inventory.get(MAGIC_POTION) - 1);
            if(inventory.get(MAGIC_POTION) < 1) {
                inventory.remove(MAGIC_POTION);
                inventoryDisplay.getChildren().remove(this);
            }
        } else{
            msg = "You have regained " + magic + " magic power";
            currentPane.getChildren().add(playerMsg.getPlayerDialogue(msg));
            player.setMagic(player.getMagic() + magic);
            inventory.put(MAGIC_POTION, inventory.get(MAGIC_POTION) - 1);
            if(inventory.get(MAGIC_POTION) < 1) {
                inventory.remove(MAGIC_POTION);
                inventoryDisplay.getChildren().remove(this);
            }
        }
    }

    private void equipArmor(ITEMS item){
        if(player.equipArmor(item).equals("Armor equipped")){
            inventory.put(item, inventory.get(item) - 1);
            msg = "You put on the armor";
            currentPane.getChildren().add(playerMsg.getPlayerDialogue(msg));
        } else if(player.equipArmor(item).equals("Already equipped")){
            msg = "Already equipped";
            currentPane.getChildren().add(playerMsg.getPlayerDialogue(msg));
        }

        if(inventory.get(item) < 1) {
            inventory.remove(item);
            inventoryDisplay.getChildren().remove(this);
        }
    }

}
