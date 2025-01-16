package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.*;

import java.nio.file.Paths;

import static model.ITEMS.*;

public class ArmoryScene {
    private final Human player = SceneController.getPlayer();
    private final AnchorPane armoryPane;
    private final Scene armoryScene;
    private final StackPane itemHolder = new StackPane();
    private String msg = "";
    private final DialogueBox itemMsg = new DialogueBox();
    private final ActionButton back = new ActionButton("BACK");

    public ArmoryScene(){
        armoryPane = new AnchorPane();
        armoryScene = new Scene(armoryPane, SceneController.WIDTH, SceneController.HEIGHT);
        createBackground();
        setBackBtn();
    }

    private void createBackground(){
        Image backgroundImage = new Image("armory_scene.jpg", 1024,768,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        armoryPane.setBackground(new Background(background));
    }

    private void setBackBtn(){
        back.setLayoutX(64);
        back.setLayoutY(546);
        armoryPane.getChildren().add(back);
        back.setOnMouseClicked(mouseEvent ->SceneController.toCamp());
    }

    public Scene getArmoryScene() {
        return armoryScene;
    }

    public void displayItems(){
        armoryPane.getChildren().remove(itemHolder);
        itemHolder.getChildren().clear();
        int counter = 0;
        int xPos = 192;
        int yPos = 192;

        for(ITEMS item : player.getInventory().keySet()){
            ItemButton itemButton = new ItemButton(item, itemHolder, armoryPane);
            itemButton.setOnMouseEntered(mouseEvent -> {
                if(item == LIFE_STEAL || item == LIGHTING_SPELL || item == FIRE_SPELL){
                    itemButton.setOnMouseClicked(mouseClick -> playSound());
                }
                checkItem(item);
            });

            itemButton.setOnMouseExited(mouseEvent -> armoryPane.getChildren().remove(itemMsg.armoryDesc(msg)));

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
        itemMsg.armoryDesc(msg).setLayoutX(576);
        itemMsg.armoryDesc(msg).setLayoutY(256);

        switch (item){
            case HEALTH_POTION:
                itemDescription("A vibrant red liquid,\nradiating warmth\nrestores 1-10 points of health\nYou have x" + player.getInventory().get(HEALTH_POTION) +" of this item");
                break;
            case MAGIC_POTION:
                itemDescription("A memorizing dark blue,\nliquid restores 1-10\npoints of magic power\nYou have x" + player.getInventory().get(MAGIC_POTION) +" of this item");
                break;
            case BROKEN_ARMOR:
                itemDescription("Rusted and in disrepair\nit offers little,\nin the way of protection\nplus one to your AC\nYou have x" + player.getInventory().get(BROKEN_ARMOR) + " of this item");
                break;
            case LIGHT_ARMOR:
                itemDescription("Well kept chain-mail\nThe interwoven links provide protection\nplus two to your AC\nYou have x" + player.getInventory().get(LIGHT_ARMOR) + " of this item");
                break;
            case MEDIUM_ARMOR:
                itemDescription("Well kept breastplate\nplus three to your AC\nLeft click to equip\nYou have x" + player.getInventory().get(MEDIUM_ARMOR) + " of this item");
                break;
            case HEAVY_ARMOR:
                itemDescription("Heavy and thick splint mail\nplus four to your AC\nLeft click to equip\nYou have x" + player.getInventory().get(HEAVY_ARMOR) + " of this item");
                break;
            case LEGENDARY_ARMOR:
                itemDescription("Offers full protection\ndespite being so light\nplus five to your AC\nYou have x" + player.getInventory().get(LEGENDARY_ARMOR) + " of this item");
                break;
            case LIFE_STEAL:
                itemDescription("A dark purple bound book\nIt feels has like it hungers,\nfor life itself.\nSteal the life\nof your enemies\nRestores 5HP, Cost 5MP");
                break;
            case HEALTH_SPELL:
                itemDescription("A vibrant red leather book\nPluses with life energy\nRestores 10HP, Cost 5MP");
                break;
            case FIRE_SPELL:
                itemDescription("Hot to the touch\nThe magic is waiting,\nto burst forward\nCast a cone of fire,\nin front you\nCost 10MP");
                break;
            case LIGHTING_SPELL:
                itemDescription("The tips of your fingers,\ntingle, The magic can,\nbarely be contained\nShoot out a lighting bolt\nCost 10MP");
                break;
            default:
        }
    }

    private void itemDescription(String message) {
        itemMsg.clear();
        msg = message;
        armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
    }

    private void playSound(){
        String url = "src/main/resources/419023__jacco18__acess-denied-buzz.mp3";
        Media h = new Media(Paths.get(url).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }

}
