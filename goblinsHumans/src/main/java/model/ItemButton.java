package model;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.SceneController;
import java.util.HashMap;
import static model.ITEMS.*;

public class ItemButton extends Button {
    private static final Human player = SceneController.getPlayer();
    private final HashMap<ITEMS, Integer> inventory = player.getInventory();
    private final StackPane inventoryDisplay;

    public ItemButton(ITEMS item, StackPane stackPane){
        this.inventoryDisplay = stackPane;
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
        switch (item){
            case HEALTH_POTION:
                System.out.println(player.getHealth());
                player.setHealth(player.getHealth() + (int) Math.floor(Math.random() * 9) + 1);
                System.out.println(player.getHealth());
                inventory.put(HEALTH_POTION, inventory.get(HEALTH_POTION) - 1);
                if(inventory.get(HEALTH_POTION) < 1) {
                    inventory.remove(HEALTH_POTION);
                    inventoryDisplay.getChildren().remove(this);
                }
                break;
            case MAGIC_POTION:
                player.setMagic(player.getMagic() + (int) Math.floor(Math.random() * 9) + 1);
                inventory.put(MAGIC_POTION, inventory.get(MAGIC_POTION) - 1);
                if(inventory.get(MAGIC_POTION) < 1) {
                    inventory.remove(MAGIC_POTION);
                    inventoryDisplay.getChildren().remove(this);
                };
        }
    }

}
