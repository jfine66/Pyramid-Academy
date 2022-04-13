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
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    private final Human player = SceneController.getPlayer();
    private final AnchorPane armoryPane;
    private final Scene armoryScene;
    private final StackPane itemHolder = new StackPane();
    private String msg = "";
    private final DialogueBox itemMsg = new DialogueBox();
    private final ActionButton back = new ActionButton("BACK");
    private MediaPlayer mediaPlayer;


    public ArmoryScene(){
        armoryPane = new AnchorPane();
        armoryScene = new Scene(armoryPane, WIDTH, HEIGHT);
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
        back.setOnMouseClicked(mouseEvent -> SceneController.toCamp());
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
                if(item == LIFE_STEAL || item == LIGHTING_SPELL || item == FIRE_SPELL){
                    itemButton.setOnMouseClicked(mouseClick -> playSound());
                }
                checkItem(item);
            });
            itemButton.setOnMouseExited(mouseEvent -> {
                armoryPane.getChildren().remove(itemMsg.armoryDesc(msg));
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
            case HEALTH_POTION:
                itemMsg.clear();
                msg = "A vibrant red liquid,\nradiating warmth\nrestores 1-10 points of health\nYou have x" + player.getInventory().get(HEALTH_POTION) +" of this item";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case MAGIC_POTION:
                itemMsg.clear();
                msg = "A memorizing dark blue,\nliquid restores 1-10\npoints of magic power\nYou have x" + player.getInventory().get(MAGIC_POTION) +" of this item";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case BROKEN_ARMOR:
                itemMsg.clear();
                msg = "Rusted and in disrepair\nit offers little,\nin the way of protection\nplus one to your AC\nYou have x" + player.getInventory().get(BROKEN_ARMOR) + " of this item";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case LIGHT_ARMOR:
                itemMsg.clear();
                msg = "Well kept chain-mail\nThe interwoven links provide protection\nplus two to your AC\nYou have x" + player.getInventory().get(LIGHT_ARMOR) + " of this item";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case MEDIUM_ARMOR:
                itemMsg.clear();
                msg = "Well kept breastplate\nplus three to your AC\nLeft click to equip\nYou have x" + player.getInventory().get(MEDIUM_ARMOR) + " of this item";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case HEAVY_ARMOR:
                itemMsg.clear();
                msg = "Heavy and thick splint mail\nplus four to your AC\nLeft click to equip\nYou have x" + player.getInventory().get(HEAVY_ARMOR) + " of this item";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case LEGENDARY_ARMOR:
                itemMsg.clear();
                msg = "Offers full protection\ndespite being so light\nplus five to your AC\nYou have x" + player.getInventory().get(LEGENDARY_ARMOR) + " of this item";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case LIFE_STEAL:
                itemMsg.clear();
                msg = "A dark purple bound book\nIt feels has like it hungers,\nfor life itself.\nSteal the life\nof your enemies\nRestores 5HP, Cost 5MP";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case HEALTH_SPELL:
                itemMsg.clear();
                msg = "A vibrant red leather book\nPluses with life energy\nRestores 10HP, Cost 5MP";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case FIRE_SPELL:
                itemMsg.clear();
                msg = "Hot to the touch\nThe magic is waiting,\nto burst forward\nCast a cone of fire,\nin front you\nCost 10MP";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            case LIGHTING_SPELL:
                itemMsg.clear();
                msg = "The tips of your fingers,\ntingle, The magic can,\nbarely be contained\nShoot out a lighting bolt\nCost 10MP";
                armoryPane.getChildren().add(itemMsg.armoryDesc(msg));
                itemMsg.armoryDesc(msg).setLayoutX(576);
                itemMsg.armoryDesc(msg).setLayoutY(256);
                break;
            default:
        }
    }

    private void playSound(){
        String url = "src/main/resources/419023__jacco18__acess-denied-buzz.mp3";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }



}
