package com.example.goblinshumans;

import javafx.application.Application;
import javafx.stage.Stage;
import view.SceneController;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        SceneController main = new SceneController();
        stage = main.getMainStage();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
