package gameLogic;


import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Banner;

public class GameLogic {
    private Banner banner = new Banner();
    private final StackPane playerBanner = banner.getPlayerBanner();

    





























    //BANNER ANIMATION
    private void moveBanner(){
        playerBanner.setLayoutX(1024);
        playerBanner.setLayoutY(180);

        TranslateTransition slideIn = new TranslateTransition();
        slideIn.setDuration(Duration.seconds(0.3));
        slideIn.setToX(-1024);

        PauseTransition pause = new PauseTransition(Duration.millis(800));

        TranslateTransition slideOut = new TranslateTransition();
        slideOut.setDuration(Duration.seconds(0.3));
        slideOut.setToX(-2048);

        SequentialTransition seqT = new SequentialTransition(playerBanner, slideIn, pause, slideOut);

        seqT.play();
    }


}
