package model;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuButtons extends Button {

    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('fantasy_button.png');";

    public MenuButtons(String text){
        setText(text);
        setTextFill(Color.WHITE);
        setButtonFont();
        setPrefWidth(418);
        setPrefHeight(150);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }

    private void setButtonFont(){
        try{
            String FONT_PATH = "file:src/main/resources/IMMORTAL.ttf";
            setFont(Font.loadFont(FONT_PATH, 40));

        } catch(Exception e){
            setFont(Font.font("Verdana", 40));
        }
    }

    private void initializeButtonListeners(){

        setOnMouseEntered(mouseEvent -> setEffect(new DropShadow()));

        setOnMouseExited(mouseEvent -> setEffect(null));

    }

}
