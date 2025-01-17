package gameLogic;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static gameLogic.GameLogic.back;

public class UI {
    Rectangle playerMenu = new Rectangle(0,0, 128, 256);
    StackPane menuPane = new StackPane();
    Rectangle playerStatus = new Rectangle(0,0, 64, 128);
    Rectangle goblinStatus = new Rectangle(0,0,64,128);
    StackPane statusPane = new StackPane();

    private final Human player;
    private final AnchorPane currentPane;
    private final GameAnnouncement announcements;

    private final Grid grid;

    private String displayMsg;

    public UI(Human player,
              AnchorPane currentPane,
              GameAnnouncement announcements,
              Grid grid,
              PlayerGridLogic playerGridLogic,
              GoblinGridLogic goblinGridLogic,
              ItemGridLogic itemGridLogic) {
        this.player = player;
        this.currentPane = currentPane;
        this.announcements = announcements;
        this.grid = grid;
    }

    public void initAttackGrid() {
        grid.initAttackGrid();
        currentPane.getChildren().remove(back);
    }

    public void showMenu(){
        statusPane.getChildren().clear();
        goblinStatus.setFill(Color.RED);
        goblinStatus.setOpacity(0.8);
        playerStatus.setFill(Color.BLUE);
        playerStatus.setOpacity(0.8);

        setPlayerMenuPos();

        player.getToken().setOnMouseEntered(mouseEvent -> {
            setStatusMenu(player.getTokenX(),player.getTokenY());
            statusPane.getChildren().add(playerStatus);
            statusText(player.getAC(), player.getHealth(), player.getMagic());
            currentPane.getChildren().add(statusPane);
        });
        player.getToken().setOnMouseExited(mouseEvent -> {
            statusPane.getChildren().clear();
            currentPane.getChildren().remove(statusPane);
        });

        ArrayList<Goblin> listOfGoblins = grid.goblinGridLogic.getListOfGoblins();

        for(Goblin goblin : listOfGoblins){
            goblin.getToken().setOnMouseEntered(mouseEvent -> {
                setStatusMenu(goblin.getTokenX(), goblin.getTokenY());
                statusPane.getChildren().add(goblinStatus);
                statusText(goblin.getAC(), goblin.getHealth(), goblin.getMagic());
                currentPane.getChildren().add(statusPane);
            });

            goblin.getToken().setOnMouseExited(mouseEvent -> {
                statusPane.getChildren().clear();
                currentPane.getChildren().remove(statusPane);
            });
        }
    }

    public void setPlayerMenuPos(){
        int x = player.getTokenX();
        int y = player.getTokenY();

        playerMenu.setFill(Color.BLUE);
        playerMenu.setOpacity(0.8);

        if(x <= 512){
            menuPane.setLayoutX(x + 64);
            menuPane.setLayoutY(y);
        } else {
            menuPane.setLayoutX(x - 128);
            menuPane.setLayoutY(y);
        }

        if(y < 192 && x >= 256){
            menuPane.setLayoutX(x + 64);
            menuPane.setLayoutY(0);
        } else if(y > 512 && x > 256){
            menuPane.setLayoutX(x - 128);
            menuPane.setLayoutY(512);
        }
    }

    private void setStatusMenu(int x, int y){
        if(x < 480){
            statusPane.setLayoutX(x + 64);
            statusPane.setLayoutY(y);
        } else {
            statusPane.setLayoutX(x - 64);
            statusPane.setLayoutY(y);
        }
    }

    private void statusText(int ac, int hitPoints, int magicPoints){
        Text armor = new Text();
        armor.setText("AC: " + ac);
        armor.setTranslateY(-50);
        armor.setFont(Font.font("Verdana", 15));
        armor.setFill(Color.WHITE);

        Text hp = new Text();
        hp.setText("HP: " + hitPoints);
        hp.setTranslateY(-25);
        hp.setFont(Font.font("Verdana", 15));
        hp.setFill(Color.WHITE);

        Text mp = new Text();
        mp.setText("MP: " + magicPoints);
        mp.setTranslateY(0);
        mp.setFont(Font.font("Verdana", 15));
        mp.setFill(Color.WHITE);

        statusPane.getChildren().addAll(armor, hp, mp);
    }

    // kinda works?
    public void itemMenu(){
        menuPane.getChildren().clear();
        int counter = 0;
        int testPos = -64;
        int yPos = 0;

        for(ITEMS item : player.getInventory().keySet()){
            ItemButton itemButton = new ItemButton(item, menuPane, currentPane);
            itemButton.setTranslateX(testPos += 64);
            itemButton.setTranslateY(yPos);
            counter++;
            if(counter > 2){
                testPos = -64;
                yPos += 64;
                counter = 0;
            }
            menuPane.getChildren().add(itemButton);
        }

        menuPane.setLayoutX(player.getTokenX());
        menuPane.setLayoutY(player.getTokenY());
        currentPane.getChildren().add(menuPane);
    }

    // kinda works?
    public void addButtons(){
        ActionButton attack = new ActionButton("ATTACK");
        ActionButton move = new ActionButton("MOVE");
        ActionButton item = new ActionButton("ITEMS");
        ActionButton endTurn = new ActionButton("END");

        announcements.getPlayerDialogueBox().getDialogue(displayMsg, Color.BLUE).setLayoutX(320);
        announcements.getPlayerDialogueBox().getDialogue(displayMsg, Color.BLUE).setLayoutY(256);

        announcements.getPlayerDialogueBox().getDialogue(displayMsg, Color.BLUE).setOnMouseClicked(event -> {
            currentPane.getChildren().remove(announcements.getPlayerDialogueBox().getDialogue(displayMsg, Color.BLUE));
            openMenu();
        });

        back.setOnMouseClicked(mouseEvent -> {
            closeMenu();
            grid.clearMovementGrid();
            grid.clearAttackGrid();
            openMenu();
            currentPane.getChildren().remove(back);
        });

        attack.setOnMouseClicked(mouseEvent -> {
            if(player.hasAttacked()){
                closeMenu();
                displayMsg = "YOU HAVE ALREADY ATTACKED";
                currentPane.getChildren().add(announcements.getPlayerDialogueBox().getDialogue(displayMsg, Color.BLUE));
            } else{
//                Look at again was formally AttackOptions
                grid.createAttackGrid();
                closeMenu();
            }
        });

        move.setOnMouseClicked(mouseEvent -> {
            if(player.hasMoved()){
                closeMenu();
                displayMsg = "YOU HAVE ALREADY MOVED";
                currentPane.getChildren().add(announcements.getPlayerDialogueBox().getDialogue(displayMsg, Color.BLUE));
            } else{
                closeMenu();
                grid.getPossiblePlayerMoves(this);
                back.setLayoutX(player.getTokenX() - 128);
                back.setLayoutY(player.getTokenY() + 128);
                currentPane.getChildren().add(back);
            }
        });

        item.setOnMouseClicked(mouseEvent -> {
            closeMenu();
            this.itemMenu();
            this.showMenu();
            back.setLayoutX(player.getTokenX() - 64);
            back.setLayoutY(player.getTokenY() - 64);
            currentPane.getChildren().add(back);
        });

        endTurn.setOnMouseClicked(mouseEvent -> endTurnPhase());

        attack.setTranslateY(-90);
        move.setTranslateY(-30);
        item.setTranslateY(30);
        endTurn.setTranslateY(90);

        menuPane.getChildren().add(attack);
        menuPane.getChildren().add(move);
        menuPane.getChildren().add(item);
        menuPane.getChildren().add(endTurn);
    }

    private void endTurnPhase(){
        Timer timer = new Timer();

        currentPane.getChildren().remove(menuPane);
        currentPane.getChildren().remove(announcements.getPlayerDialogueBox().getDialogue(displayMsg, Color.BLUE));
        currentPane.getChildren().remove(announcements.getPlayerBanner());
        player.setHasMoved(false);
        player.setHasAttacked(false);
        if(grid.goblinGridLogic.anyGoblinsAlive()){
            currentPane.getChildren().add(announcements.getGoblinBanner());
            moveBanner(announcements.getGoblinBanner());
            int time = 2000;

            ArrayList<Goblin> listOfGoblins = grid.goblinGridLogic.getListOfGoblins();

            for(Goblin goblin : listOfGoblins){
                grid.goblinGridLogic.goblinTurn(goblin,
                        time,
                        this,
                        grid.recList,
                        grid.gridPos);
                time += 1000;
            }

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> playerTurn());
                }
            }, time + 800);
        } else {
            player.playClearLevelSound();
            outComeBanner(announcements.getVictoryBanner());
            currentPane.getChildren().add(announcements.getVictoryBanner());
        }
    }

    public void closeMenu(){
        menuPane.getChildren().clear();
        currentPane.getChildren().remove(menuPane);
    }

    //BANNER RELATED
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

    public void gameOver(){
        player.playPlayerDeathSound();
        outComeBanner(announcements.getDefeatBanner());
        currentPane.getChildren().add(announcements.getDefeatBanner());
    }

    public void playerTurn(){
        currentPane.getChildren().remove(announcements.getGoblinBanner());
        if(player.getHealth() > 0){
            currentPane.getChildren().add(announcements.getPlayerBanner());
            moveBanner(announcements.getPlayerBanner());
            this.showMenu();
        }

        Timer timer = new Timer();

        if(grid.goblinGridLogic.anyGoblinsAlive() && player.getHealth() > 0){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> openMenu());
                }
            }, 1450);
        }
    }

    public void openMenu() {
        grid.goblinGridLogic.getDead();
        menuPane.getChildren().clear();
        setPlayerMenuPos();
        menuPane.getChildren().add(playerMenu);
        addButtons();
        currentPane.getChildren().add(menuPane);
    }

}
