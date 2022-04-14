package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import model.MenuButtons;

public class RulesMenu{
    //setting up window panes
    private final AnchorPane rulesPane;
    private final Scene ruleScene;
    private final Text text = new Text();
    private final StackPane rules = new StackPane();



    public RulesMenu(){
        rulesPane = new AnchorPane();
        ruleScene = new Scene(rulesPane, SceneController.WIDTH, SceneController.HEIGHT);
        createBackground();
        createBackButton();
        createRulesText();
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

    private void createRulesText(){
        Image backgroundImage = new Image("old_paper_letter_template.png", 700,700,false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        rules.setBackground(new Background(background));

        rules.setPrefWidth(700);
        rules.setPrefHeight(700);
        text.setText("PLACEHOLDER");
        rules.getChildren().add(text);
        rulesPane.getChildren().add(rules);
    }



}
