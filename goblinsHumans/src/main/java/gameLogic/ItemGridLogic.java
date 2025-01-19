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
import static model.ITEMS.LIFE_STEAL;

public class ItemGridLogic {

    private final StackPane directionPane = new StackPane();
    private final AnchorPane currentPane;
    private ArrayList<Rectangle> recList;
    private final Human player;
    private final GameAnnouncement announcements;
    private HashMap<ArrayList<Integer>, GameEntity> gridPos;
    private String msg;

    public ItemGridLogic(Human player,
                         AnchorPane currentPane,
                         ArrayList<Rectangle> recList,
                         GameAnnouncement announcements) {
        this.player = player;
        this.currentPane = currentPane;
        this.recList = recList;
        this.announcements = announcements;
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
                    player.setMagic(player.getMagic() - 5);
                    player.getInventory().put(LIFE_STEAL, player.getInventory().get(LIFE_STEAL) - 1);
                });
            }
        }
    }

    public void spellDirection(String attackName) {
        ActionButton up = new ActionButton("UP");
        ActionButton left = new ActionButton("LEFT");
        ActionButton right = new ActionButton("RIGHT");
        ActionButton down = new ActionButton("DOWN");

        up.setOnMouseClicked(mouseEvent -> {
            if(attackName.equals("fire")) {
                createCone("up");
            } else {
                createLightingDirectionAttack("up");
            }
            currentPane.getChildren().remove(directionPane);
        });

        left.setOnMouseClicked(mouseEvent -> {
            if(attackName.equals("fire")) {
                createCone("left");
            } else {
                createLightingDirectionAttack("left");
            }
            currentPane.getChildren().remove(directionPane);
        });

        right.setOnMouseClicked(mouseEvent -> {
            if(attackName.equals("fire")) {
                createCone("right");
            } else {
                createLightingDirectionAttack("right");
            }
            currentPane.getChildren().remove(directionPane);
        });

        down.setOnMouseClicked(mouseEvent -> {
            if(attackName.equals("fire")) {
                createCone("down");
            } else {
                createLightingDirectionAttack("down");
            }
            currentPane.getChildren().remove(directionPane);
        });

        back.setLayoutX(896);
        back.setLayoutY(704);

        up.setTranslateX(player.getTokenX() + 64);
        left.setTranslateX(player.getTokenX() + 64);
        right.setTranslateX(player.getTokenX() + 64);
        down.setTranslateX(player.getTokenX() + 64);

        up.setTranslateY(player.getTokenY());
        left.setTranslateY(player.getTokenY() + 64);
        right.setTranslateY(player.getTokenY() + 128);
        down.setTranslateY(player.getTokenY() + 192);

        directionPane.getChildren().addAll(up,left,right,down);
        currentPane.getChildren().add(directionPane);
    }

    public void clearDirectionMenu() {
        clearAttackGrid(this.recList);
        directionPane.getChildren().clear();
        currentPane.getChildren().remove(directionPane);
    }

    private void createLightingDirectionAttack(String direction) {
        boolean isVerticalAxis = direction.equals("up") || direction.equals("down");
        int tileDirection = direction.equals("left") || direction.equals("up") ? -64 : 64;
        recList = new ArrayList<>();
        int x = player.getTokenX() + (direction.equals("left") || direction.equals("right") ? tileDirection : 0);
        int y = player.getTokenY() + (isVerticalAxis ? tileDirection : 0);

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
            if(isVerticalAxis) {
                y += tileDirection;
            } else {
                x += tileDirection;
            }
            setListeners(r, "lighting");
            currentPane.getChildren().add(r);
        }
    }

    private void createCone(String direction) {
        boolean isVerticalAxis = direction.equals("up") || direction.equals("down");
        boolean isRightOrDown = direction.equals("right") || direction.equals("down");
        recList = new ArrayList<>();
        int x = player.getTokenX() + (direction.equals("right") ? 128 : -128);
        int y = player.getTokenY() + (isRightOrDown ? 128 : -128);

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
            if (isVerticalAxis) {
                x += 64;
            } else {
                y += direction.equals("left") ? 64 : -64;
            }
            setListeners(recList.get(i), "fire");
            currentPane.getChildren().add(recList.get(i));
        }

        x = player.getTokenX() + (direction.equals("right") ? 64 : -64);
        y = player.getTokenY() + (isRightOrDown ? 64 : -64);

        for(int j = 5; j < recList.size(); j++){
            recList.get(j).setLayoutX(x);
            recList.get(j).setLayoutY(y);
            if(isVerticalAxis) {
                x += 64;
            } else {
                y += direction.equals("left") ? 64 : -64;
            }
            setListeners(recList.get(j), "fire");
            currentPane.getChildren().add(recList.get(j));
        }

    }

    private void setListeners(Rectangle r, String type) {
        boolean isFire = type.equals("fire");
        r.setOnMouseClicked(mouseEvent -> {
            msg = isFire ? "You casted Dragon's Breath" : "You casted Lighting Bolt";
            currentPane.getChildren().remove(back);
            currentPane.getChildren().add(announcements.getPlayerDialogueBox().getDialogue(msg, Color.BLUE));
            player.setMagic(player.getMagic() - 10);
            if(isFire) {
                spellAttack(ITEMS.FIRE_SPELL);
            } else {
                spellAttack(ITEMS.LIGHTING_SPELL);
            }
            clearDirectionMenu();
        });
    }

    private void spellAttack(ITEMS item){
        int damage = item == ITEMS.LIGHTING_SPELL ? 7 : 4;

        for(Rectangle r : recList){
            if(gridPos.containsKey(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY())))){
                gridPos.get(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY())))
                        .setHealth(gridPos.get(new ArrayList<>(Arrays.asList((int) r.getLayoutX(),(int) r.getLayoutY()))).getHealth() - damage);
            }
        }

        player.getInventory().put(item, player.getInventory().get(item) - 1);
        if(player.getInventory().get(item) <= 0){
            player.getInventory().remove(item);
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
