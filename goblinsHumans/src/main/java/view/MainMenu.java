package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.MenuButtons;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    //setting up window panes
    private final AnchorPane menuPane;
    private final Scene menuScene;
    //Create buttons
    List<MenuButtons> menuButtons;
    MediaPlayer mediaPlayer;

    public MainMenu(){
        menuPane = new AnchorPane();
        menuScene = new Scene(menuPane, SceneController.WIDTH, SceneController.HEIGHT);
        menuButtons = new ArrayList<>();
        mainBackgroundMusic();
        createButtons();
        createBackground();
    }

    public Scene getMenuScene(){
        return menuScene;
    }

    //Create Buttons Methods
    private void addButton(MenuButtons button){
        button.setLayoutX(300);
        button.setLayoutY(150 + menuButtons.size() * 150);
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

        startButton.setOnAction(actionEvent -> {
            mediaPlayer.stop();
            SceneController.toLevelOne();
        });
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

    private void mainBackgroundMusic(){
        String url = "src/main/resources/2019-07-29_-_Elven_Forest_-_FesliyanStudios.com_-_David_Renda.mp3";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }
}
