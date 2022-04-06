package model;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Banner {
    private final Rectangle r = new Rectangle(0,0,1024, 128);
    private final Text text = new Text();
    private final StackPane stack = new StackPane();

    public Banner(){
        setFont();
    }


    public StackPane getPlayerBanner(){
        stack.getChildren().clear();
        r.setFill(Color.BLUE);
        r.setOpacity(0.5);
        text.setText("YOUR TURN");
        stack.getChildren().addAll(r, text);
        return stack;
    }

    public StackPane getGoblinsBanner(){
        stack.getChildren().clear();
        r.setFill(Color.RED);
        r.setOpacity(0.5);
        text.setText("ENEMY TURN");
        stack.getChildren().addAll(r, text);
        return stack;
    }

    private void setFont(){
        try{
            String FONT_PATH = "file:src/main/resources/IMMORTAL.ttf";
            text.setFill(Color.WHITE);
            text.setFont(Font.loadFont(FONT_PATH, 40));

        } catch(Exception e){
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Verdana", 40));
        }
    }


}
