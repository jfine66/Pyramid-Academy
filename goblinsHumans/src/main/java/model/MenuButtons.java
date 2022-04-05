package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
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
            setFont(Font.loadFont(FONT_PATH, 50));

        } catch(Exception e){
            setFont(Font.font("Verdana", 50));
        }
    }

    private void initializeButtonListeners(){

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());

            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });

    }

}
