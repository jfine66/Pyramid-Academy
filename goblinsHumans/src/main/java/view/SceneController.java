package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    //GET OTHER SCENES
    private static MainMenu menu = new MainMenu();
    private static Scene menuScene = menu.getMenuScene();
    private static RulesMenu rules = new RulesMenu();
    private static Scene rulesScene = rules.getRuleScene();

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
        mainStage.setScene(menuScene);
    }

    public static void toRules(){
        mainStage.setScene(rulesScene);
    }


}
