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
    private ArrayList<Goblin> listOfGoblins = new ArrayList<>();

    private final Banner banner = new Banner();
    private final StackPane playerBanner = banner.getPlayerBanner();
    private final Banner gobBanner = new Banner();
    private final StackPane goblinBanner = gobBanner.getGoblinsBanner();
    private final Banner victoryDisplay = new Banner();
    private final StackPane victoryBanner = victoryDisplay.getVictoryBanner();
    private final Banner defeatDisplay = new Banner();
    private final StackPane defeatBanner = defeatDisplay.getDefeatBanner(mainMenuButton());
    private final DialogueBox dialogueBox = new DialogueBox();


    private AnchorPane currentPane;

    Rectangle playerMenu = new Rectangle(0,0, 128, 256);
    StackPane menuPane = new StackPane();
    Rectangle playerStatus = new Rectangle(0,0, 64, 128);
    StackPane statusPane = new StackPane();

    private String msg = "";
    private MediaPlayer mediaPlayer;
    private boolean hasAttacked = false;
    private boolean hasMoved = false;
    private int numberOfGoblins;
    ActionButton back = new ActionButton("BACK");

    ArrayList<Rectangle> recList = new ArrayList<>();
    ArrayList<Rectangle> moveGrid = new ArrayList<>();
    HashMap<Integer, String> gridXAxis = new HashMap<>();
    HashMap<Integer, String> gridYAxis = new HashMap<>();
    HashMap<Integer, String> goblinsXPos = new HashMap<>();
    HashMap<Integer, String> goblinsYPos = new HashMap<>();

    public GameLogic(AnchorPane pane){
        this.currentPane = pane;
        listOfGoblins.add(testGoblin);
        listOfGoblins.add(goblinTwo);
        numberOfGoblins = listOfGoblins.size();

        gameStart();
    }


    public void gameStart(){
        currentPane.getChildren().add(player.getToken());
        player.setTokenPos(128,128);
        setGoblinPos();
        player.setHealth(5);
        testGoblin.setHealth(10);
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
        showMenu();
        Timer timer = new Timer();

        for (Goblin goblin : listOfGoblins){
            if(goblin.getHealth() < 0){
                currentPane.getChildren().remove(goblin.getToken());
                goblin.setTokenPos(0,0);
                numberOfGoblins--;
            }
        }

        if(numberOfGoblins > 0){
            currentPane.getChildren().add(playerBanner);
            moveBanner(playerBanner);
            currentPane.getChildren().remove(goblinBanner);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> openMenu());
                }
            }, 1450);
        } else {
            playerClearLevelSound();
            outComeBanner(victoryBanner);
            currentPane.getChildren().add(victoryBanner);
        }

    }

    //SEE PLAYER STATUS
    private void showMenu(){
        statusPane.getChildren().clear();
        setMenuPos();
        statusPane.getChildren().add(playerStatus);

        player.getToken().setOnMouseEntered(mouseEvent -> {
            statusText();
            currentPane.getChildren().add(statusPane);
        });
        player.getToken().setOnMouseExited(mouseEvent -> currentPane.getChildren().remove(statusPane));
    }

    private void statusText(){
        Text hp = new Text();
        hp.setText("HP: " + player.getHealth());
        hp.setTranslateY(-30);
        hp.setFont(Font.font("Verdana", 20));
        hp.setFill(Color.WHITE);
        statusPane.getChildren().add(hp);
    }

    //TURN MENU
    private void openMenu(){
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
        playerStatus.setFill(Color.BLUE);
        playerStatus.setOpacity(0.3);

        if(x > 256){
            menuPane.setLayoutX(x - 128);
            menuPane.setLayoutY(y);
            statusPane.setLayoutX(x + 64);
            statusPane.setLayoutY(y);
        } else {
            menuPane.setLayoutX(x + 64);
            menuPane.setLayoutY(y);
            statusPane.setLayoutX(x - 64);
            statusPane.setLayoutY(y);
        }

        if(y < 192 && x >= 256){
            menuPane.setLayoutX(x + 64);
            menuPane.setLayoutY(0);
        } else if(y > 512 && x > 256){
            menuPane.setLayoutX(x - 128);
            menuPane.setLayoutY(512);
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
            currentPane.getChildren().remove(dialogueBox.getPlayerDialogue(msg));
            currentPane.getChildren().remove(playerBanner);
            hasAttacked = false;
            hasMoved = false;
            goblinTurn();
        });

        attack.setTranslateY(-90);
        move.setTranslateY(-30);
        item.setTranslateY(30);
        endTurn.setTranslateY(90);

        menuPane.getChildren().add(attack);
        menuPane.getChildren().add(move);
        menuPane.getChildren().add(item);
        menuPane.getChildren().add(endTurn);
    }

    // EVERYTHING GRID RELATED
    private void fillAxis(){
        gridXAxis.put(player.getTokenX(), "filled");
        gridYAxis.put(player.getTokenY(), "filled");

        for(Goblin goblin : listOfGoblins){
            gridXAxis.put(goblin.getTokenX(), "filled");
        }

        for(Goblin goblin : listOfGoblins){
            gridYAxis.put(goblin.getTokenY(), "filled");
        }

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
                    if(goblinsXPos.containsKey( (int) recList.get(i).getLayoutX()) && goblinsYPos.containsKey((int) recList.get(i).getLayoutY()))
                        setAttackListeners(recList.get(i), getCurrentGoblin(), back);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 1:
                    recList.get(i).setLayoutX(x + rightAndDown);
                    recList.get(i).setLayoutY(y);
                    if(goblinsXPos.containsKey( (int) recList.get(i).getLayoutX()) && goblinsYPos.containsKey((int) recList.get(i).getLayoutY())) setAttackListeners(recList.get(i), getCurrentGoblin(), back);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 2:
                    recList.get(i).setLayoutX(x);
                    recList.get(i).setLayoutY(rightAndDown + y);
                    if(goblinsXPos.containsKey((int)recList.get(i).getLayoutX()) && goblinsYPos.containsKey((int)recList.get(i).getLayoutY())) setAttackListeners(recList.get(i), getCurrentGoblin(), back);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 3:
                    recList.get(i).setLayoutX(upAndLeft + x);
                    recList.get(i).setLayoutY(y);
                    if(goblinsXPos.containsKey((int)recList.get(i).getLayoutX()) && goblinsYPos.containsKey((int)recList.get(i).getLayoutY())) setAttackListeners(recList.get(i), getCurrentGoblin(), back);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                default:
            }
        }
    }

    private void setAttackListeners(Rectangle r, Goblin goblin, ActionButton back){
        Timer timer = new Timer();

        r.setOnMouseClicked(mouseEvent -> {
            msg = player.toHit(goblin);
            hasAttacked = true;
            clearAttackGrid();

            dialogueBox.getPlayerDialogue(msg).setLayoutX(player.getTokenX() - 64);
            dialogueBox.getPlayerDialogue(msg).setLayoutY(player.getTokenY() - 64);
            currentPane.getChildren().add(dialogueBox.getPlayerDialogue(msg));
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> currentPane.getChildren().remove(dialogueBox.getPlayerDialogue(msg)));
                }
            }, 1000);
            currentPane.getChildren().remove(back);
            openMenu();
        });
    }

    //PLAYER MOVE GRID
    public void getPossibleMoves(){
        gridXAxis = new HashMap<>();
        gridYAxis = new HashMap<>();
        fillAxis();

        int x = player.getTokenX();
        int y = player.getTokenY();

        int startX = x - 64;
        int startY = y - 64;
        int maxRight = x + 128;
        int maxDown = y + 128;

        for(int i = startX; i < maxRight; i += 64){
            for(int j = startY; j < maxDown; j += 64){
                if(gridXAxis.containsKey(i) && gridYAxis.containsKey(j)){
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
    private void goblinTurn(){
        Timer timer = new Timer();
        boolean anyGoblinsAlive = false;

        for(Goblin goblin: listOfGoblins){
            if (goblin.getHealth() > 0) {
                anyGoblinsAlive = true;
                break;
            }
        }

        if(anyGoblinsAlive){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        currentPane.getChildren().add(goblinBanner);
                        moveBanner(goblinBanner);
                    });
                }
            }, 1000);
        }


        for(Goblin goblin : listOfGoblins){
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> getGoblinsMoves(goblin));
                    }
                }, 3000);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> goblinAttack(goblin));
                    }
                }, 4000);
        }

        currentPane.getChildren().remove(goblinBanner);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(player.getHealth() > 0){
                        playerTurn();
                    } else {
                        playerDeathSound();
                        outComeBanner(defeatBanner);
                        currentPane.getChildren().add(defeatBanner);
                    }
                });
            }
        }, 4100);
    }

    private Goblin getCurrentGoblin(){
        for(Goblin goblin : listOfGoblins){
            for(Integer x : goblinsXPos.keySet()){
                for(Integer y : goblinsYPos.keySet()){
                    if(goblin.getTokenX() == x && goblin.getTokenY() == y) return goblin;
                }
            }
        }

        return null;
    }

    //GOBLINS CONTROLLER
    private void getGoblinsMoves(Goblin goblin){
        int goblinX = goblin.getTokenX();
        int goblinY = goblin.getTokenY();
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
            goblin.setTokenPos(goblinX,goblinY);
        } else{
            goblin.setTokenPos(closetX, closetY);
        }

        clearMovementGrid();
    }

    private void goblinAttack(Goblin goblin){
        createAttackGrid();
        setGoblinAttackGrid(goblin);
    }

    private void setGoblinAttackGrid(Goblin goblin){
        int x = player.getTokenX();
        int y = player.getTokenY();

        int upAndLeft = -64;
        int rightAndDown = 64;
        int goblinX = goblin.getTokenX();
        int goblinY = goblin.getTokenY();

        for(int i = 0; i < 4; i++){
            switch (i){
                case 0:
                    recList.get(i).setLayoutX(goblinX);
                    recList.get(i).setLayoutY(goblinY + upAndLeft);
                    if(x == recList.get(i).getLayoutX() && y == recList.get(i).getLayoutY()) goblin.toHit(player);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 1:
                    recList.get(i).setLayoutX(goblinX + rightAndDown);
                    recList.get(i).setLayoutY(goblinY);
                    if(x == recList.get(i).getLayoutX() && y == recList.get(i).getLayoutY()) goblin.toHit(player);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 2:
                    recList.get(i).setLayoutX(goblinX);
                    recList.get(i).setLayoutY(rightAndDown + goblinY);
                    if(x == recList.get(i).getLayoutX() && y == recList.get(i).getLayoutY()) goblin.toHit(player);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                case 3:
                    recList.get(i).setLayoutX(upAndLeft + goblinX);
                    recList.get(i).setLayoutY(goblinY);
                    if(x == recList.get(i).getLayoutX() && y == recList.get(i).getLayoutY()) goblin.toHit(player);
                    currentPane.getChildren().add(recList.get(i));
                    break;
                default:
            }
        }

        clearAttackGrid();
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
