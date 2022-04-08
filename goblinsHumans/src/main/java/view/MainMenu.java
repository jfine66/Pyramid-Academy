package view;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.MenuButtons;


import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    //setting up window panes
    private final AnchorPane menuPane;
    private final Scene menuScene;
    //Create buttons
    private final static int MENU_BUTTON_START_X = 300;
    private final static int MENU_BUTTON_START_Y = 150;
    List<MenuButtons> menuButtons;


    public MainMenu(){
        menuPane = new AnchorPane();
        menuScene = new Scene(menuPane, WIDTH, HEIGHT);
        menuButtons = new ArrayList<>();
        createButtons();
        createBackground();
    }


    public Scene getMenuScene(){
        return menuScene;
    }

    //Create Buttons Methods
    private void addButton(MenuButtons button){
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 150);
        menuButtons.add(button);
        menuPane.getChildren().add(button);
    }

    private void createButtons(){
        createStartButton();
        createInstructionsButton();
        createExitButton();
    }

    private void createStartButton(){
        MenuButtons startButton = new MenuButtons("PLAY");
        addButton(startButton);


        startButton.setOnAction(actionEvent -> SceneController.toLevelOne());
    }

    private void createInstructionsButton(){
        MenuButtons instructionsButton = new MenuButtons("RULES");
        addButton(instructionsButton);

        instructionsButton.setOnAction(actionEvent -> SceneController.toRules());
    }

    private void createExitButton(){
        MenuButtons exitButton = new MenuButtons("EXIT");
        addButton(exitButton);

        exitButton.setOnAction(actionEvent -> System.exit(0));
    }

    //Set Main Menu Background
    private void createBackground(){
        Image backgroundImage = new Image("fantasy_art-digital_art-mountains-castle.jpg", 1024,768,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        menuPane.setBackground(new Background(background));
    }


}
