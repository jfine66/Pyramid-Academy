package model;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
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

        setOnMouseClicked(mouseEvent -> useItem(item));

        setOnMouseExited(mouseEvent -> setEffect(null));
    }

    private void useItem(ITEMS item){
        Timer timer = new Timer();

        playerMsg.getPlayerDialogue(msg).setLayoutX(320);
        playerMsg.getPlayerDialogue(msg).setLayoutY(256);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> currentPane.getChildren().remove(playerMsg.getPlayerDialogue(msg)));
            }
        }, 1000);


        switch (item){
            case HEALTH_POTION:
                useHealthPot();
                break;
            case MAGIC_POTION:
                useMagicPot();
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

}
