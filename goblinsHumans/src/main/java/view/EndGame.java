package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MenuButtons;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EndGame{
    //setting up window panes
    private final AnchorPane menuPane;
    private final Scene menuScene;
    //Create buttons
    List<MenuButtons> menuButtons;
    MediaPlayer mediaPlayer;

    public EndGame(){
        menuPane = new AnchorPane();
        menuScene = new Scene(menuPane, SceneController.WIDTH, SceneController.HEIGHT);
        menuButtons = new ArrayList<>();
        createButtons();
        createBackground();
        createText();
    }

    public Scene getEndScene(){
        return menuScene;
    }

    //Create Buttons Methods
    private void addButton(MenuButtons button){
        button.setLayoutX(300);
        button.setLayoutY(300 + menuButtons.size() * 150);
        menuButtons.add(button);
        menuPane.getChildren().add(button);
    }

    private void createText(){
        Text text = new Text();
        text.setText("YOU HAVE WON NOBLE HERO\nYOU HAVE RID THIS LAND OF GOBLINS\nTHEY WILL SING YOUR LEGEND FOR AGES");
        text.setFill(Color.WHITE);
        text.setLayoutX(300);
        text.setLayoutY(150);
        text.setFont(Font.loadFont("file:src/main/resources/THE_LAST_KINGDOM.ttf", 40));
        menuPane.getChildren().add(text);
    }


    private void createButtons(){
        createExitButton();
    }

    private void createExitButton(){
        MenuButtons exitButton = new MenuButtons("EXIT");
        addButton(exitButton);
        exitButton.setOnAction(actionEvent -> System.exit(0));
    }

    //Set Main Menu Background
    protected void createBackground(){
        Image backgroundImage = new Image("fantasy_art-digital_art-mountains-castle.jpg", 1024,768,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        menuPane.setBackground(new Background(background));
    }

}
