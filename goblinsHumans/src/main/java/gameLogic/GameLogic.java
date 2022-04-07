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


public class GameLogic {
    public static final Human player = SceneController.getPlayer();
    protected final Goblin testGoblin = new Goblin();
    private Banner banner = new Banner();
    private final StackPane playerBanner = banner.getPlayerBanner();
    private Banner gobBanner = new Banner();
    private final StackPane goblinBanner = gobBanner.getGoblinsBanner();
    Rectangle playerMenu = new Rectangle(0,0, 128, 192);
    StackPane menuPane = new StackPane();

    private boolean hasAttacked = false;
    private boolean hasMoved = false;

    ArrayList<Rectangle> recList = new ArrayList<>();
    ArrayList<Rectangle> moveGrid = new ArrayList<>();

    public void gameStart(AnchorPane pane){
        pane.getChildren().add(player.getToken());
        pane.getChildren().add(testGoblin.getToken());
        player.setTokenPos(512,256);
        testGoblin.setTokenPos(64,64);
        pane.getChildren().add(playerBanner);
        moveBanner(playerBanner);
        playerTurn(pane);

    }

    private void playerTurn(AnchorPane pane){
        pane.getChildren().remove(goblinBanner);
        openMenu(pane, testGoblin);
    }

    private void goblinTurn(AnchorPane pane){
        pane.getChildren().remove(playerBanner);
        pane.getChildren().add(goblinBanner);
        moveBanner(goblinBanner);
        getGoblinsMoves(pane);
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

    //TURN MENU
    public void openMenu(AnchorPane pane, Goblin goblin){
        menuPane.getChildren().clear();
        setMenuPos();
        menuPane.getChildren().add(playerMenu);
        addButtons(pane, goblin);
        pane.getChildren().add(menuPane);
    }

    private void closeMenu(AnchorPane pane){
        menuPane.getChildren().clear();
        pane.getChildren().remove(menuPane);
    }

    private void setMenuPos(){
        int x = GameLogic.player.getTokenX();
        int y = GameLogic.player.getTokenY();

        playerMenu.setFill(Color.BLUE);
        playerMenu.setOpacity(0.3);

        if(x > 256){
            menuPane.setLayoutX(x - 128);
            menuPane.setLayoutY(y);
        } else if(x <= 256){
            menuPane.setLayoutX(x + 64);
            menuPane.setLayoutY(y);
        }
    }

    private void addButtons(AnchorPane pane, Goblin goblin){
        ActionButton attack = new ActionButton("ATTACK");
        ActionButton move = new ActionButton("MOVE");
        ActionButton item = new ActionButton("ITEMS");
        ActionButton back = new ActionButton("BACK");
        ActionButton endTurn = new ActionButton("END");

        back.setOnMouseClicked(mouseEvent -> {
            clearMovementGrid(pane);
            clearAttackGrid(pane);
            openMenu(pane, goblin);
            pane.getChildren().remove(back);
        });

        attack.setOnMouseClicked(mouseEvent -> {
            if(hasAttacked){
                System.out.println("You have already attacked this turn!!!");
            } else{
                attackGrid(pane, back, goblin);
                closeMenu(pane);
            }
        });

        move.setOnMouseClicked(mouseEvent -> {
            if(hasMoved){
                System.out.println("You have already moved this turn!!!");
            } else{
                closeMenu(pane);
                getPossibleMoves(pane, goblin, back);
                back.setLayoutX(player.getTokenX() - 64);
                back.setLayoutY(player.getTokenY() + 128);
                pane.getChildren().add(back);
            }
        });

        endTurn.setOnMouseClicked(mouseEvent -> {
            pane.getChildren().remove(menuPane);
            goblinTurn(pane);
        });

        attack.setTranslateY(-64);
        item.setTranslateY(64);
        endTurn.setTranslateY(128);

        menuPane.getChildren().add(attack);
        menuPane.getChildren().add(move);
        menuPane.getChildren().add(item);
        menuPane.getChildren().add(endTurn);
    }

    //ATTACK MENU
    public void attackGrid(AnchorPane pane, ActionButton back, Goblin goblin){
        createAttackGrid();
        int x = player.getTokenX();
        int y = player.getTokenY();
        setAttackGrid(x,y, pane, back, goblin);
    }

    private void createAttackGrid(){
        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.2);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);
            recList.add(r);
        }
    }

    private void setAttackGrid(int x, int y, AnchorPane pane, ActionButton back, Goblin goblin){
        int upAndLeft = -64;
        int rightAndDown = 64;
        int goblinX = goblin.getTokenX();
        int goblinY = goblin.getTokenY();

        back.setLayoutX(x - 128);
        back.setLayoutY(y + rightAndDown);
        pane.getChildren().add(back);

        for(int i = 0; i < 4; i++){
            switch (i){
                case 0:
                    recList.get(i).setLayoutX(x);
                    recList.get(i).setLayoutY(y + upAndLeft);
                    if(goblinX == recList.get(i).getLayoutX() && goblinY == recList.get(i).getLayoutY()) setAttackListeners(recList.get(i), goblin, pane, back);
                    pane.getChildren().add(recList.get(i));
                    break;
                case 1:
                    recList.get(i).setLayoutX(x + rightAndDown);
                    recList.get(i).setLayoutY(y);
                    if(goblinX == recList.get(i).getLayoutX() && goblinY == recList.get(i).getLayoutY()) setAttackListeners(recList.get(i), goblin, pane, back);
                    pane.getChildren().add(recList.get(i));
                    break;
                case 2:
                    recList.get(i).setLayoutX(x);
                    recList.get(i).setLayoutY(rightAndDown + y);
                    if(goblinX == recList.get(i).getLayoutX() && goblinY == recList.get(i).getLayoutY()) setAttackListeners(recList.get(i), goblin, pane, back);
                    pane.getChildren().add(recList.get(i));
                    break;
                case 3:
                    recList.get(i).setLayoutX(upAndLeft + x);
                    recList.get(i).setLayoutY(y);
                    if(goblinX == recList.get(i).getLayoutX() && goblinY == recList.get(i).getLayoutY()) setAttackListeners(recList.get(i), goblin, pane, back);
                    pane.getChildren().add(recList.get(i));
                    break;
                default:
            }
        }
    }

    private void setAttackListeners(Rectangle r, Goblin goblin, AnchorPane pane, ActionButton back){
        r.setOnMouseClicked(mouseEvent -> {
            player.toHit(goblin);
            hasAttacked = true;
            clearAttackGrid(pane);
            pane.getChildren().remove(back);
            openMenu(pane, goblin);
        });
    }

    private void clearAttackGrid(AnchorPane pane){
        for (Rectangle rectangle : recList) {
            pane.getChildren().remove(rectangle);
        }
    }

    private void clearMovementGrid(AnchorPane pane){
        for (Rectangle rectangle : moveGrid) {
            pane.getChildren().remove(rectangle);
        }
        moveGrid = new ArrayList<>();
    }

    //PLAYER MOVE GRID
    public void getPossibleMoves(AnchorPane pane, Goblin goblin, ActionButton back){
        int x = player.getTokenX();
        int y = player.getTokenY();
        int goblinX = goblin.getTokenX();
        int goblinY = goblin.getTokenY();

        int startX = x - 64;
        int startY = y - 64;
        int maxRight = x + 128;
        int maxDown = y + 128;

        for(int i = startX; i < maxRight; i += 64){
            for(int j = startY; j < maxDown; j += 64){
                if(goblinX == i && goblinY == j){
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
                clearMovementGrid(pane);
                pane.getChildren().remove(back);
                openMenu(pane, goblin);
            });

            pane.getChildren().add(r);
        }
    }

    private void getGoblinsMoves(AnchorPane pane){
        int goblinX = testGoblin.getTokenX();
        int goblinY = testGoblin.getTokenY();
        int playerX = player.getTokenX();
        int playerY = player.getTokenY();

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
            pane.getChildren().add(r);
        }
    }

}
