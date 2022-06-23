package fine.jonathon.tictactoe.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class StartApplication extends Application {
    @Override
    public void start(Stage stage) {
        // Creates Main Pane for our game
        BorderPane pane = new BorderPane();

        // Everything that will need to go on the pane
        Label turnOrder = new Label("Turn: X");
        GridPane grid = new GridPane();
        Button button = new Button("RESTART");

        //Game Logic Object has all of our game logic in its class
        GameGrid gameGrid = new GameGrid(grid, turnOrder);

        // Give the player the ability to restart
        button.setOnAction((event) -> gameGrid.playAgain());

        // A Vertical Box to organize our pane
        VBox vBox = new VBox();
        vBox.getChildren().addAll(turnOrder, grid, button);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);

        // Place box in the middle of pane
        pane.setCenter(vBox);

        // Used to set the icon of our game
        Image image = new Image("tictactoe.png");
        stage.getIcons().add(image);
        stage.setResizable(false);

        // Scene for game
        Scene scene = new Scene(pane, 300, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}