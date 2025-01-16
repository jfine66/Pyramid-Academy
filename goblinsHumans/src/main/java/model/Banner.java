package model;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.SceneController;


public class Banner {
    private final Rectangle r = new Rectangle(0,0,1024, 128);
    private final Text text = new Text();
    private final StackPane stack = new StackPane();

    public Banner(){
        setFont();
    }

//    Can turn these two methods into one???
    public StackPane getPlayerBanner(){
        stack.getChildren().clear();
        r.setFill(Color.BLUE);
        r.setOpacity(0.8);
        text.setText("YOUR TURN");
        stack.getChildren().addAll(r, text);
        return stack;
    }

    public StackPane getGoblinsBanner(){
        stack.getChildren().clear();
        r.setFill(Color.RED);
        r.setOpacity(0.8);
        text.setText("ENEMY TURN");
        stack.getChildren().addAll(r, text);
        return stack;
    }

    public StackPane getVictoryBanner(){
        stack.getChildren().clear();
        r.setFill(Color.GREEN);
        r.setOpacity(0.8);
        r.setHeight(192);
        text.setText("YOU HAVE SLAIN ALL THE GOBLINS\nPROCEED TO CAMP?");
        text.setTranslateY(-50);
        ActionButton toCamp = new ActionButton("PROCEED");
        toCamp.setPrefWidth(192);
        toCamp.setTranslateY(64);
        toCamp.setOnMouseClicked(mouseEvent -> SceneController.toCamp());
        stack.getChildren().addAll(r, text, toCamp);
        return stack;
    }

    public StackPane getDefeatBanner(ActionButton button){
        stack.getChildren().clear();
        r.setFill(Color.RED);
        r.setOpacity(0.8);
        r.setHeight(192);
        text.setText("YOU HAVE BEEN SLAIN");
        text.setTranslateY(-50);
        stack.getChildren().addAll(r, text, button);
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
