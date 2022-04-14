package model;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ActionButton extends Button {

    public ActionButton(String text){
        setText(text);
        setTextFill(Color.WHITE);
        setButtonFont();
        setPrefWidth(128);
        setPrefHeight(64);
        String buttonStyle = "-fx-background-color: transparent;";
        setStyle(buttonStyle);
        initializeButtonListeners();
    }

    private void setButtonFont(){
        try{
            String FONT_PATH = "file:src/main/resources/THE_LAST_KINGDOM.ttf";
            setFont(Font.loadFont(FONT_PATH, 30));
        } catch(Exception e){
            setFont(Font.font("Verdana", 30));
        }
    }

    private void initializeButtonListeners(){
        setOnMouseEntered(mouseEvent -> {
          setEffect(new DropShadow());
          setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 3;");
        });

        setOnMouseExited(mouseEvent -> {
            setEffect(null);
            setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        });
    }
}
