package model;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DialogueBox {
    private final Rectangle r = new Rectangle(0,0,240, 64);
    private final Text text = new Text();
    private final StackPane stack = new StackPane();

    public DialogueBox(){
        setFont();
    }

    public StackPane getPlayerDialogue(String msg){
        stack.getChildren().clear();
        r.setFill(Color.BLUE);
        r.setOpacity(0.5);
        text.setText(msg);
        stack.getChildren().addAll(r, text);
        return stack;
    }

    private void setFont(){
        try{
            String FONT_PATH = "file:src/main/resources/IMMORTAL.ttf";
            text.setFill(Color.WHITE);
            text.setFont(Font.loadFont(FONT_PATH, 25));

        } catch(Exception e){
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Verdana", 25));
        }
    }




}
