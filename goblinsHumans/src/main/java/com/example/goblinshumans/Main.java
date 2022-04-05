package com.example.goblinshumans;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage){
     Group root = new Group();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.setTitle("Humans vs Goblins");
     stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}