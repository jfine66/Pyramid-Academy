package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import model.DialogueBox;
import model.MenuButtons;

public class RulesMenu{
    //setting up window panes
    private final AnchorPane rulesPane;
    private final Scene ruleScene;
    private final Text text = new Text();
    private final StackPane rules = new StackPane();
    private final DialogueBox gameRules = new DialogueBox();

    public RulesMenu(){
        rulesPane = new AnchorPane();
        ruleScene = new Scene(rulesPane, SceneController.WIDTH, SceneController.HEIGHT);
        createBackground();
        createBackButton();
        createRules();
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

    private void createRules(){
        String newRules = "THE RULES ARE SIMPLE\nTHE GAME IS TURN BASED AFTER YOUR TURN\nTHE GOBLINS GO\nON YOUR TURN YOU CAN\nMOVE, ATTACK, USE ITEM, END TURN\nYOU MAY ONLY ATTACK AND MOVE ONCE\n" +
                "IF YOU TRY TO DO SO AGAIN A MESSAGE WILL APPEAR,\nSIMPLY LEFT CLICK TO GID RID OF IT.\nYOU CAN USE HAS MANY ITEMS HAS YOU LIKE\nRIGHT CLICK TO LEARN WHAT THEY DO\nLEFT CLICK TO USE\nAFTER USING" +
                " AN ITEM,\nA MESSAGE WILL APPEAR FOR A\nSHORT TIME TELLING YOU WHAT IT DID\nAFTER EACH LEVEL YOU WILL GET\nSOME GOLD AND BONUSES TO YOUR PLAYER\nTHEN GO TO CAMP, AT CAMP YOU CAN\n" +
                "GO ONTO THE NEXT LEVEL, SHOP OR\nSEE THE ITEMS YOU ALREADY HAVE\nGOOD LUCK";
        gameRules.rules(newRules).setLayoutX(130);
        rulesPane.getChildren().add(gameRules.rules(newRules));
    }
}
