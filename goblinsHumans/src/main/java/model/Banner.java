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

    public StackPane getBanner(Color color, String msg) {
        stack.getChildren().clear();
        r.setFill(color);
        r.setOpacity(0.8);
        text.setText(msg);
        stack.getChildren().addAll(r, text);
        return stack;
    }

    public StackPane getVictoryBanner(){
        resultBanner(Color.GREEN, "YOU HAVE SLAIN ALL THE GOBLINS\nPROCEED TO CAMP?");
        ActionButton toCamp = new ActionButton("PROCEED");
        toCamp.setPrefWidth(192);
        toCamp.setTranslateY(64);
        toCamp.setOnMouseClicked(mouseEvent -> SceneController.toCamp());
        stack.getChildren().addAll(r, text, toCamp);
        return stack;
    }

    public StackPane getDefeatBanner(ActionButton button){
        resultBanner(Color.RED, "YOU HAVE BEEN SLAIN");
        stack.getChildren().addAll(r, text, button);
        return stack;
    }

    private void resultBanner(Color color, String msg) {
        stack.getChildren().clear();
        r.setFill(color);
        r.setOpacity(0.8);
        r.setHeight(192);
        text.setText(msg);
        text.setTranslateY(-50);
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
