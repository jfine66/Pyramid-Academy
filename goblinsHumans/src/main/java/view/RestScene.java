package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.MenuButtons;

import java.util.ArrayList;
import java.util.List;

public class RestScene {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    //setting up window panes
    private final AnchorPane campPane;
    private final Scene campScene;
    //Create Buttons
    private final static int MENU_LAYOUT_X = 50;
    private final static int MENU_LAYOUT_Y = 100;
    List<MenuButtons> buttonList;

    public RestScene(){
        campPane = new AnchorPane();
        campScene = new Scene(campPane, WIDTH, HEIGHT);
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
        MenuButtons nextLevel = new MenuButtons("NEXT");
        addButton(nextLevel);
    }

    private void createShopButton(){
        MenuButtons shopButton = new MenuButtons("SHOP");
        addButton(shopButton);
    }

    private void createItemsButton(){
        MenuButtons itemsButton = new MenuButtons("ITEMS");
        addButton(itemsButton);
    }
}
