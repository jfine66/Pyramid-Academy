package view;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.DialogueBox;
import model.Human;
import model.ITEMS;
import model.ItemButton;


public class ArmoryScene {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    private final Human player = SceneController.getPlayer();
    private final AnchorPane armoryPane;
    private final Scene armoryScene;
    private final StackPane itemHolder = new StackPane();
    private String msg = "";
    private final DialogueBox itemMsg = new DialogueBox();


    public ArmoryScene(){
        armoryPane = new AnchorPane();
        armoryScene = new Scene(armoryPane, WIDTH, HEIGHT);
        createBackground();
    }

    private void createBackground(){
        Image backgroundImage = new Image("armory_scene.jpg", 1024,768,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        armoryPane.setBackground(new Background(background));
    }

    public Scene getArmoryScene() {
        return armoryScene;
    }

    public void displayItems(){
        itemHolder.getChildren().clear();
        int counter = 0;
        int xPos = 192;
        int yPos = 192;

        for(ITEMS item : player.getInventory().keySet()){
            ItemButton itemButton = new ItemButton(item, itemHolder, armoryPane);
            itemButton.setOnMouseEntered(mouseEvent -> {

            });
            itemButton.setTranslateX(xPos += 64);
            itemButton.setTranslateY(yPos);
            counter++;
            if(counter > 3){
                xPos = 192;
                yPos += 64;
                counter = 0;
            }
            itemHolder.getChildren().add(itemButton);
        }

        itemHolder.setLayoutX(64);
        itemHolder.setLayoutY(64);
        armoryPane.getChildren().add(itemHolder);
    }

    private void checkItem(ITEMS item){
        switch (item){
            case GOLD:
                
        }
    }



}
