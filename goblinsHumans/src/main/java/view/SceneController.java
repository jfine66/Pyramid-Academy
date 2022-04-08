package view;

import gameLogic.GameLogic;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Human;

import java.nio.file.Paths;

public class SceneController {
    //WIDTH AND HEIGHT FOR STAGE
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    //SETTING UP WINDOW
    private final AnchorPane mainWindow;
    private Scene mainScene;
    private static Stage mainStage;
    //PATH FOR ICON
    private String ICON_PATH = "pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png";
    //SET UP GAME
    private static final Human player = new Human();
    //GET OTHER SCENES
    private static final MainMenu menu = new MainMenu();
    private static final Scene menuScene = menu.getMenuScene();
    private static final RulesMenu rules = new RulesMenu();
    private static final Scene rulesScene = rules.getRuleScene();
    private static final RestScene camp = new RestScene();
    private static final Scene campScene = camp.getCampScene();
    private static FirstLevel levelOne = new FirstLevel();
    private static Scene levelOneScene = levelOne.getScene();
    private static MediaPlayer mediaPlayer;


    public SceneController(){
        mainWindow = new AnchorPane();
        mainScene = new Scene(mainWindow, WIDTH, HEIGHT);
        mainStage = new Stage();

        Image icon = new Image(ICON_PATH);
        mainStage.setTitle("Humans vs Goblins");
        mainStage.getIcons().add(icon);
        mainStage.setResizable(false);

        mainStage.setScene(menuScene);
    }

    public Stage getMainStage(){
        return mainStage;
    }

    public static void toMainMenu(){
        menuMusic();
        mainStage.setScene(menuScene);
    }

    public static void toRules(){
        mainStage.setScene(rulesScene);
    }

    public static void toCamp(){
        menuMusic();
        mainStage.setScene(campScene);
    }

    public static void toLevelOne(){
        levelOne = new FirstLevel();
        levelOneScene = levelOne.getScene();
        mainStage.setScene(levelOneScene);
    }

    public static Human getPlayer() {
        return player;
    }

    //SCENE MUSIC
    private static void menuMusic(){
        String url = "src/main/resources/2019-07-29_-_Elven_Forest_-_FesliyanStudios.com_-_David_Renda.mp3";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }


}
