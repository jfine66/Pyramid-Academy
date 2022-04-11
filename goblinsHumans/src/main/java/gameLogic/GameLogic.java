package gameLogic;


import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.*;
import view.SceneController;


import java.nio.file.Paths;
import java.util.*;


public class GameLogic {
    public static final Human player = SceneController.getPlayer();
    protected final Goblin testGoblin = new Goblin();
    protected final Goblin goblinTwo = new Goblin();
    protected final Goblin goblinThree = new Goblin();
    protected final Goblin goblinFour = new Goblin();
    private final ArrayList<Goblin> listOfGoblins = new ArrayList<>();

    private final Banner banner = new Banner();
    private final StackPane playerBanner = banner.getPlayerBanner();
    private final Banner gobBanner = new Banner();
    private final StackPane goblinBanner = gobBanner.getGoblinsBanner();
    private final Banner victoryDisplay = new Banner();
    private final StackPane victoryBanner = victoryDisplay.getVictoryBanner();
    private final Banner defeatDisplay = new Banner();
    private final StackPane defeatBanner = defeatDisplay.getDefeatBanner(mainMenuButton());
    private final DialogueBox playerDialogueBox = new DialogueBox();
    private final DialogueBox goblinDialogueBox = new DialogueBox();

    private AnchorPane currentPane;

    Rectangle playerMenu = new Rectangle(0,0, 128, 256);
    StackPane menuPane = new StackPane();
    Rectangle playerStatus = new Rectangle(0,0, 64, 128);
    Rectangle goblinStatus = new Rectangle(0,0,64,128);
    StackPane statusPane = new StackPane();

    private String msg = "";
    private MediaPlayer mediaPlayer;
    private boolean hasAttacked = false;
    private boolean hasMoved = false;
    ActionButton back = new ActionButton("BACK");

    ArrayList<Rectangle> recList = new ArrayList<>();
    ArrayList<Rectangle> moveGrid = new ArrayList<>();
    HashMap<ArrayList<Integer>, Object> gridPos = new HashMap<>();
    HashMap<Integer, String> goblinsXPos = new HashMap<>();
    HashMap<Integer, String> goblinsYPos = new HashMap<>();

    public GameLogic(AnchorPane pane){
        this.currentPane = pane;
        listOfGoblins.add(testGoblin);
        listOfGoblins.add(goblinTwo);
        listOfGoblins.add(goblinThree);
        listOfGoblins.add(goblinFour);
        gameStart();
    }

    public AnchorPane getCurrentPane() {
        return currentPane;
    }

    public void setCurrentPane(AnchorPane pane){
        this.currentPane = pane;
    }

    public void gameStart(){
        player.playerStartPos(currentPane);
//        testGoblin.setTokenPos(512, 384);
//        goblinTwo.setTokenPos(512,512);
//        goblinThree.setTokenPos(448,448);
//        goblinFour.setTokenPos(576,448);


        setGoblinPos();
        fillAxis();
        playerTurn();
    }

    private void setGoblinPos(){
        for(Goblin goblin : listOfGoblins){
            goblin.setTokenPos(getRandomX(), getRandomY());
            currentPane.getChildren().add(goblin.getToken());
        }
    }

    private int getRandomX(){
        Random rand = new Random();
        int x = rand.nextInt(961);
        while(x % 64 != 0){
            x = rand.nextInt(961);
        }

        return x;
    }

    private int getRandomY(){
        Random rand = new Random();
        int y = rand.nextInt(705);
        while(y % 64 != 0){
            y = rand.nextInt(705);
        }

        return y;
    }

    //EVERYTHING PLAYER RELATED
    private void playerTurn(){
        currentPane.getChildren().remove(goblinBanner);
        if(player.getHealth() > 0){
            currentPane.getChildren().add(playerBanner);
            moveBanner(playerBanner);
            showMenu();
        }

        Timer timer = new Timer();

        if(anyGoblinsAlive() && player.getHealth() > 0){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> openMenu());
                }
            }, 1450);
        }
    }

    //SEE PLAYER STATUS
    private void showMenu(){
        statusPane.getChildren().clear();
        goblinStatus.setFill(Color.RED);
        goblinStatus.setOpacity(0.8);
        playerStatus.setFill(Color.BLUE);
        playerStatus.setOpacity(0.8);

        setPlayerMenuPos();

        player.getToken().setOnMouseEntered(mouseEvent -> {
            System.out.println(player.getInventory());
            setStatusMenu(player.getTokenX(),player.getTokenY());
            statusPane.getChildren().add(playerStatus);
            statusText(player.getAc(), player.getHealth(), player.getMagic());
            currentPane.getChildren().add(statusPane);
        });
        player.getToken().setOnMouseExited(mouseEvent -> {
            statusPane.getChildren().clear();
            currentPane.getChildren().remove(statusPane);
        });

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


    //TURN MENU
    private void openMenu(){
        menuPane.getChildren().clear();
        setPlayerMenuPos();
        menuPane.getChildren().add(playerMenu);
        addButtons();
        currentPane.getChildren().add(menuPane);
    }

    private void closeMenu(){
        menuPane.getChildren().clear();
        currentPane.getChildren().remove(menuPane);
    }

    private void setPlayerMenuPos(){
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

    private void itemMenu(){
        menuPane.getChildren().clear();
        int testPos = -64;

        for(ITEMS item : player.getInventory().keySet()){
            ItemButton itemButton = new ItemButton(item, menuPane, currentPane);
            itemButton.setTranslateX(testPos += 64);
            menuPane.getChildren().add(itemButton);
        }

        menuPane.setLayoutX(player.getTokenX());
        menuPane.setLayoutY(player.getTokenY());
        currentPane.getChildren().add(menuPane);
    }

    private void addButtons(){
        ActionButton attack = new ActionButton("ATTACK");
        ActionButton move = new ActionButton("MOVE");
        ActionButton item = new ActionButton("ITEMS");
        ActionButton endTurn = new ActionButton("END");

        playerDialogueBox.getPlayerDialogue(msg).setLayoutX(320);
        playerDialogueBox.getPlayerDialogue(msg).setLayoutY(256);

        playerDialogueBox.getPlayerDialogue(msg).setOnMouseClicked(event -> {
            currentPane.getChildren().remove(playerDialogueBox.getPlayerDialogue(msg));
            openMenu();
        });

        back.setOnMouseClicked(mouseEvent -> {
            closeMenu();
            clearMovementGrid();
            clearAttackGrid();
            openMenu();
            currentPane.getChildren().remove(back);
        });

        attack.setOnMouseClicked(mouseEvent -> {
            if(hasAttacked){
                closeMenu();
                msg = "YOU HAVE ALREADY ATTACKED";
                currentPane.getChildren().add(playerDialogueBox.getPlayerDialogue(msg));
            } else{
                attackGrid();
                closeMenu();
            }
        });

        move.setOnMouseClicked(mouseEvent -> {
            if(hasMoved){
                closeMenu();
                msg = "YOU HAVE ALREADY MOVED";
                currentPane.getChildren().add(playerDialogueBox.getPlayerDialogue(msg));
            } else{
                closeMenu();
                getPossibleMoves();
                back.setLayoutX(player.getTokenX() - 128);
                back.setLayoutY(player.getTokenY() + 128);
                currentPane.getChildren().add(back);
            }
        });

        item.setOnMouseClicked(mouseEvent -> {
            closeMenu();
            itemMenu();
            showMenu();
            back.setLayoutX(player.getTokenX() - 64);
            back.setLayoutY(player.getTokenY() + 64);
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
        currentPane.getChildren().remove(playerDialogueBox.getPlayerDialogue(msg));
        currentPane.getChildren().remove(playerBanner);
        hasAttacked = false;
        hasMoved = false;
        if(anyGoblinsAlive()){
            currentPane.getChildren().add(goblinBanner);
            moveBanner(goblinBanner);
            int time = 2000;
            for(Goblin goblin : listOfGoblins){
                goblinTurn(goblin, time);
                time += 1000;
            }

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> playerTurn());
                }
            }, time + 800);
        } else {
            playerClearLevelSound();
            outComeBanner(victoryBanner);
            currentPane.getChildren().add(victoryBanner);
        }
    }

    // EVERYTHING GRID RELATED
    private void fillAxis(){
        gridPos = new HashMap<>();

        gridPos.put(new ArrayList<>(Arrays.asList(player.getTokenX(),player.getTokenY())), player);

        for(Goblin goblin : listOfGoblins){
            gridPos.put(new ArrayList<>(Arrays.asList(goblin.getTokenX(), goblin.getTokenY())), goblin);
        }
    }

    private boolean anyGoblinsAlive(){
        boolean isAlive = false;

        for(Goblin goblin : listOfGoblins){
            isAlive = goblin.getHealth() > 0;
        }

        return isAlive;
    }

    private void getGoblinsPos(){
        for(Goblin goblin : listOfGoblins){
            goblinsXPos.put(goblin.getTokenX(), "filled");
        }

        for(Goblin goblin : listOfGoblins){
            goblinsYPos.put(goblin.getTokenY(), "filled");
        }
    }

    public void attackGrid(){
        createAttackGrid();
        setAttackGrid();
    }

    private void createAttackGrid(){
        recList = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            Rectangle r = new Rectangle(0,0, 64,64);
            r.setOpacity(0.4);
            r.setFill(Color.RED);
            r.setStroke(Color.WHITE);
            recList.add(r);
        }
    }

    private void setAttackGrid(){
        getGoblinsPos();
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
            if(goblinsXPos.containsKey((int) recList.get(l).getLayoutX()) && goblinsYPos.containsKey((int) recList.get(l).getLayoutY())){
                setAttackListeners(recList.get(l), getCurrentGoblin( (int) recList.get(l).getLayoutX(), (int) recList.get(l).getLayoutY()));
            }
            currentPane.getChildren().add(recList.get(l));
        }
    }

    private void setAttackListeners(Rectangle r, Goblin goblin){
        r.setOnMouseClicked(mouseEvent -> {
            msg = player.toHit(goblin);
            hasAttacked = true;
            if(goblin.getHealth() < 0) removeDeadGoblin(goblin);
            clearAttackGrid();

            currentPane.getChildren().remove(back);

            currentPane.getChildren().add(playerDialogueBox.getPlayerDialogue(msg));

        });

    }

    private  void removeDeadGoblin(Goblin goblin){
        ITEMS drop = goblin.didDrop();
        if(drop != null){
            msg += "\nGoblin dropped " + drop + " \nit has been added to your inventory";
            player.addToInventory(drop);
        }
        currentPane.getChildren().remove(goblin.getToken());
        goblin.setTokenPos(0,0);
        listOfGoblins.remove(goblin);
    }


    //PLAYER MOVE GRID
    public void getPossibleMoves(){
        fillAxis();
        int x = player.getTokenX();
        int y = player.getTokenY();

        for(int i = y - 128; i < y + 129; i += 64){
            if(isSpaceTaken(x,i)) continue;
            Rectangle r = new Rectangle(x, i, 64,64);
            r.setOpacity(0.6);
            r.setFill(Color.BLUE);
            r.setStroke(Color.WHITE);
            r.setStrokeWidth(1);
            moveGrid.add(r);
        }

        for(int i = x - 128; i < x + 129; i += 64){
            if(isSpaceTaken(i,y)) continue;
            Rectangle r = new Rectangle(i, y, 64,64);
            r.setOpacity(0.6);
            r.setFill(Color.BLUE);
            r.setStroke(Color.WHITE);
            r.setStrokeWidth(1);
            moveGrid.add(r);
        }

        y -= 64;

        for(int i = x - 64; i < x + 128; i += 64){
            if(isSpaceTaken(i,y)) {
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

        y = player.getTokenY();

        y += 64;

        for(int i = x - 64; i < x + 128; i += 64){
            if(isSpaceTaken(i,y)) {
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

        // PUT LISTENERS IN ANOTHER METHOD FOOL!!!!!
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

    // EVERYTHING RELATED TO GOBLINS
    private void goblinTurn(Goblin goblin, int time){
        Timer timer = new Timer();
        //ACTION GOBLINS TAKE ON THEIR TURN
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> getGoblinsMoves(goblin));
            }
        }, time);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> goblinAttack(goblin));
            }
        }, time);

    }

    private Goblin getCurrentGoblin(int x, int y){
        return (Goblin) gridPos.get(new ArrayList<>(Arrays.asList(x,y)));
    }

    //GOBLINS CONTROLLER
    private void getGoblinsMoves(Goblin goblin){
        fillAxis();
        int goblinX = goblin.getTokenX();
        int goblinY = goblin.getTokenY();
        int playerX = player.getTokenX();
        int playerY = player.getTokenY();

        int closetX = goblinX;
        int closetY = goblinY;

        //NEED TO ADD A WAY TO MOVE UP-LEFT, UP-RIGHT
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

        if(!isSpaceTaken(closetX, closetY)) goblin.setTokenPos(closetX, closetY);

        fillAxis();
    }

    private boolean isSpaceTaken(int x, int y){
        return gridPos.containsKey(new ArrayList<>(Arrays.asList(x,y)));
    }

    private void goblinAttack(Goblin goblin){
        createAttackGrid();
        setGoblinAttackGrid(goblin);
    }

    private void setGoblinAttackGrid(Goblin goblin){
        goblinDialogueBox.getPlayerDialogue(msg).setLayoutX(320);
        goblinDialogueBox.getPlayerDialogue(msg).setLayoutY(256);

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
                            currentPane.getChildren().add(goblinDialogueBox.getGoblinDialogue(msg));
                        });
                    }
                }, 1000);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> currentPane.getChildren().remove(goblinDialogueBox.getPlayerDialogue(msg)));
                    }
                }, 1800);

                if (player.getHealth() < 0) gameOver();
            }
        }



    }

    private void gameOver(){
        playerDeathSound();
        outComeBanner(defeatBanner);
        currentPane.getChildren().add(defeatBanner);
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

    //SOUNDS
    private void playerDeathSound(){
        String url = "src/main/resources/553724__maxim-nick__sad-defeat-emotion-maxim-nick.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }

    private void playerClearLevelSound(){
        String url = "src/main/resources/462250__silverillusionist__victory-sound-1.wav";
        Media h = new Media(Paths.get(url).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }

}
