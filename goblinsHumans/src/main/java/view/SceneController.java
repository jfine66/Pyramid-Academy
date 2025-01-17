package view;

import gameLogic.GameLogic;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Goblin;
import model.Human;

import java.nio.file.Paths;
import java.util.ArrayList;

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

    public static GameLevel levelOne = new GameLevel(new AnchorPane(), "Cave_level.jpg", "src/main/resources/my-first-walk-13852.mp3");
    public static GameLevel levelTwo = new GameLevel(new AnchorPane(),"level_two_sized.png", "src/main/resources/modern-rnb-all-your-base-15484.mp3");
    public static GameLevel levelThree = new GameLevel(new AnchorPane(), "level_three_sized.jpg", "src/main/resources/man-is-he-mega-glbml-22045.mp3");
    public static GameLevel levelFour = new GameLevel(new AnchorPane(), "level_four_sized.jpg", "src/main/resources/let-the-games-begin-21858.mp3");
    public static GameLevel levelFive = new GameLevel(new AnchorPane(), "level_five_sized.png", "src/main/resources/workout-10823.mp3");

    public static EndGame end = new EndGame();

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
        levelOne.getMediaPlayer().stop();
        levelTwo.getMediaPlayer().stop();
        levelThree.getMediaPlayer().stop();
        levelFour.getMediaPlayer().stop();
        levelFive.getMediaPlayer().stop();
        menuMusic();
        mainStage.setScene(menuScene);
    }

    public static void toRules(){
        mainStage.setScene(rulesScene);
    }

    public static void toCamp(){
        levelOne.getMediaPlayer().stop();
        levelTwo.getMediaPlayer().stop();
        levelThree.getMediaPlayer().stop();
        levelFour.getMediaPlayer().stop();
        levelFive.getMediaPlayer().stop();
        menuMusic();
        mainStage.setScene(campScene);
    }

    public static void toLevelOne(){
        game = new GameLogic(levelOne.getLevelPane());
        Scene levelOneScene = levelOne.getScene();
        ArrayList<Goblin> list = new ArrayList<>();
        for(int i = 0; i < 5;i++){
            list.add(new Goblin());
        }
        game.setListOfGoblins(list);
        game.removePlayer();
        game.gameStart();
//        levelOne.getMediaPlayer().play();
        mainStage.setScene(levelOneScene);
    }

    public static void toLevelTwo(){
        Scene levelTwoScene = levelTwo.getScene();
        game.setCurrentPane(levelTwo.getLevelPane());
        ArrayList<Goblin> list = new ArrayList<>();
        for(int i = 0; i < 10;i++){
            list.add(new Goblin());
        }
        game.setListOfGoblins(list);
        game.removePlayer();
        game.gameStart();
        levelTwo.getMediaPlayer().play();
        mainStage.setScene(levelTwoScene);
    }

    public static void toLevelThree(){
        Scene levelThreeScene = levelThree.getScene();
        game.setCurrentPane(levelThree.getLevelPane());
        ArrayList<Goblin> list = new ArrayList<>();
        for(int i = 0; i < 15;i++){
            list.add(new Goblin());
        }
        game.setListOfGoblins(list);
        game.removePlayer();
        game.gameStart();
        levelThree.getMediaPlayer().play();
        mainStage.setScene(levelThreeScene);
    }

    public static void toLevelFour(){
        Scene levelFourScene = levelFour.getScene();
        game.setCurrentPane(levelFour.getLevelPane());
        ArrayList<Goblin> list = new ArrayList<>();
        for(int i = 0; i < 20;i++){
            list.add(new Goblin());
        }
        game.setListOfGoblins(list);
        game.removePlayer();
        game.gameStart();
        mainStage.setScene(levelFourScene);
    }

    public static void toLevelFive(){
        Scene levelFiveScene = levelFive.getScene();
        game.setCurrentPane(levelFive.getLevelPane());
        ArrayList<Goblin> list = new ArrayList<>();
        for(int i = 0; i < 25;i++){
            list.add(new Goblin());
        }
        game.setListOfGoblins(list);
        game.removePlayer();
        game.gameStart();
        mainStage.setScene(levelFiveScene);
    }

    public static void toArmory(){
        armory.displayItems();
        mainStage.setScene(armoryScene);
    }

    public static void toShop(){
        shop.createStock();
        mainStage.setScene(shopScene);
    }

    public static void toEnd(){
        mainStage.setScene(end.getEndScene());
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
