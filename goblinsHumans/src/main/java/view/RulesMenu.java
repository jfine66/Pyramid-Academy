package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.MenuButtons;

public class RulesMenu{
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    //setting up window panes
    private final AnchorPane rulesPane;
    private final Scene ruleScene;



    public RulesMenu(){
        rulesPane = new AnchorPane();
        ruleScene = new Scene(rulesPane, WIDTH,HEIGHT);
        createBackground();
        createBackButton();
    }

    public Scene getRuleScene() {
        return ruleScene;
    }

    private void createBackground(){
        Image backgroundImage = new Image("fantasy_art-digital_art-mountains-castle.jpg", 1024,768,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        rulesPane.setBackground(new Background(background));
    }

    private void createBackButton(){
        MenuButtons backButton = new MenuButtons("BACK");
        backButton.setLayoutX(300);
        backButton.setLayoutY(600);
        rulesPane.getChildren().add(backButton);

        backButton.setOnAction(actionEvent -> SceneController.toMainMenu());
    }



}
