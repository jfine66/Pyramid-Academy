package view;

import gameLogic.GameLogic;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Human;

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
    private static final GameLogic game = new GameLogic(player);
    //GET OTHER SCENES
    private static final MainMenu menu = new MainMenu();
    private static final Scene menuScene = menu.getMenuScene();
    private static final RulesMenu rules = new RulesMenu();
    private static final Scene rulesScene = rules.getRuleScene();
    private static final RestScene camp = new RestScene();
    private static final Scene campScene = camp.getCampScene();
    private static final FirstLevel levelOne = new FirstLevel();
    private static final Scene levelOneScene = levelOne.getScene();


    public SceneController(){
        mainWindow = new AnchorPane();
        mainScene = new Scene(mainWindow, WIDTH, HEIGHT);
        mainStage = new Stage();

        Image icon = new Image(ICON_PATH);
        mainStage.setTitle("Humans vs Goblins");
        mainStage.getIcons().add(icon);
        mainStage.setResizable(false);

        mainStage.setScene(levelOneScene);
    }

    public Stage getMainStage(){
        return mainStage;
    }

    public static void toMainMenu(){
        mainStage.setScene(menuScene);
    }

    public static void toRules(){
        mainStage.setScene(rulesScene);
    }

    public static void toCamp(){
        mainStage.setScene(campScene);
    }

    public static void toLevelOne(){
        mainStage.setScene(levelOneScene);
    }

    public static Human getPlayer() {
        return player;
    }

    public static GameLogic getGame(){
        return game;
    }
}
