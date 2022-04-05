package com.example.goblinshumans;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.MainMenu;


public class Main extends Application {
    @Override
    public void start(Stage stage){
        MainMenu menu = new MainMenu();
        stage = menu.getMenuStage();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}