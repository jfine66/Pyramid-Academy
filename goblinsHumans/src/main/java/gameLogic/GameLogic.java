package gameLogic;


import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.ActionButton;
import model.Banner;
import model.Goblin;
import model.Human;
import view.SceneController;

import java.util.ArrayList;
import java.util.HashMap;


public class GameLogic {
    public static final Human player = SceneController.getPlayer();
    protected final Goblin testGoblin = new Goblin();
    private final Banner banner = new Banner();
    private final StackPane playerBanner = banner.getPlayerBanner();
    private final Banner gobBanner = new Banner();
    private final StackPane goblinBanner = gobBanner.getGoblinsBanner();
    private final Banner victoryDisplay = new Banner();
    private final StackPane victoryBanner = victoryDisplay.getVictoryBanner();
    private final Banner defeatDisplay = new Banner();
    private final StackPane defeatBanner = defeatDisplay.getDefeatBanner(mainMenuButton());
    private AnchorPane currentPane;

    Rectangle playerMenu = new Rectangle(0,0, 128, 192);
    StackPane menuPane = new StackPane();

    private boolean hasAttacked = false;
    private boolean hasMoved = false;
    private int numberOfGoblins = 1;
    ActionButton back = new ActionButton("BACK");

    ArrayList<Rectangle> recList = new ArrayList<>();
    ArrayList<Rectangle> moveGrid = new ArrayList<>();
    HashMap<Integer, String> gridXAxis = new HashMap<>();
    HashMap<Integer, String> gridYAxis = new HashMap<>();
    HashMap<Integer, String> goblinsXPos = new HashMap<>();
    HashMap<Integer, String> goblinsYPos = new HashMap<>();

    public GameLogic(AnchorPane pane){
        this.currentPane = pane;
        gameStart();
    }


    public void gameStart(){
        currentPane.getChildren().add(player.getToken());
        currentPane.getChildren().add(testGoblin.getToken());
        player.setTokenPos(512,384);
        testGoblin.setTokenPos(512,320);
        player.setHealth(5);
        testGoblin.setHealth(10);
        playerTurn();
    }

    private void fillAxis(){
        for(int x = 0; x < 961; x += 64){
            if(x == player.getTokenX() || x == testGoblin.getTokenX()){
                gridXAxis.put(x, "filled");
            } else{
                gridXAxis.put(x, "empty");
            }
        }

        for(int y = 0; y < 705; y += 64){
            if(y == player.getTokenY() || y == testGoblin.getTokenY()){
                gridYAxis.put(y, "filled");
            } else {
                gridYAxis.put(y, "empty");
            }
        }
    }

    private void getGoblinsPos(){
        for(int x = 0; x < 961; x += 64){
            if(x == testGoblin.getTokenX()){
                goblinsXPos.put(x, "filled");
            } else{
                goblinsXPos.put(x, "empty");
            }
        }

        for(int y = 0; y < 705; y += 64){
            if(y == testGoblin.getTokenY()){
                goblinsYPos.put(y, "filled");
            } else {
                goblinsYPos.put(y, "empty");
            }
        }
    }

    private void playerTurn(){
        if(player.getHealth() < 0){
            outComeBanner(defeatBanner);
            currentPane.getChildren().add(defeatBanner);
        }

        if(testGoblin.getHealth() < 0){
            currentPane.getChildren().remove(testGoblin.getToken());
            testGoblin.setTokenPos(0,0);
            numberOfGoblins--;
        }

        if(numberOfGoblins > 0){
            currentPane.getChildren().add(playerBanner);
            moveBanner(playerBanner);
            currentPane.getChildren().remove(goblinBanner);
            openMenu();
        } else {
            outComeBanner(victoryBanner);
            currentPane.getChildren().add(victoryBanner);
        }

    }

    private void goblinTurn(){
        currentPane.getChildren().remove(playerBanner);
        currentPane.getChildren().add(goblinBanner);
        moveBanner(goblinBanner);
        getGoblinsMoves();
        goblinAttack();

        playerTurn();
    }


    //BANNER ANIMATION
    private void moveBanner(StackPane banner){
        banner.setLayoutX(1024);
        banner.setLayoutY(180);

        TranslateTransition slideIn = new TranslateTransition();
        slideIn.setDuration(Duration.seconds(0.3));
        slideIn.setToX(-1024);

        PauseTransition pause = new PauseTransition(Duration.millis(800));

        TranslateTransition slideOut = new TranslateTransition();
        slideOut.setDuration(Duration.seconds(0.3));
        slideOut.setToX(-2048);

        SequentialTransition seqT = new SequentialTransition(banner, slideIn, pause, slideOut);

        seqT.play();
    }

    private void outComeBanner(StackPane banner){
        banner.setLayoutX(0);
        banner.setLayoutY(192);
    }

    //TURN MENU
    public void openMenu(){
        menuPane.getChildren().clear();
        setMenuPos();
        menuPane.getChildren().add(playerMenu);
        addButtons();
        currentPane.getChildren().add(menuPane);
    }

    private void closeMenu(){
        menuPane.getChildren().clear();
        currentPane.getChildren().remove(menuPane);
    }

    private void setMenuPos(){
        int x = GameLogic.player.getTokenX();
        int y = GameLogic.player.getTokenY();

        playerMenu.setFill(Color.BLUE);
        playerMenu.setOpacity(0.3);

        if(x > 256){
            menuPane.setLayoutX(x - 128);
            menuPane.setLayoutY(y);
        } else {
            menuPane.setLayoutX(x + 64);
            menuPane.setLayoutY(y);
        }
    }

    private void addButtons(){
        ActionButton attack = new ActionButton("ATTACK");
        ActionButton move = new ActionButton("MOVE");
        ActionButton item = new ActionButton("ITEMS");
        ActionButton endTurn = new ActionButton("END");

        back.setOnMouseClicked(mouseEvent -> {
            clearMovementGrid();
            clearAttackGrid();
            openMenu();
            currentPane.getChildren().remove(back);
        });

        attack.setOnMouseClicked(mouseEvent -> {
            if(hasAttacked){
                System.out.println("You have already attacked this turn!!!");
            } else{
                attackGrid();
                closeMenu();
            }
        });

        move.setOnMouseClicked(mouseEvent -> {
            if(hasMoved){
                System.out.println("You have already moved this turn!!!");
            } else{
                closeMenu();
                getPossibleMoves();
                back.setLayoutX(player.getTokenX() - 64);
                back.setLayoutY(player.getTokenY() + 128);
                currentPane.getChildren().add(back);
            }
        });

        endTurn.setOnMouseClicked(mouseEvent -> {
            currentPane.getChildren().remove(menuPane);
            hasAttacked = false;
            hasMoved = false;
            goblinTurn();
        });

        attack.setTranslateY(-64);
        item.setTranslateY(64);
        endTurn.setTranslateY(128);

        menuPane.getChildren().add(attack);
        menuPane.getChildren().add(move);
        menuPane.getChildren().add(item);
        menuPane.getChildren().add(endTurn);
    }

    private ActionButton mainMenuButton(){
        ActionButton toCamp = new ActionButton("MAIN MENU");
        toCamp.setPrefWidth(192);
        toCamp.setTranslateY(64);
        toCamp.setOnMouseClicked(mouseEvent -> {
            currentPane.getChildren().clear();
            SceneController.toMainMenu();
        });

        return toCamp;
    }

    //ATTACK MENU
    public void attackGrid(){
        createAttackGrid();
        setAttackGrid();
    }

    private void createAttackGrid(){
        recList = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.2);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);
            recList.add(r);
        }
    }

    private void setAttackGrid(){
        getGoblinsPos();
        int x = player.getTokenX();
        int y = player.getTokenY();

        int upAndLeft = -64;
        int rightAndDown = 64;

        back.setLayoutX(x - 128);
        back.setLayoutY(y + rightAndDown);
        currentPane.getChildren().add(back);

        for(int i = 0; i < 4; i++){
            switch (i){
                case 0:
                    recList.get(i).setLayoutX(x);
                    recList.get(i).setLayoutY(y + upAndLeft);
                    if(goblinsXPos.get( (int) recList.get(i).getLayoutX()).equals("filled") && goblinsYPos.get((int) recList.get(i).getLayoutY()).equals("filled"))
                        setAttackListeners(recList.get(i), getCurrentGoblin(), back);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 1:
                    recList.get(i).setLayoutX(x + rightAndDown);
                    recList.get(i).setLayoutY(y);
                    if(goblinsXPos.get( (int) recList.get(i).getLayoutX()).equals("filled") && goblinsYPos.get((int) recList.get(i).getLayoutY()).equals("filled")) setAttackListeners(recList.get(i), getCurrentGoblin(), back);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 2:
                    recList.get(i).setLayoutX(x);
                    recList.get(i).setLayoutY(rightAndDown + y);
                    if(goblinsXPos.get((int)recList.get(i).getLayoutX()).equals("filled") && goblinsYPos.get((int)recList.get(i).getLayoutY()).equals("filled")) setAttackListeners(recList.get(i), getCurrentGoblin(), back);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 3:
                    recList.get(i).setLayoutX(upAndLeft + x);
                    recList.get(i).setLayoutY(y);
                    if(goblinsXPos.get((int)recList.get(i).getLayoutX()).equals("filled") && goblinsYPos.get((int)recList.get(i).getLayoutY()).equals("filled")) setAttackListeners(recList.get(i), getCurrentGoblin(), back);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                default:
            }
        }
    }

    private void setAttackListeners(Rectangle r, Goblin goblin, ActionButton back){
        r.setOnMouseClicked(mouseEvent -> {
            player.toHit(goblin);
            hasAttacked = true;
            clearAttackGrid();
            currentPane.getChildren().remove(back);
            openMenu();
        });
    }

    private Goblin getCurrentGoblin(){
        for(int i = 0; i < 961; i += 64){
            for(int j = 0; j < 705; j += 64){
                if(i == testGoblin.getTokenX() && j == testGoblin.getTokenY()){
                    return testGoblin;
                }
            }
        }

        return null;
    }

    private void clearAttackGrid(){
        for (Rectangle rectangle : recList) {
            currentPane.getChildren().remove(rectangle);
        }
    }

    private void clearMovementGrid(){
        for (Rectangle rectangle : moveGrid) {
            currentPane.getChildren().remove(rectangle);
        }
        moveGrid = new ArrayList<>();
    }

    //PLAYER MOVE GRID
    public void getPossibleMoves(){
        fillAxis();
        int x = player.getTokenX();
        int y = player.getTokenY();

        int startX = x - 64;
        int startY = y - 64;
        int maxRight = x + 128;
        int maxDown = y + 128;

        for(int i = startX; i < maxRight; i += 64){
            for(int j = startY; j < maxDown; j += 64){
                if(gridXAxis.get(i).equals("filled") && gridYAxis.get(j).equals("filled")){
                    continue;
                }
                Rectangle r = new Rectangle(i, j, 64,64);
                r.setOpacity(0.2);
                r.setFill(Color.BLUE);
                r.setStroke(Color.RED);
                moveGrid.add(r);
            }
        }

        for(Rectangle r : moveGrid){
            r.setOnMouseEntered(mouseEvent -> r.setStroke(Color.WHITE));
            r.setOnMouseExited(mouseEvent -> r.setStroke(Color.RED));

            r.setOnMouseClicked(mouseEvent -> {
                player.setTokenPos((int) r.getX(), (int) r.getY());
                hasMoved = true;
                clearMovementGrid();
                currentPane.getChildren().remove(back);
                openMenu();
            });

            currentPane.getChildren().add(r);
        }
    }

    //GOBLINS CONTROLLER
    private void getGoblinsMoves(){
        int goblinX = testGoblin.getTokenX();
        int goblinY = testGoblin.getTokenY();
        int playerX = player.getTokenX();
        int playerY = player.getTokenY();

        int shortestX = playerX - goblinX;
        int shortestY = playerY - goblinY;
        int closetX = goblinX;
        int closetY = goblinY;

        if(shortestX < 0){
            closetX = goblinX - 64;
        } else if(shortestX > 0){
            closetX = goblinX + 64;
        }

        if(shortestY < 0){
            closetY = goblinY - 64;
        } else if(shortestY > 0){
            closetY = goblinY + 64;
        }

        int startX = goblinX - 64;
        int startY = goblinY - 64;
        int maxRight = goblinX + 128;
        int maxDown = goblinY + 128;

        for(int i = startX; i < maxRight; i += 64){
            for(int j = startY; j < maxDown; j += 64){
                if(playerX == i && playerY == j){
                    continue;
                }
                Rectangle r = new Rectangle(i, j, 64,64);
                r.setOpacity(0.2);
                r.setFill(Color.BLUE);
                r.setStroke(Color.RED);
                moveGrid.add(r);
            }
        }

        for(Rectangle r : moveGrid){
            currentPane.getChildren().add(r);
        }

        if(closetX == playerX && closetY == playerY){
            testGoblin.setTokenPos(goblinX,goblinY);
        } else{
            testGoblin.setTokenPos(closetX, closetY);
        }

        clearMovementGrid();
    }

    private void goblinAttack(){
        createAttackGrid();
        setGoblinAttackGrid();
    }

    private void setGoblinAttackGrid(){
        int x = player.getTokenX();
        int y = player.getTokenY();

        int upAndLeft = -64;
        int rightAndDown = 64;
        int goblinX = testGoblin.getTokenX();
        int goblinY = testGoblin.getTokenY();

        for(int i = 0; i < 4; i++){
            switch (i){
                case 0:
                    recList.get(i).setLayoutX(goblinX);
                    recList.get(i).setLayoutY(goblinY + upAndLeft);
                    if(x == recList.get(i).getLayoutX() && y == recList.get(i).getLayoutY()) testGoblin.toHit(player);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 1:
                    recList.get(i).setLayoutX(goblinX + rightAndDown);
                    recList.get(i).setLayoutY(goblinY);
                    if(x == recList.get(i).getLayoutX() && y == recList.get(i).getLayoutY()) testGoblin.toHit(player);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 2:
                    recList.get(i).setLayoutX(goblinX);
                    recList.get(i).setLayoutY(rightAndDown + goblinY);
                    if(x == recList.get(i).getLayoutX() && y == recList.get(i).getLayoutY()) testGoblin.toHit(player);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 3:
                    recList.get(i).setLayoutX(upAndLeft + goblinX);
                    recList.get(i).setLayoutY(goblinY);
                    if(x == recList.get(i).getLayoutX() && y == recList.get(i).getLayoutY()) testGoblin.toHit(player);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                default:
            }
        }

        clearAttackGrid();
    }

}
