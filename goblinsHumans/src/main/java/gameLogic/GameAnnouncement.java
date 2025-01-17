package gameLogic;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.ActionButton;
import model.Banner;
import model.DialogueBox;
import view.SceneController;

public class GameAnnouncement {

    private final Banner banner = new Banner();
    private final Banner gobBanner = new Banner();
    private final Banner victoryDisplay = new Banner();
    private final Banner defeatDisplay = new Banner();

    private final StackPane playerBanner = banner.getBanner(Color.BLUE, "YOUR TURN");
    private final StackPane goblinBanner = gobBanner.getBanner(Color.RED, "ENEMY TURN");
    private final StackPane victoryBanner = victoryDisplay.getVictoryBanner();
    private final StackPane defeatBanner = defeatDisplay.getDefeatBanner(mainMenuButton());

    private final DialogueBox playerDialogueBox = new DialogueBox();
    private final DialogueBox goblinDialogueBox = new DialogueBox();

    private final AnchorPane currentPane;


    public GameAnnouncement(AnchorPane currentPane) {
        this.currentPane = currentPane;
    }

    private ActionButton mainMenuButton(){
        ActionButton toCamp = new ActionButton("MAIN MENU");
        toCamp.setPrefWidth(192);
        toCamp.setTranslateY(64);
        toCamp.setOnMouseClicked(mouseEvent -> {
            currentPane.getChildren().clear();
            SceneController.toMainMenu();
        });

        return toCamp;
    }

    public StackPane getPlayerBanner() {
        return playerBanner;
    }

    public StackPane getGoblinBanner() {
        return goblinBanner;
    }

    public StackPane getVictoryBanner() {
        return victoryBanner;
    }

    public StackPane getDefeatBanner() {
        return defeatBanner;
    }

    public DialogueBox getPlayerDialogueBox() {
        return playerDialogueBox;
    }

    public DialogueBox getGoblinDialogueBox() {
        return goblinDialogueBox;
    }
}
