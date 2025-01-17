package gameLogic;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import model.GameEntity;
import model.Goblin;
import model.Human;

import java.util.*;


public class Grid {
    protected ArrayList<Rectangle> recList;
    ArrayList<Rectangle> moveGrid = new ArrayList<>();
    protected HashMap<ArrayList<Integer>, GameEntity> gridPos;

    protected Human player;
    protected AnchorPane currentPane;
    protected GameAnnouncement announcements;

//    Composite Grids
    PlayerGridLogic playerGridLogic;
    GoblinGridLogic goblinGridLogic;
    ItemGridLogic itemGridLogic;

    public Grid() {
    }

    public Grid(Human player,
                ArrayList<Rectangle> recList,
                GameAnnouncement announcements,
                AnchorPane currentPane,
                HashMap<ArrayList<Integer>, GameEntity> gridPos) {
        this.player = player;
        this.recList = recList;
        this.currentPane = currentPane;
        this.announcements = announcements;


        this.playerGridLogic = new PlayerGridLogic(this.player, this.currentPane);
        this.goblinGridLogic = new GoblinGridLogic(this.player, this.currentPane, this.announcements);
        this.itemGridLogic = new ItemGridLogic(this.player, this.currentPane, this.recList, this.announcements);
        this.gridPos = gridPos;
        this.goblinGridLogic.setGrid(gridPos);
        this.playerGridLogic.setGridPos(gridPos);
        this.itemGridLogic.setGridPos(gridPos);
        addPlayerToGridPos();
    }

    public void addPlayerToGridPos() {
        this.gridPos.put(new ArrayList<>(Arrays.asList(player.getTokenX(), player.getTokenY())), player);
    }

    public void isSpaceTaken(int x, int y) {
        this.playerGridLogic.isSpaceTaken(player.getTokenX(), player.getTokenY());
        this.goblinGridLogic.isSpaceTaken(x, y);
    }

    public void getPossiblePlayerMoves(UI uiLogic) {
        this.moveGrid.clear();
        isSpaceTaken(player.getTokenX(), player.getTokenY());
        this.playerGridLogic.getPossibleMoves(this.moveGrid, uiLogic);
    }

    public void createAttackGrid(){
        this.playerGridLogic.createAttackOptions(this.recList);
        this.goblinGridLogic.setAttackGrid(recList);
    }

    public void initAttackGrid() {
        this.playerGridLogic.createAttackOptions(this.recList);
        clearAttackGrid();
    }


    public void clearAttackGrid() {
        this.playerGridLogic.clearAttackGrid(this.recList);
    }

    public void clearMovementGrid() {
        this.playerGridLogic.clearMovementGrid(this.moveGrid);
        this.moveGrid.clear();
    }

    public void setListOfGoblins(ArrayList<Goblin> listOfGoblins) {
        this.goblinGridLogic.setListOfGoblins(listOfGoblins);
    }

    public void setGoblinPos() {
        this.goblinGridLogic.setGoblinPos();
    }

    public void getGoblinsGridPos() {
        this.goblinGridLogic.setGoblinsGridPos(this.gridPos);
    }

    public PlayerGridLogic getPlayerGridLogic() {
        return playerGridLogic;
    }

    public GoblinGridLogic getGoblinGridLogic() {
        return goblinGridLogic;
    }

    public void clearRecList() {
        this.recList.clear();
    }

    public HashMap<ArrayList<Integer>, GameEntity> getGridPos() {
        return gridPos;
    }

    public Human getPlayer() {
        return player;
    }
}
