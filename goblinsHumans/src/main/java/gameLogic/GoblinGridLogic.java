package gameLogic;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GameEntity;
import model.Goblin;
import model.Human;
import model.ITEMS;

import java.util.*;

import static gameLogic.GameLogic.back;

public class GoblinGridLogic{
    private ArrayList<Goblin> listOfGoblins = new ArrayList<>();
    ArrayList<Goblin> deadGoblins = new ArrayList<>();
    // This needs removed
    HashMap<ArrayList<Integer>, GameEntity> gridPos;
    private final GameAnnouncement announcements;

    private final Human player;

    String msg;
    String droppedMsg;
    protected AnchorPane currentPane;
    private final MathLogic mathLogic = new MathLogic();

    public GoblinGridLogic(Human player,
                           AnchorPane currentPane,
                           GameAnnouncement announcements) {
        this.player = player;
        this.currentPane = currentPane;
        this.announcements = announcements;
    }

    public void setListOfGoblins(ArrayList<Goblin> list) {
        this.listOfGoblins = list;
    }

    public void setGoblinPos(){
        for(Goblin goblin : listOfGoblins){
            goblin.setTokenPos(mathLogic.getRandomX(), mathLogic.getRandomY());
            currentPane.getChildren().add(goblin.getToken());
        }
    }

    public void setGoblinsGridPos(HashMap<ArrayList<Integer>, GameEntity> gridPos){
        for(Goblin goblin : listOfGoblins){
            gridPos.put(new ArrayList<>(Arrays.asList(goblin.getTokenX(), goblin.getTokenY())), goblin);
        }
    }

    public Goblin getCurrentGoblin(int x,
                                   int y,
                                   HashMap<ArrayList<Integer>, GameEntity> gridPos) {
        return (Goblin) gridPos.get(new ArrayList<>(Arrays.asList(x,y)));
    }

    public void getDead() {
        for(Goblin goblin : listOfGoblins){
            if(goblin.getHealth() <= 0){
                deadGoblins.add(goblin);
            }
        }
        removeDead();
    }

    private void removeDead(){
        for(Goblin goblin : deadGoblins){
            removeDeadGoblin(goblin);
        }
    }

    public void removeDeadGoblin(Goblin goblin){
        ITEMS drop = goblin.didDrop();
        if(drop != null){
            droppedMsg = "\nGoblin dropped " + drop + " \nit has been added to your inventory";
            player.addToInventory(drop);
        }

        gridPos.remove(new ArrayList<>(Arrays.asList(goblin.getTokenX(), goblin.getTokenY())));
        currentPane.getChildren().remove(goblin.getToken());
        goblin.setTokenPos(0,0);
        listOfGoblins.remove(goblin);
    }

    public boolean anyGoblinsAlive(){
        boolean isAlive = false;

        for(Goblin goblin : listOfGoblins){
            isAlive = goblin.getHealth() > 0;
            break;
        }

        return isAlive;
    }

    public ArrayList<Goblin> getListOfGoblins() {
        return listOfGoblins;
    }

    private void getGoblinsMoves(Goblin goblin,
                                 HashMap<ArrayList<Integer>, GameEntity> gridPos){
        int goblinX = goblin.getTokenX();
        int goblinY = goblin.getTokenY();
        int playerX = player.getTokenX();
        int playerY = player.getTokenY();
        //NEED TO ADD A WAY TO MOVE UP-LEFT, UP-RIGHT
        int closetX = goblinX;
        int closetY = goblinY;

        if(goblinX > playerX){
            closetX = goblinX - 64;
        } else if(goblinX < playerX){
            closetX = goblinX + 64;
        }

        if(goblinY > playerY){
            closetY = goblinY - 64;
        } else if(goblinY < playerY){
            closetY = goblinY + 64;
        }


        if(!isSpaceTaken(closetX, closetY)) {
            gridPos.values().remove(goblin);
            gridPos.put(new ArrayList<>(Arrays.asList(closetX, closetY)), goblin);
            goblin.setTokenPos(closetX, closetY);
        }

    }

    public void goblinAttack(Goblin goblin,
                             UI ui,
                             ArrayList<Rectangle> recList){
        setGoblinAttackGrid(goblin, ui, recList);
    }

    public void goblinTurn(Goblin goblin,
                           int time,
                           UI ui,
                           ArrayList<Rectangle> recList,
                           HashMap<ArrayList<Integer>, GameEntity> gridPos){
        Timer timer = new Timer();
        //ACTION GOBLINS TAKE ON THEIR TURN
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> getGoblinsMoves(goblin, gridPos));
            }
        }, time);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> goblinAttack(goblin, ui, recList));
            }
        }, time);
    }

    private void setGoblinAttackGrid(Goblin goblin, UI uiLogic, ArrayList<Rectangle> recList){
        announcements.getGoblinDialogueBox().getDialogue(msg, Color.RED).setLayoutX(320);
        announcements.getGoblinDialogueBox().getDialogue(msg, Color.RED).setLayoutY(256);

        Timer timer = new Timer();
        int x = player.getTokenX();
        int y = player.getTokenY();

        int goblinX = goblin.getTokenX();
        int goblinY = goblin.getTokenY();

        recList.get(0).setLayoutX(goblinX);
        recList.get(0).setLayoutY(goblinY + 64);

        recList.get(1).setLayoutX(goblinX + 64);
        recList.get(1).setLayoutY(goblinY);

        recList.get(2).setLayoutX(goblinX);
        recList.get(2).setLayoutY(goblinY - 64);

        recList.get(3).setLayoutX(goblinX - 64);
        recList.get(3).setLayoutY(goblinY);

        for (Rectangle rectangle : recList) {
            if (x == rectangle.getLayoutX() && y == rectangle.getLayoutY()) {
                String temp = goblin.toHit(player);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            msg = temp;
                            currentPane.getChildren().add(announcements.getGoblinDialogueBox().getDialogue(msg, Color.RED));
                        });
                    }
                }, 1000);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> currentPane.getChildren().remove(announcements.getGoblinDialogueBox().getDialogue(msg, Color.RED)));
                    }
                }, 1800);

                if (player.getHealth() <= 0) {
                    uiLogic.gameOver();
                    break;
                }
            }
        }
    }

    public void setAttackGrid(ArrayList<Rectangle> recList){
        // need goblins GridPos
        int x = player.getTokenX();
        int y = player.getTokenY();

        recList.get(0).setLayoutX(x - 64);
        recList.get(0).setLayoutY(y);
        recList.get(1).setLayoutX(x + 64);
        recList.get(1).setLayoutY(y);

        recList.get(2).setLayoutX(x);
        recList.get(2).setLayoutY(y + 64);
        recList.get(3).setLayoutX(x);
        recList.get(3).setLayoutY(y - 64);
        for(int l = 0; l < 4; l++){
            if(gridPos.containsKey(new ArrayList<>(Arrays.asList((int) recList.get(l).getLayoutX(),(int) recList.get(l).getLayoutY())))){
                setAttackListeners(recList.get(l),
                        this.getCurrentGoblin(
                                (int) recList.get(l).getLayoutX(),
                                (int) recList.get(l).getLayoutY(),
                                gridPos),
                                recList
                );
            }
        }
    }

    private void setAttackListeners(Rectangle r,
                                    Goblin goblin,
                                    ArrayList<Rectangle> recList){
        r.setOnMouseClicked(mouseEvent -> {
            msg = player.toHit(goblin);
            player.setHasAttacked(true);
            if(goblin.getHealth() <= 0) this.removeDeadGoblin(goblin);
            clearAttackGrid(recList);

            currentPane.getChildren().remove(back);

            currentPane.getChildren().add(announcements.getPlayerDialogueBox().getDialogue(msg, Color.BLUE));
        });
    }

    public void clearAttackGrid(ArrayList<Rectangle> recList){
        for (Rectangle rectangle : recList) {
            currentPane.getChildren().remove(rectangle);
        }
    }

    public boolean isSpaceTaken(int x, int y) {
        return gridPos.containsKey(new ArrayList<>(Arrays.asList(x,y)));
    }

    public void setGrid(HashMap<ArrayList<Integer>, GameEntity> gridPos) {
        this.gridPos = gridPos;
    }


}
