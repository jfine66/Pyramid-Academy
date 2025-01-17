package gameLogic;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GameEntity;
import model.Human;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static gameLogic.GameLogic.back;

public class PlayerGridLogic {

    private final Human player;
    private final AnchorPane currentPane;
    private HashMap<ArrayList<Integer>, GameEntity> gridPos;

    public PlayerGridLogic(Human player,
                           AnchorPane currentPane) {
        this.player = player;
        this.currentPane = currentPane;
    }

    public void createAttackOptions(ArrayList<Rectangle> rectangles){
        ArrayList<Rectangle> recList = createAttackGrid(rectangles);
        int x = player.getTokenX();
        int y = player.getTokenY();

        back.setLayoutX(x - 128);
        back.setLayoutY(y + 64);
        currentPane.getChildren().add(back);

        recList.get(0).setLayoutX(x - 64);
        recList.get(0).setLayoutY(y);
        recList.get(1).setLayoutX(x + 64);
        recList.get(1).setLayoutY(y);

        recList.get(2).setLayoutX(x);
        recList.get(2).setLayoutY(y + 64);
        recList.get(3).setLayoutX(x);
        recList.get(3).setLayoutY(y - 64);


        for(int l = 0; l < 4; l++){
            currentPane.getChildren().add(recList.get(l));
        }

    }

    public void getPossibleMoves(ArrayList<Rectangle> moveGrid,
                                 UI ui){

        int x = player.getTokenX();
        int y = player.getTokenY();

        //MOVES ABOVE THE PLAYER
        for(int i = y - 64; i > y - 192; i -= 64){
            if(isSpaceTaken(x, i)) {
                break;
            }
            Rectangle r = new Rectangle(x, i, 64,64);
            r.setOpacity(0.6);
            r.setFill(Color.BLUE);
            r.setStroke(Color.WHITE);
            r.setStrokeWidth(1);
            moveGrid.add(r);
        }

        //MOVES BELOW PLAYER
        for(int i = y + 64; i < y + 192; i += 64){
            if(isSpaceTaken(x, i))  {
                break;
            }
            Rectangle r = new Rectangle(x, i, 64,64);
            r.setOpacity(0.6);
            r.setFill(Color.BLUE);
            r.setStroke(Color.WHITE);

            moveGrid.add(r);
        }

        //MOVES TO LEFT OF PLAYER
        for(int i = x - 64; i > x - 192; i -= 64){
            if(isSpaceTaken(i, y))  {
                break;
            }
            Rectangle r = new Rectangle(i, y, 64,64);
            r.setOpacity(0.6);
            r.setFill(Color.BLUE);
            r.setStroke(Color.WHITE);
            r.setStrokeWidth(1);
            moveGrid.add(r);
        }

        //MOVES TO RIGHT OF PLAYER
        for(int i = x + 64; i < x + 192; i += 64){
            if(isSpaceTaken(i, y))  {
                break;
            }
            Rectangle r = new Rectangle(i, y, 64,64);
            r.setOpacity(0.6);
            r.setFill(Color.BLUE);
            r.setStroke(Color.WHITE);
            r.setStrokeWidth(1);
            moveGrid.add(r);
        }

//                Start Right Bottom to Top Left diagonal squares
        y -= 64;
        for(int i = x - 64; i < x + 128; i += 64){
            if(isSpaceTaken(i, y)) {
                y += 64;
                continue;
            }
            Rectangle r = new Rectangle(i, y, 64,64);
            r.setOpacity(0.6);
            r.setFill(Color.BLUE);
            r.setStroke(Color.WHITE);
            r.setStrokeWidth(1);
            moveGrid.add(r);
            y += 64;
        }


//        Start Left Bottom to Top Right diagonal squares
        y = player.getTokenY();
        y += 64;

        for(int i = x - 64; i < x + 128; i += 64){
            if(isSpaceTaken(i, y)) {
                y -= 64;
                continue;
            }
            Rectangle r = new Rectangle(i, y, 64,64);
            r.setOpacity(0.6);
            r.setFill(Color.BLUE);
            r.setStroke(Color.WHITE);
            r.setStrokeWidth(1);
            moveGrid.add(r);
            y -= 64;
        }
        createMovementListeners(moveGrid, ui);
    }

    private void createMovementListeners(ArrayList<Rectangle> moveGrid,
                                         UI uiLogic){

        for(Rectangle r : moveGrid){
            r.setOnMouseEntered(mouseEvent -> r.setStroke(Color.WHITE));
            r.setOnMouseExited(mouseEvent -> r.setStroke(Color.WHITESMOKE));

            r.setOnMouseClicked(mouseEvent -> {
                player.setTokenPos((int) r.getX(), (int) r.getY());
                gridPos.values().remove(player);
                gridPos.put(new ArrayList<>(Arrays.asList(player.getTokenX(), player.getTokenY())), player);
                player.setHasMoved(true);
                clearMovementGrid(moveGrid);
                currentPane.getChildren().remove(back);
                uiLogic.openMenu();
            });

            currentPane.getChildren().add(r);
        }
    }

    public void clearMovementGrid(ArrayList<Rectangle> moveGrid) {
        for (Rectangle rectangle : moveGrid) {
            currentPane.getChildren().remove(rectangle);
        }
    }

    public void clearAttackGrid(ArrayList<Rectangle> recList){
        for (Rectangle rectangle : recList) {
            currentPane.getChildren().remove(rectangle);
        }
    }

    private ArrayList<Rectangle> createAttackGrid(ArrayList<Rectangle> recList){
        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);
            recList.add(r);
        }

        return recList;
    }

    public boolean isSpaceTaken(int x, int y) {
        return gridPos.containsKey(new ArrayList<>(Arrays.asList(x,y)));
    }

    public void setGridPos(HashMap<ArrayList<Integer>, GameEntity> gridPos) {
        this.gridPos = gridPos;
    }

}
