package view;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;

import java.util.HashMap;

import static model.ITEMS.*;

public class ShopScene {
    private final Human player = SceneController.getPlayer();
    private final AnchorPane shopPane;
    private final Scene shopScene;
    private final StackPane itemHolder = new StackPane();
    private String msg = "";
    private final DialogueBox itemMsg = new DialogueBox();
    private final ActionButton back = new ActionButton("BACK");
    private final MenuButtons buy = new MenuButtons("BUY");
    private final MenuButtons sell = new MenuButtons("SELL");
    private final ActionButton prevMenu = new ActionButton("PREV");
    private HashMap<ITEMS, Integer> shopInventory;
    private final StackPane goldHolder = new StackPane();
    private final Text goldAmount = new Text();
    private final ImageView goldPic = new ImageView("gold_bag.png");

    public ShopScene(){
        shopPane = new AnchorPane();
        shopScene = new Scene(shopPane, SceneController.WIDTH, SceneController.HEIGHT);
        createBackground();
        setBackBtn();
        setBuyBtn();
        setSellBtn();
        shopInventory = new HashMap<>();
        createStock();
        showPlayerGold();
    }

    public Scene getShopScene() {
        return shopScene;
    }

    private void showPlayerGold(){
        goldHolder.getChildren().clear();
        goldAmount.setFont(Font.loadFont("file:src/main/resources/THE_LAST_KINGDOM.ttf", 30));
        goldAmount.setFill(Color.WHITE);

        goldAmount.setText("x" + player.getGoldBag().get(GOLD));
        goldPic.setTranslateX(-64);

        goldHolder.getChildren().addAll(goldPic, goldAmount);
        goldHolder.setLayoutX(768);
        goldHolder.setLayoutY(64);
        shopPane.getChildren().add(goldHolder);
    }

    private void createBackground(){
        Image backgroundImage = new Image("Thrift-1.png", 1024,768,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        shopPane.setBackground(new Background(background));
    }

    private void setBackBtn(){
        back.setLayoutX(64);
        back.setLayoutY(546);
        shopPane.getChildren().add(back);
        back.setOnMouseClicked(mouseEvent -> SceneController.toCamp());
    }


    private void setBuyBtn(){
        buy.setLayoutX(64);
        buy.setLayoutY(128);
        buy.setOnMouseClicked(mouseEvent -> openBuyMenu());
       shopPane.getChildren().add(buy);
    }

    private void setSellBtn(){
        sell.setLayoutX(64);
        sell.setLayoutY(384);
        sell.setOnMouseClicked(mouseEvent -> openSellMenu());
        shopPane.getChildren().add(sell);
    }

    private void switchMenus(){
        shopPane.getChildren().remove(sell);
        shopPane.getChildren().remove(buy);
        shopPane.getChildren().remove(back);
        prevMenu.setLayoutX(768);
        prevMenu.setLayoutY(546);
        shopPane.getChildren().add(prevMenu);
        prevMenu.setOnMouseEntered(mouseEvent -> {
            prevMenu.setEffect(new DropShadow());
            prevMenu.setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 3;");
        });

        prevMenu.setOnMouseClicked(mouseEvent -> {
            shopPane.getChildren().add(sell);
            shopPane.getChildren().add(buy);
            shopPane.getChildren().add(back);
            shopPane.getChildren().remove(itemHolder);
            shopPane.getChildren().remove(prevMenu);
        });
    }

    private void openSellMenu(){
        itemHolder.getChildren().clear();
        switchMenus();

        int xPos = 448;
        int yPos = 128;
        int counter = 0;

       for(ITEMS item : player.getInventory().keySet()){
           ItemButton itemButton = new ItemButton(item, itemHolder,shopPane);
           itemButton.setTranslateX(xPos);
           itemButton.setTranslateY(yPos += 64);
           counter++;

           itemButton.setOnMouseEntered(mouseEvent -> sellDesc(item));
           itemButton.setOnMouseExited(mouseEvent -> shopPane.getChildren().remove(itemMsg.getPlayerDialogue(msg)));
           itemButton.setOnMouseClicked(mouseEvent -> sellItem(item, itemButton));

           if(counter > 5){
               yPos = 128;
               xPos += 128;
               counter = 0;
           }

           itemHolder.getChildren().add(itemButton);
       }

        itemHolder.setLayoutX(64);
        itemHolder.setLayoutY(64);
        shopPane.getChildren().add(itemHolder);
    }

    private void openBuyMenu(){
        itemHolder.getChildren().clear();
        switchMenus();

        int xPos = 448;
        int yPos = -64;

        for(ITEMS item : shopInventory.keySet()){
            ItemButton itemButton = new ItemButton(item, itemHolder, shopPane);
            itemButton.setTranslateX(xPos);
            itemButton.setTranslateY(yPos += 64);
            itemButton.setOnMouseEntered(mouseEvent -> buyDesc(item));
            itemButton.setOnMouseExited(mouseEvent -> shopPane.getChildren().remove(itemMsg.getPlayerDialogue(msg)));
            itemButton.setOnMouseClicked(mouseEvent -> buyItem(item, itemButton));

            itemHolder.getChildren().add(itemButton);
        }

        itemHolder.setLayoutX(64);
        itemHolder.setLayoutY(64);
        shopPane.getChildren().add(itemHolder);
    }

    public void createStock(){
        shopInventory = new HashMap<>();
        for(ITEMS item : ITEMS.values()){
        if(item == ITEMS.LEGENDARY_ARMOR){
                shopInventory.put(item, 1);
            } else if(item == GOLD) {
                continue;
            } else {
                shopInventory.put(item, (int) Math.floor(Math.random() * 5) + 1);
            }
        }
    }

    private void sellDesc(ITEMS item){
        itemMsg.getPlayerDialogue(msg).setLayoutX(0);
        itemMsg.getPlayerDialogue(msg).setLayoutY(256);

        switch (item){
            case HEALTH_POTION:
                itemMsg.clear();
                msg = "Health Potion x" + player.getInventory().get(HEALTH_POTION) +" Sells for 10G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case MAGIC_POTION:
                itemMsg.clear();
                msg = "Magic Potion x" + player.getInventory().get(MAGIC_POTION) + " Sells for 10G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case BROKEN_ARMOR:
                itemMsg.clear();
                msg = "Rusted Armor x" + player.getInventory().get(BROKEN_ARMOR) + " Sells for 5G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case LIGHT_ARMOR:
                itemMsg.clear();
                msg = "Light Armor x" + player.getInventory().get(LIGHT_ARMOR) + " Sells for 15G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case MEDIUM_ARMOR:
                itemMsg.clear();
                msg = "Medium Armor x" + player.getInventory().get(MEDIUM_ARMOR) + " Sells for 20G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case HEAVY_ARMOR:
                itemMsg.clear();
                msg = "Heavy Armor x" + player.getInventory().get(HEAVY_ARMOR) + " Sells for 25G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case LEGENDARY_ARMOR:
                itemMsg.clear();
                msg = "Legendary Armor x" + player.getInventory().get(LEGENDARY_ARMOR) + " Sells for 75G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case LIFE_STEAL:
                itemMsg.clear();
                msg = "Life Steal SpellBook x" + player.getInventory().get(LIFE_STEAL) + " Sells for 20G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case HEALTH_SPELL:
                itemMsg.clear();
                msg = "Spell of Life x" + player.getInventory().get(HEALTH_SPELL) + " Sells for 20G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case FIRE_SPELL:
                itemMsg.clear();
                msg = "Dragon's Breath x" + player.getInventory().get(FIRE_SPELL) + " Sells for 25G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case LIGHTING_SPELL:
                itemMsg.clear();
                msg = "Lighting Bolt x" + player.getInventory().get(LIGHTING_SPELL) + " Sells for 25G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            default:
        }
    }

    private void buyDesc(ITEMS item){
        itemMsg.getPlayerDialogue(msg).setLayoutX(0);
        itemMsg.getPlayerDialogue(msg).setLayoutY(256);

        switch (item){
            case HEALTH_POTION:
                itemMsg.clear();
                msg = "Health Potion x" + shopInventory.get(HEALTH_POTION) +" Buy for 20G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case MAGIC_POTION:
                itemMsg.clear();
                msg = "Magic Potion x" + shopInventory.get(MAGIC_POTION) + " Buy for 20G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case BROKEN_ARMOR:
                itemMsg.clear();
                msg = "Rusted Armor x" + shopInventory.get(BROKEN_ARMOR) + " Buy for 10G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case LIGHT_ARMOR:
                itemMsg.clear();
                msg = "Light Armor x" +shopInventory.get(LIGHT_ARMOR) + " Buy for 25G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case MEDIUM_ARMOR:
                itemMsg.clear();
                msg = "Medium Armor x" + shopInventory.get(MEDIUM_ARMOR) + " Buy for 30G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case HEAVY_ARMOR:
                itemMsg.clear();
                msg = "Heavy Armor x" + shopInventory.get(HEAVY_ARMOR) + " Buy for 40G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case LEGENDARY_ARMOR:
                itemMsg.clear();
                msg = "Legendary Armor x" + shopInventory.get(LEGENDARY_ARMOR) + " Buy for 100G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case LIFE_STEAL:
                itemMsg.clear();
                msg = "Life Steal SpellBook x" + shopInventory.get(LIFE_STEAL) + " Buy for 40G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case HEALTH_SPELL:
                itemMsg.clear();
                msg = "Spell of Life x" + shopInventory.get(HEALTH_SPELL) + " Buy for 40G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case FIRE_SPELL:
                itemMsg.clear();
                msg = "Dragon's Breath x" + shopInventory.get(FIRE_SPELL) + " Buy for 45G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            case LIGHTING_SPELL:
                itemMsg.clear();
                msg = "Lighting Bolt x" + shopInventory.get(LIGHTING_SPELL) + " Buy for 45G";
                shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
                break;
            default:
        }
    }

    private void sellItem(ITEMS item, ItemButton btn){
        switch (item){
            case HEALTH_POTION:
                player.getInventory().put(HEALTH_POTION, player.getInventory().get(HEALTH_POTION) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case MAGIC_POTION:
                player.getInventory().put(MAGIC_POTION, player.getInventory().get(MAGIC_POTION) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case BROKEN_ARMOR:
                player.getInventory().put(BROKEN_ARMOR, player.getInventory().get(BROKEN_ARMOR) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case LIGHT_ARMOR:
                player.getInventory().put(LIGHT_ARMOR, player.getInventory().get(LIGHT_ARMOR) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case MEDIUM_ARMOR:
                player.getInventory().put(MEDIUM_ARMOR, player.getInventory().get(MEDIUM_ARMOR) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case HEAVY_ARMOR:
                player.getInventory().put(HEAVY_ARMOR, player.getInventory().get(HEAVY_ARMOR) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case LEGENDARY_ARMOR:
                player.getInventory().put(LEGENDARY_ARMOR, player.getInventory().get(LEGENDARY_ARMOR) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case LIFE_STEAL:
                player.getInventory().put(LIFE_STEAL, player.getInventory().get(LIFE_STEAL) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case HEALTH_SPELL:
                player.getInventory().put(HEALTH_SPELL, player.getInventory().get(HEALTH_SPELL) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case FIRE_SPELL:
                player.getInventory().put(FIRE_SPELL, player.getInventory().get(FIRE_SPELL) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            case LIGHTING_SPELL:
                player.getInventory().put(LIGHTING_SPELL, player.getInventory().get(LIGHTING_SPELL) - 1);
                removeEmptyItems(item, btn);
                itemSold(item);
                break;
            default:
        }
    }

    private void buyItem(ITEMS item, ItemButton btn){
        itemMsg.getPlayerDialogue(msg).setLayoutX(0);
        itemMsg.getPlayerDialogue(msg).setLayoutY(256);

        switch (item){
            case HEALTH_POTION:
                if(player.getGoldBag().get(GOLD) < 20){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(HEALTH_POTION, shopInventory.get(HEALTH_POTION) - 1);
                    msg = "Health Potion x" + shopInventory.get(HEALTH_POTION) + " Buy for 20G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case MAGIC_POTION:
                if(player.getGoldBag().get(GOLD) < 20){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(MAGIC_POTION, shopInventory.get(MAGIC_POTION) - 1);
                    msg = "Magic Potion x" + shopInventory.get(MAGIC_POTION) + " Buy for 20G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case BROKEN_ARMOR:
                if(player.getGoldBag().get(GOLD) < 10){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(BROKEN_ARMOR, shopInventory.get(BROKEN_ARMOR) - 1);
                    msg = "Rusted Armor x" + shopInventory.get(BROKEN_ARMOR) + " Buy for 10G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case LIGHT_ARMOR:
                if(player.getGoldBag().get(GOLD) < 25){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(LIGHT_ARMOR, shopInventory.get(LIGHT_ARMOR) - 1);
                    msg = "Light Armor x" + shopInventory.get(LIGHT_ARMOR) + " Buy for 25G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case MEDIUM_ARMOR:
                if(player.getGoldBag().get(GOLD) < 30){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(MEDIUM_ARMOR, shopInventory.get(MEDIUM_ARMOR) - 1);
                    msg = "Medium Armor x" + shopInventory.get(MEDIUM_ARMOR) + " Buy for 30G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case HEAVY_ARMOR:
                if(player.getGoldBag().get(GOLD) < 40){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(HEAVY_ARMOR, shopInventory.get(HEAVY_ARMOR) - 1);
                    msg = "Heavy Armor x" + shopInventory.get(HEAVY_ARMOR) + " Buy for 40G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case LEGENDARY_ARMOR:
                if(player.getGoldBag().get(GOLD) < 100){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(LEGENDARY_ARMOR, shopInventory.get(LEGENDARY_ARMOR) - 1);
                    msg = "Legendary Armor x" + shopInventory.get(LEGENDARY_ARMOR) + " Buy for 100G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case LIFE_STEAL:
                if(player.getGoldBag().get(GOLD) < 40){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(LIFE_STEAL, shopInventory.get(LIFE_STEAL) - 1);
                    msg = "Life Steal SpellBook x" + shopInventory.get(LIFE_STEAL) + " Buy for 40G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case HEALTH_SPELL:
                if(player.getGoldBag().get(GOLD) < 40){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(HEALTH_SPELL, shopInventory.get(HEALTH_SPELL) - 1);
                    msg = "Spell of Life x" + shopInventory.get(HEALTH_SPELL) + " Buy for 40G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case FIRE_SPELL:
                if(player.getGoldBag().get(GOLD) < 45){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(FIRE_SPELL, shopInventory.get(FIRE_SPELL) - 1);
                    msg = "Dragon's Breath x" + shopInventory.get(FIRE_SPELL) + " Buy for 45G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            case LIGHTING_SPELL:
                if(player.getGoldBag().get(GOLD) < 45){
                    lackGold();
                } else {
                    updatePlayerInventory(item);

                    shopInventory.put(LIGHTING_SPELL, shopInventory.get(LIGHTING_SPELL) - 1);
                    msg = "Lighting Bolt x" + shopInventory.get(LIGHTING_SPELL) + " Buy for 45G";
                    itemMsg.getPlayerDialogue(msg);

                    removeOutOfStock(item, btn);
                    boughtItem(item);
                }
                break;
            default:
        }
    }

    private void updatePlayerInventory(ITEMS item){
        if(player.getInventory().containsKey(item)){
            player.getInventory().put(item, player.getInventory().get(item) + 1);
        } else {
            player.getInventory().put(item, 1);
        }
    }

    private void removeEmptyItems(ITEMS item, ItemButton btn){
        if(player.getInventory().get(item) < 1) {
            player.getInventory().remove(item);
            itemHolder.getChildren().remove(btn);
        }
    }

    private void removeOutOfStock(ITEMS item, ItemButton btn){
        if(shopInventory.get(item) < 1) {
            shopInventory.remove(item);
            itemHolder.getChildren().remove(btn);
        }
    }

    private void lackGold(){
        shopPane.getChildren().remove(itemMsg.getPlayerDialogue(msg));
        itemMsg.clear();
        msg = "You don't have enough Gold";
        shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
    }

    private void itemSold(ITEMS item){
        switch (item){
            case HEALTH_POTION:
            case MAGIC_POTION:
            case LIGHT_ARMOR:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) + 10);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case BROKEN_ARMOR:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) + 5);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case MEDIUM_ARMOR:
            case LIFE_STEAL:
            case HEALTH_SPELL:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) + 20);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case HEAVY_ARMOR:
            case LIGHTING_SPELL:
            case FIRE_SPELL:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) + 25);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case LEGENDARY_ARMOR:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) + 75);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            default:
        }
    }

    private void boughtItem(ITEMS item){
        switch (item){
            case HEALTH_POTION:
            case MAGIC_POTION:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) - 20);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case BROKEN_ARMOR:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) - 10);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case LIGHT_ARMOR:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) - 25);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case MEDIUM_ARMOR:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) - 30);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case HEAVY_ARMOR:
            case LIFE_STEAL:
            case HEALTH_SPELL:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) - 40);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case LEGENDARY_ARMOR:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) - 100);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            case FIRE_SPELL:
            case LIGHTING_SPELL:
                player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) - 45);
                shopPane.getChildren().remove(goldHolder);
                showPlayerGold();
                break;
            default:
        }
    }
}
