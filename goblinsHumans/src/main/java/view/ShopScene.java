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

           itemButton.setOnMouseEntered(mouseEvent -> wareDesc(item, false));
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
            itemButton.setOnMouseEntered(mouseEvent -> wareDesc(item, true));
            itemButton.setOnMouseExited(mouseEvent -> shopPane.getChildren().remove(itemMsg.getPlayerDialogue(msg)));
            itemButton.setOnMouseClicked(mouseEvent -> buyItem(item, itemButton));

            itemHolder.getChildren().add(itemButton);
        }

        itemHolder.setLayoutX(64);
        itemHolder.setLayoutY(64);
        shopPane.getChildren().add(itemHolder);
    }

//    The continue IS needed to avoid putting Gold as a buy-able item in the shop
    public void createStock(){
        shopInventory = new HashMap<>();
        for(ITEMS item : ITEMS.values()){
            if (item == GOLD) {
                continue;
            } else if (item == ITEMS.LEGENDARY_ARMOR) {
                shopInventory.put(item, 1);
            } else {
                shopInventory.put(item, (int) Math.floor(Math.random() * 5) + 1);
            }
        }
    }

    public void createBuyAndSellDescription(String message) {
        itemMsg.clear();
        msg = message;
        shopPane.getChildren().add(itemMsg.getPlayerDialogue(msg));
    }

    private void wareDesc(ITEMS item, boolean isBuying) {
        itemMsg.getPlayerDialogue(msg).setLayoutX(0);
        itemMsg.getPlayerDialogue(msg).setLayoutY(256);
        int numOfItem = isBuying ? shopInventory.get(item) : player.getInventory().get(item);
        String buyOrSell = isBuying ? " Buy for " : " Sell for ";

        switch (item){
            case HEALTH_POTION:
                createBuyAndSellDescription("Health Potion x" + numOfItem + buyOrSell + (isBuying ? "20G" : "10G"));
                break;
            case MAGIC_POTION:
                createBuyAndSellDescription("Magic Potion x" + numOfItem + buyOrSell + (isBuying ? "20G" : "10G"));
                break;
            case BROKEN_ARMOR:
                createBuyAndSellDescription("Rusted Armor x" + numOfItem + buyOrSell + (isBuying ? "10G" : "5G"));
                break;
            case LIGHT_ARMOR:
                createBuyAndSellDescription("Light Armor x" + numOfItem + buyOrSell + (isBuying ? "25G" : "15G"));
                break;
            case MEDIUM_ARMOR:
                createBuyAndSellDescription("Medium Armor x" + numOfItem + buyOrSell + (isBuying ? "30G" : "20G"));
                break;
            case HEAVY_ARMOR:
                createBuyAndSellDescription("Heavy Armor x" + numOfItem + buyOrSell + (isBuying ? "40G" : "25G"));
                break;
            case LEGENDARY_ARMOR:
                createBuyAndSellDescription("Legendary Armor x" + numOfItem + buyOrSell + (isBuying ? "100G" : "75G"));
                break;
            case LIFE_STEAL:
                createBuyAndSellDescription("Life Steal SpellBook x" + numOfItem + buyOrSell + (isBuying ? "40G" : "20G"));
                break;
            case HEALTH_SPELL:
                createBuyAndSellDescription("Spell of Life x" + numOfItem + buyOrSell + (isBuying ? "40G" : "20G"));
                break;
            case FIRE_SPELL:
                createBuyAndSellDescription("Dragon's Breath x" + numOfItem + buyOrSell + (isBuying ? "45G" : "25G"));
                break;
            case LIGHTING_SPELL:
                createBuyAndSellDescription("Lighting Bolt x" + numOfItem + buyOrSell + (isBuying ? "45G" : "25G"));
                break;
            default:
        }
    }

    private void sellItem(ITEMS item, ItemButton btn){
        player.getInventory().put(item, player.getInventory().get(item) - 1);
        removeItemFromPlayer(item, btn);
    }

    private void removeItemFromPlayer(ITEMS item, ItemButton btn) {
        removeEmptyItems(item, btn);
        buySellItem(item, false);
    }

    private void buyItem(ITEMS item, ItemButton btn){
        itemMsg.getPlayerDialogue(msg).setLayoutX(0);
        itemMsg.getPlayerDialogue(msg).setLayoutY(256);

        switch (item){
            case HEALTH_POTION:
            case MAGIC_POTION:
                if(player.getGoldBag().get(GOLD) < 20){
                    lackGold();
                } else {
                    updateShopInv(item, btn, " Buy for 20G");
                }
                break;
            case BROKEN_ARMOR:
                if(player.getGoldBag().get(GOLD) < 10){
                    lackGold();
                } else {
                    updateShopInv(item, btn, " Buy for 10G");
                }
                break;
            case LIGHT_ARMOR:
                if(player.getGoldBag().get(GOLD) < 25){
                    lackGold();
                } else {
                    updateShopInv(item, btn, " Buy for 25G");
                }
                break;
            case MEDIUM_ARMOR:
                if(player.getGoldBag().get(GOLD) < 30){
                    lackGold();
                } else {
                    updateShopInv(item, btn, " Buy for 30G");
                }
                break;
            case HEAVY_ARMOR:
            case LIFE_STEAL:
            case HEALTH_SPELL:
                if(player.getGoldBag().get(GOLD) < 40){
                    lackGold();
                } else {
                    updateShopInv(item, btn, " Buy for 40G");
                }
                break;
            case LEGENDARY_ARMOR:
                if(player.getGoldBag().get(GOLD) < 100){
                    lackGold();
                } else {
                    updateShopInv(item, btn, " Buy for 100G");
                }
                break;
            case FIRE_SPELL:
            case LIGHTING_SPELL:
                if(player.getGoldBag().get(GOLD) < 45){
                    lackGold();
                } else {
                    updateShopInv(item, btn, " Buy for 45G");
                }
                break;
            default:
        }
    }

    private void updateShopInv(ITEMS item, ItemButton btn, String buyMsg) {
        updatePlayerInventory(item);

        shopInventory.put(item, shopInventory.get(item) - 1);
        msg = "Lighting Bolt x" + shopInventory.get(item) + buyMsg;
        itemMsg.getPlayerDialogue(msg);

        removeOutOfStock(item, btn);
        buySellItem(item, true);
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

    private void buySellItem(ITEMS item, boolean isBuying) {
        switch (item) {
            case HEALTH_POTION:
            case MAGIC_POTION:
                playerGoldBag((isBuying ? 20 : 10), isBuying);
                break;
            case BROKEN_ARMOR:
                playerGoldBag((isBuying ? 10 : 5), isBuying);
                break;
            case LIGHT_ARMOR:
                playerGoldBag((isBuying ? 25 : 10), isBuying);
                break;
            case MEDIUM_ARMOR:
                playerGoldBag((isBuying ? 30 : 20), isBuying);
                break;
            case HEAVY_ARMOR:
                playerGoldBag((isBuying ? 40 : 25), isBuying);
                break;
            case LEGENDARY_ARMOR:
                playerGoldBag((isBuying ? 100 : 75), isBuying);
                break;
            case LIFE_STEAL:
            case HEALTH_SPELL:
                playerGoldBag((isBuying ? 40 : 20), isBuying);
                break;
            case FIRE_SPELL:
            case LIGHTING_SPELL:
                playerGoldBag((isBuying ? 45 : 25), isBuying);
                break;
            default:
        }
    }

    private void playerGoldBag(int price, boolean isBuying) {
        player.getGoldBag().put(GOLD, player.getGoldBag().get(GOLD) + (isBuying ? -price : price));
        shopPane.getChildren().remove(goldHolder);
        showPlayerGold();
    }
}
