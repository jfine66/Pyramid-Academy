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
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    //SETTING UP WINDOW
    private static Stage mainStage;
    //SET UP GAME
    private static GameLogic game;
    private static final Human player = new Human();
    //GET OTHER SCENES
    private static final MainMenu menu = new MainMenu();
    private static final Scene menuScene = menu.getMenuScene();
    private static final RulesMenu rules = new RulesMenu();
    private static final Scene rulesScene = rules.getRuleScene();
    private static final RestScene camp = new RestScene();
    private static final Scene campScene = camp.getCampScene();
    public static FirstLevel levelOne = new FirstLevel(new AnchorPane());
    private static final ArmoryScene armory = new ArmoryScene();
    private static final Scene armoryScene = armory.getArmoryScene();
    private static final ShopScene shop = new ShopScene();
    private static final Scene shopScene = shop.getShopScene();


    public SceneController(){
        mainStage = new Stage();

        String ICON_PATH = "pixel-goblin-studio-pixel-goblin-rug-minecraft-transparent-png-2764172.png";
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
        Scene levelOneScene = levelOne.getScene();
        game = new GameLogic(levelOne.getLevelOnePane());
        mainStage.setScene(levelOneScene);
    }

    public static void toArmory(){
        armory.displayItems();
        mainStage.setScene(armoryScene);
    }

    public static void toShop(){
        shop.createStock();
        mainStage.setScene(shopScene);
    }

    public static Human getPlayer() {
        return player;
    }

    public static GameLogic getGame(){return game;}


    //SCENE MUSIC
    private static void menuMusic(){
        String url = "src/main/resources/2019-07-29_-_Elven_Forest_-_FesliyanStudios.com_-_David_Renda.mp3";
        Media h = new Media(Paths.get(url).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        System.out.println("Scene Player is playing");
        mediaPlayer.play();
    }


}
