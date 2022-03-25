import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class GameLogic {
    private final Land board;
    private final Human player;
    private final Goblin enemyOne;
    private final Goblin enemyTwo;
    private final Goblin enemyThree;
    private final Goblin enemyFour;
    private final Goblin enemyFive;


    public GameLogic(Land board, Human player, Goblin...enemies){
        this.board = board;
        this.player = player;
        this.enemyOne = enemies[0];
        this.enemyTwo = enemies[1];
        this.enemyThree = enemies[2];
        this.enemyFour = enemies[3];
        this.enemyFive = enemies[4];
    }

    public void defaultPos(){
        board.setPlayerStart(player);
        board.setGoblins(enemyOne,enemyTwo,enemyThree,enemyFour,enemyFive);
    }

    public void display(){
        System.out.println(board.toString());
    }

    public void move(Object object, String direction){
        int[] piecePos = board.getCurrentPos(object);
        int column = piecePos[0];
        int row = piecePos[1];

        boolean isHuman = object.getClass().getSimpleName().equals("Human");
        boolean isGoblin = object.getClass().getSimpleName().equals("Goblin");

        try{
            board.getColumn(column).set(row, "#");
            switch (direction){
                case "e":
                    if(board.getColumn(column).get(row + 1).getClass().getSimpleName().equals("String")){
                        board.getColumn(column).set(row + 1, object);

                    } else if(isHuman && board.getColumn(column).get(row + 1).getClass().getSimpleName().equals("Goblin")){
                        ((Human) object).attack((Goblin) board.getColumn(column).get(row + 1));
                        board.setPos(object, column, row);
                        removeDead((Goblin) board.getColumn(column).get(row + 1));

                    } else if(isGoblin && board.getColumn(column).get(row + 1).getClass().getSimpleName().equals("Human")){
                        ((Goblin) object).attack((Human) board.getColumn(column).get(row + 1));
                        board.setPos(object,column,row);
                        checkPlayerHp();

                    } else {
                        board.setPos(object,column,row);
                    }
                    break;

                case "w":
                    if(board.getColumn(column).get(row - 1).getClass().getSimpleName().equals("String")){
                        board.getColumn(column).set(row - 1, object);
                    } else if(isHuman && board.getColumn(column).get(row - 1).getClass().getSimpleName().equals("Goblin")){
                        ((Human) object).attack((Goblin) board.getColumn(column).get(row - 1));
                        board.setPos(object, column, row);
                        removeDead((Goblin) board.getColumn(column).get(row - 1));

                    } else if(isGoblin && board.getColumn(column).get(row + 1).getClass().getSimpleName().equals("Human")){
                        ((Goblin) object).attack((Human) board.getColumn(column).get(row - 1));
                        board.setPos(object,column,row);
                        checkPlayerHp();
                    } else {
                        board.setPos(object,column,row);
                    }
                    break;

                case "n":
                    if(board.getColumn(column - 1).get(row).getClass().getSimpleName().equals("String")){
                        board.getColumn(column - 1).set(row, object);

                    } else if(isHuman && board.getColumn(column - 1).get(row).getClass().getSimpleName().equals("Goblin")){
                        ((Human) object).attack((Goblin) board.getColumn(column - 1).get(row));
                        board.setPos(object, column, row);
                        removeDead((Goblin) board.getColumn(column - 1).get(row));

                    } else if(isGoblin && board.getColumn(column - 1).get(row).getClass().getSimpleName().equals("Human")){
                        ((Goblin) object).attack((Human) board.getColumn(column - 1).get(row));
                        board.setPos(object,column,row);
                        checkPlayerHp();
                    } else {
                        board.setPos(object,column,row);
                    }
                    break;

                case "s":
                    if(board.getColumn(column + 1).get(row).getClass().getSimpleName().equals("String")){
                        board.getColumn(column + 1).set(row, object);

                    } else if(isHuman && board.getColumn(column + 1).get(row).getClass().getSimpleName().equals("Goblin")){
                        ((Human) object).attack((Goblin) board.getColumn(column + 1).get(row));
                        board.setPos(object, column, row);
                        removeDead((Goblin) board.getColumn(column + 1).get(row));

                    } else if(isGoblin && board.getColumn(column + 1).get(row).getClass().getSimpleName().equals("Human")){
                        ((Goblin) object).attack((Human) board.getColumn(column + 1).get(row));
                        board.setPos(object,column,row);
                        checkPlayerHp();
                    } else {
                        board.setPos(object,column,row);
                    }
                    break;
                default:
                    board.setPos(object,column,row);
            }
        } catch (Exception e){
            //System.out.println("Exception was caught: " + e.getMessage());
            board.setPos(object,column,row);
        }
    }

    public boolean checkGoblinAlive(Goblin object){
        return object.getHealth() > 0;
    }

    public void removeDead(Goblin object){
        int[] piecePos = board.getCurrentPos(object);

        if(!checkGoblinAlive(object)){
            System.out.println("YOU HAVE SLAIN A GOBLIN!!!");
            board.setPos("#", piecePos[0], piecePos[1]);
        }
    }

    public void checkPlayerHp(){
        if(player.getHealth() <= 0){
            System.out.println("You have been slain!\nGame Over.");
            System.exit(0);
        }
    }

    public void playerTurn(String direction){
        move(player, direction);
    }

    public void goblinsTurn(){
        ArrayList<String> dir = new ArrayList<>(Arrays.asList("n", "e","s","w"));


        if(checkGoblinAlive(enemyOne)){
            Collections.shuffle(dir);
            move(enemyOne, dir.get(0));
        }

        if(checkGoblinAlive(enemyTwo)){
            Collections.shuffle(dir);
            move(enemyTwo, dir.get(0));
        }

        if(checkGoblinAlive(enemyThree)){
            Collections.shuffle(dir);
            move(enemyThree, dir.get(0));
        }

        if(checkGoblinAlive(enemyOne)){
            Collections.shuffle(dir);
            move(enemyThree, dir.get(0));
        }

        if(checkGoblinAlive(enemyFour)){
            Collections.shuffle(dir);
            move(enemyFour, dir.get(0));
        }

        if(checkGoblinAlive(enemyFive)){
            Collections.shuffle(dir);
            move(enemyFive, dir.get(0));
        }
    }

    public boolean goblinsAlive(){
        return enemyOne.getHealth() > 0 || enemyTwo.getHealth() > 0 || enemyThree.getHealth() > 0 || enemyFour.getHealth() > 0 || enemyFive.getHealth() > 0;
    }
}


