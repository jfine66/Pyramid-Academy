package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.Human;
import model.ITEMS;
import model.MenuButtons;

import java.util.ArrayList;
import java.util.List;

public class RestScene {
    //setting up window panes
    private final AnchorPane campPane;
    private final Scene campScene;
    //Create Buttons
    private final static int MENU_LAYOUT_X = 50;
    private final static int MENU_LAYOUT_Y = 100;
    List<MenuButtons> buttonList;
    private int counter = 0;

    public RestScene(){
        campPane = new AnchorPane();
        campScene = new Scene(campPane, SceneController.WIDTH, SceneController.HEIGHT);
        buttonList = new ArrayList<>();
        createBackground();
        createButtons();
    }

    public Scene getCampScene(){
        return campScene;
    }

    private void createBackground(){
        Image backgroundImage = new Image("rest_stage.png", 1024,768,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        campPane.setBackground(new Background(background));
    }

    private void createButtons(){
        createNextLevelButton();
        createShopButton();
        createItemsButton();
    }

    private void addButton(MenuButtons button){
        button.setLayoutX(MENU_LAYOUT_X);
        button.setLayoutY(MENU_LAYOUT_Y + buttonList.size() * 150);
        buttonList.add(button);
        campPane.getChildren().add(button);
    }

    private void createNextLevelButton(){
        Human player = SceneController.getPlayer();

        MenuButtons nextLevel = new MenuButtons("NEXT");
        addButton(nextLevel);
        nextLevel.setOnMouseClicked(mouseEvent -> {
            player.setHealth(player.getMaxHP());
            player.setMagic(player.getMaxMP());
            switch (counter){
                case 0:
                    SceneController.toLevelTwo();
                    player.setConMod(player.getConMod() + 1);
                    player.setDexMod(player.getDexMod() + 1);
                    player.setIntMod(player.getIntMod() + 1);
                    player.setStrengthMod(player.getStrengthMod() + 1);
                    player.getGoldBag().put(ITEMS.GOLD, player.getGoldBag().get(ITEMS.GOLD) + 10);
                    counter++;
                    break;
                case 1:
                    SceneController.toLevelThree();
                    player.setConMod(player.getConMod() + 2);
                    player.setDexMod(player.getDexMod() + 2);
                    player.setIntMod(player.getIntMod() + 2);
                    player.setStrengthMod(player.getStrengthMod() + 2);
                    player.getGoldBag().put(ITEMS.GOLD, player.getGoldBag().get(ITEMS.GOLD) + 20);
                    counter++;
                    break;
                case 2:
                    SceneController.toLevelFour();
                    player.setConMod(player.getConMod() + 3);
                    player.setDexMod(player.getDexMod() + 3);
                    player.setIntMod(player.getIntMod() + 3);
                    player.setStrengthMod(player.getStrengthMod() + 3);
                    player.getGoldBag().put(ITEMS.GOLD, player.getGoldBag().get(ITEMS.GOLD) + 30);
                    counter++;
                    break;
                case 3:
                    SceneController.toLevelFive();
                    player.setConMod(player.getConMod() + 4);
                    player.setDexMod(player.getDexMod() + 4);
                    player.setIntMod(player.getIntMod() + 4);
                    player.setStrengthMod(player.getStrengthMod() + 4);
                    player.getGoldBag().put(ITEMS.GOLD, player.getGoldBag().get(ITEMS.GOLD) + 40);
                    counter++;
                    break;
                case 4:
                    SceneController.toEnd();
                default:
            }
        });
    }

    private void createShopButton(){
        MenuButtons shopButton = new MenuButtons("SHOP");
        addButton(shopButton);
        shopButton.setOnMouseClicked(mouseEvent -> SceneController.toShop());
    }

    private void createItemsButton(){
        MenuButtons itemsButton = new MenuButtons("ITEMS");
        itemsButton.setOnMouseClicked(mouseEvent -> {
            SceneController.toArmory();
        });
        addButton(itemsButton);
    }
}
