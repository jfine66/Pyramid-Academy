package model;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DialogueBox {
    private final Rectangle r = new Rectangle(320,256,448, 192);
    private final Text text = new Text();
    private StackPane stack = new StackPane();

    public DialogueBox(){
        setFont();
    }

    public StackPane getPlayerDialogue(String msg){
        stack.getChildren().clear();
        r.setStrokeWidth(5);
        r.setStroke(Color.WHITE);
        r.setFill(Color.BLUE);
        r.setOpacity(0.8);
        text.setText(msg);
        stack.getChildren().addAll(r, text);
        return stack;
    }

    public StackPane getGoblinDialogue(String msg){
        stack.getChildren().clear();
        r.setStrokeWidth(5);
        r.setStroke(Color.WHITE);
        r.setFill(Color.RED);
        r.setOpacity(0.8);
        text.setText(msg);
        stack.getChildren().addAll(r, text);
        return stack;
    }

    public StackPane armoryDesc(String msg){
        stack.getChildren().clear();
        r.setStrokeWidth(5);
        r.setStroke(Color.WHITE);
        r.setFill(Color.BLUE);
        text.setText(msg);
        r.setWidth(320);
        r.setHeight(448);
        text.setTranslateY(-128);
        stack.getChildren().addAll(r, text);
        return stack;
    }

    public void clear(){
        stack = new StackPane();
    }

    private void setFont(){
        try{
            String FONT_PATH = "file:src/main/resources/IMMORTAL.ttf";
            text.setFill(Color.WHITE);
            text.setFont(Font.loadFont(FONT_PATH, 23));

        } catch(Exception e){
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Verdana", 23));
        }
    }




}
