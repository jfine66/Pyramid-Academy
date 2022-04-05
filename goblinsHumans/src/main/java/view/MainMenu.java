package view;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.MenuButtons;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    // Width and Height for window
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    //setting up window panes
    private final AnchorPane menuPane;
    private Scene menuScene;
    private Stage menuStage;
    //Path to icon image
    private String ICON_PATH = "pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png";
    //Create buttons
    private final static int MENU_BUTTON_START_X = 300;
    private final static int MENU_BUTTON_START_Y = 150;
    List<MenuButtons> menuButtons;



    public MainMenu(){
        menuPane = new AnchorPane();
        menuScene = new Scene(menuPane, WIDTH, HEIGHT);
        menuStage = new Stage();
        Image icon = new Image(ICON_PATH);
        menuStage.setTitle("Humans vs Goblins");
        menuStage.getIcons().add(icon);
        menuStage.setResizable(false);
        menuStage.setScene(menuScene);
        menuButtons = new ArrayList<>();
        createButtons();

    }

    public Stage getMenuStage(){
        return menuStage;
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
    }

    private void createInstructionsButton(){
        MenuButtons instructionsButton = new MenuButtons("RULES");
        addButton(instructionsButton);
    }

    private void createExitButton(){
        MenuButtons exitButton = new MenuButtons("EXIT");
        addButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }

}
