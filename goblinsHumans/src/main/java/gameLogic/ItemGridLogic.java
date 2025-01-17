package gameLogic;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.ActionButton;
import model.GameEntity;
import model.Human;
import model.ITEMS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static gameLogic.GameLogic.back;

public class ItemGridLogic {

    private final StackPane menuPane = new StackPane();
    private AnchorPane currentPane;
    private ArrayList<Rectangle> recList;
    private Human player;
    private GameAnnouncement announcements;
    private HashMap<ArrayList<Integer>, GameEntity> gridPos;
    private String msg;

    public ItemGridLogic() {
    }

    public ItemGridLogic(Human player,
                         AnchorPane currentPane,
                         ArrayList<Rectangle> recList,
                         GameAnnouncement announcements) {
        this.player = player;
        this.currentPane = currentPane;
        this.recList = recList;
    }

    public void setLifeStealListeners(){
        for(Rectangle r : recList){
            if(gridPos.containsKey(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY())))){
                r.setOnMouseClicked(mouseEvent -> {
                    msg = "You casted Life Steal";
                    currentPane.getChildren().remove(back);
                    currentPane.getChildren().add(announcements.getPlayerDialogueBox().getDialogue(msg, Color.BLUE));
                    player.setHealth(player.getHealth() + 5);
                    gridPos.get(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY())))
                            .setHealth(gridPos.get(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY()))).getHealth() - 5);
                    clearAttackGrid(this.recList);
                });
            }
        }
    }

    public void directionMenu(){
        menuPane.getChildren().clear();
        currentPane.getChildren().remove(menuPane);

        ActionButton up = new ActionButton("UP");
        ActionButton left = new ActionButton("LEFT");
        ActionButton right = new ActionButton("RIGHT");
        ActionButton down = new ActionButton("DOWN");

        up.setOnMouseClicked(mouseEvent -> {
            createUpAttack();
            currentPane.getChildren().remove(menuPane);
        });

        left.setOnMouseClicked(mouseEvent -> {
            createLeftAttack();
            currentPane.getChildren().remove(menuPane);
        });

        right.setOnMouseClicked(mouseEvent -> {
            createRightAttack();
            currentPane.getChildren().remove(menuPane);
        });

        down.setOnMouseClicked(mouseEvent -> {
            createDownAttack();
            currentPane.getChildren().remove(menuPane);
        });

        up.setTranslateY(-128);
        left.setTranslateY(-64);
        right.setTranslateY(0);
        down.setTranslateY(64);

        menuPane.getChildren().addAll(up,left,right,down);
        currentPane.getChildren().add(menuPane);
    }

    public void fireDirection(){
        menuPane.getChildren().clear();
        currentPane.getChildren().remove(menuPane);

        ActionButton up = new ActionButton("UP");
        ActionButton left = new ActionButton("LEFT");
        ActionButton right = new ActionButton("RIGHT");
        ActionButton down = new ActionButton("DOWN");

        up.setOnMouseClicked(mouseEvent -> {
            createUpCone();
            currentPane.getChildren().remove(menuPane);
        });

        left.setOnMouseClicked(mouseEvent -> {
            createLeftCone();
            currentPane.getChildren().remove(menuPane);
        });

        right.setOnMouseClicked(mouseEvent -> {
            createRightCone();
            currentPane.getChildren().remove(menuPane);
        });

        down.setOnMouseClicked(mouseEvent -> {
            createDownCone();
            currentPane.getChildren().remove(menuPane);
        });

        up.setTranslateY(-128);
        left.setTranslateY(-64);
        right.setTranslateY(0);
        down.setTranslateY(64);

        menuPane.getChildren().addAll(up,left,right,down);
        currentPane.getChildren().add(menuPane);
    }

    private void createUpAttack(){
        recList = new ArrayList<>();
        int x = player.getTokenX();
        int y = player.getTokenY() - 64;

        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);

            recList.add(r);
        }

        for(Rectangle r : recList){
            r.setLayoutX(x);
            r.setLayoutY(y);
            y -= 64;
            setLightingListeners(r);
            currentPane.getChildren().add(r);
        }
    }

    private void createLeftAttack(){
        recList = new ArrayList<>();
        int x = player.getTokenX() - 64;
        int y = player.getTokenY();

        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);

            recList.add(r);
        }

        for(Rectangle r : recList){
            r.setLayoutX(x);
            r.setLayoutY(y);
            x -= 64;
            setLightingListeners(r);
            currentPane.getChildren().add(r);
        }
    }

    private void createRightAttack(){
        recList = new ArrayList<>();
        int x = player.getTokenX() + 64;
        int y = player.getTokenY();

        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);

            recList.add(r);
        }

        for(Rectangle r : recList){
            r.setLayoutX(x);
            r.setLayoutY(y);
            x += 64;
            setLightingListeners(r);
            currentPane.getChildren().add(r);
        }
    }

    private void createDownAttack(){
        recList = new ArrayList<>();
        int x = player.getTokenX();
        int y = player.getTokenY() + 64;

        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);

            recList.add(r);
        }

        for(Rectangle r : recList){
            r.setLayoutX(x);
            r.setLayoutY(y);
            y += 64;
            setLightingListeners(r);
            currentPane.getChildren().add(r);
        }
    }

    private void createUpCone(){
        recList = new ArrayList<>();
        int x = player.getTokenX() - 128;
        int y = player.getTokenY() - 128;

        for(int i = 0; i < 8; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);

            recList.add(r);
        }

        for(int i = 0; i < 5; i++){
            recList.get(i).setLayoutX(x);
            recList.get(i).setLayoutY(y);
            x += 64;
            setFireListeners(recList.get(i));
            currentPane.getChildren().add(recList.get(i));
        }

        x = player.getTokenX() - 64;
        y = player.getTokenY() -64;

        for(int j = 5; j < recList.size(); j++){
            recList.get(j).setLayoutX(x);
            recList.get(j).setLayoutY(y);
            x += 64;
            setFireListeners(recList.get(j));
            currentPane.getChildren().add(recList.get(j));
        }
    }

    private void createLeftCone(){
        recList = new ArrayList<>();
        int x = player.getTokenX() - 128;
        int y = player.getTokenY() - 128;

        for(int i = 0; i < 8; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);

            recList.add(r);
        }

        for(int i = 0; i < 5; i++){
            recList.get(i).setLayoutX(x);
            recList.get(i).setLayoutY(y);
            y += 64;
            setFireListeners(recList.get(i));
            currentPane.getChildren().add(recList.get(i));
        }

        x = player.getTokenX() - 64;
        y = player.getTokenY() -64;

        for(int j = 5; j < recList.size(); j++){
            recList.get(j).setLayoutX(x);
            recList.get(j).setLayoutY(y);
            y += 64;
            setFireListeners(recList.get(j));
            currentPane.getChildren().add(recList.get(j));
        }

        back.setLayoutX(player.getTokenX() + 64);
        back.setLayoutY(player.getTokenY() - 64);
    }

    private void createRightCone(){
        recList = new ArrayList<>();
        int x = player.getTokenX() + 128;
        int y = player.getTokenY() + 128;

        for(int i = 0; i < 8; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);

            recList.add(r);
        }

        for(int i = 0; i < 5; i++){
            recList.get(i).setLayoutX(x);
            recList.get(i).setLayoutY(y);
            y -= 64;
            setFireListeners(recList.get(i));
            currentPane.getChildren().add(recList.get(i));
        }

        x = player.getTokenX() + 64;
        y = player.getTokenY() + 64;

        for(int j = 5; j < recList.size(); j++){
            recList.get(j).setLayoutX(x);
            recList.get(j).setLayoutY(y);
            y -= 64;
            setFireListeners(recList.get(j));
            currentPane.getChildren().add(recList.get(j));
        }
    }

    private void createDownCone(){
        recList = new ArrayList<>();
        int x = player.getTokenX() - 128;
        int y = player.getTokenY() + 128;

        for(int i = 0; i < 8; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);

            recList.add(r);
        }

        for(int i = 0; i < 5; i++){
            recList.get(i).setLayoutX(x);
            recList.get(i).setLayoutY(y);
            x += 64;
            setFireListeners(recList.get(i));
            currentPane.getChildren().add(recList.get(i));
        }

        x = player.getTokenX() - 64;
        y = player.getTokenY() + 64;

        for(int j = 5; j < recList.size(); j++){
            recList.get(j).setLayoutX(x);
            recList.get(j).setLayoutY(y);
            x += 64;
            setFireListeners(recList.get(j));
            currentPane.getChildren().add(recList.get(j));
        }

        back.setLayoutY(player.getTokenY());
    }

    private void setFireListeners(Rectangle r){
        r.setOnMouseClicked(mouseEvent -> {
            msg = "You casted Dragon's Breath";
            currentPane.getChildren().remove(back);
            currentPane.getChildren().add(announcements.getPlayerDialogueBox().getDialogue(msg, Color.BLUE));
            player.setMagic(player.getMagic() - 10);
            fireAttack();
            clearAttackGrid(this.recList);
        });
    }

    private void setLightingListeners(Rectangle r){
        r.setOnMouseClicked(mouseEvent -> {
            msg = "You casted Lighting bolt";
            currentPane.getChildren().remove(back);
            currentPane.getChildren().add(announcements.getPlayerDialogueBox().getDialogue(msg, Color.BLUE));
            lightingAttack();
            clearAttackGrid(this.recList);
        });
    }

    private void fireAttack(){
        for(Rectangle r : recList){
            if(gridPos.containsKey(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY())))){
                gridPos.get(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY())))
                        .setHealth(gridPos.get(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY()))).getHealth() - 4);
            }
        }

        player.getInventory().put(ITEMS.FIRE_SPELL, player.getInventory().get(ITEMS.FIRE_SPELL) - 1);
        if(player.getInventory().get(ITEMS.FIRE_SPELL) <= 0){
            player.getInventory().remove(ITEMS.FIRE_SPELL);
        }
    }

    private void lightingAttack(){
        for(Rectangle r : recList){
            if(gridPos.containsKey(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY())))){
                gridPos.get(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY())))
                        .setHealth(gridPos.get(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY()))).getHealth() - 7);
            }
        }

        player.getInventory().put(ITEMS.LIGHTING_SPELL, player.getInventory().get(ITEMS.LIGHTING_SPELL) - 1);
        if(player.getInventory().get(ITEMS.LIGHTING_SPELL) <= 0){
            player.getInventory().remove(ITEMS.LIGHTING_SPELL);
        }
    }

    public void setGridPos(HashMap<ArrayList<Integer>, GameEntity> gridPos) {
        this.gridPos = gridPos;
    }

    private void clearAttackGrid(ArrayList<Rectangle> recList){
        for (Rectangle rectangle : recList) {
            currentPane.getChildren().remove(rectangle);
        }
    }
}
