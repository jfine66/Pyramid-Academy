package gameLogic;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import model.*;
import view.SceneController;

import java.util.*;

import static view.SceneController.levelOne;

public class GameLogic {
    public final Human player = SceneController.getPlayer();
    private AnchorPane currentPane;

    public static final ActionButton back = new ActionButton("BACK");

    ArrayList<Rectangle> recList = new ArrayList<>();
    UI uiLogic;
    Grid grid;
    PlayerGridLogic playerGridLogic;
    GoblinGridLogic goblinGridLogic;
    ItemGridLogic itemGridLogic;


    public GameLogic(AnchorPane pane){
        this.currentPane = pane;
        GameAnnouncement announcements = new GameAnnouncement(currentPane);
        HashMap<ArrayList<Integer>, GameEntity> gridPos = new HashMap<>(levelOne.getGridPos());
        player.playerStartPos(this.currentPane);
        this.grid = new Grid(player, recList, announcements, currentPane, gridPos);

        this.uiLogic = new UI(player,
                currentPane,
                announcements,
                grid,
                playerGridLogic,
                goblinGridLogic,
                itemGridLogic
        );
    }

    public ActionButton getBack() {
        return back;
    }

    public void setCurrentPane(AnchorPane pane){
        this.currentPane = pane;
    }

    public void gameStart(){
        grid.setGoblinPos();
        grid.getGoblinsGridPos();
        uiLogic.initAttackGrid();
        uiLogic.playerTurn();
    }

    public void removePlayer(){
        currentPane.getChildren().remove(player);
    }

    /* NEW CODE ADDED  */
    public void closeMenu() {
        uiLogic.closeMenu();
    }

    public void createAttackOptions(){
        grid.createAttackGrid();
    }

    public void setLifeStealListeners() {
        itemGridLogic.setLifeStealListeners();
    }

    public void fireDirection() {
        itemGridLogic.fireDirection();
    }

    public void directionMenu() {
        itemGridLogic.directionMenu();
    }

    public void setListOfGoblins(ArrayList<Goblin> listOfGoblins) {
        grid.setListOfGoblins(listOfGoblins);
    }

    /* NEW CODE ADDED */
}
