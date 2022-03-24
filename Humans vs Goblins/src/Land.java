import java.util.ArrayList;
import java.util.Arrays;

public class Land {
    private final int side;
    private final ArrayList<String> row = new ArrayList<>();
    private ArrayList<ArrayList<Object>> board;
    private final Human player;
    private final Goblin enemyOne;
    private final Goblin enemyTwo;
    private final Goblin enemyThree;
    private final Goblin enemyFour;
    private final Goblin enemyFive;


   public Land(Human player, Goblin...enemies){
       side = 5;
       this.player = player;
       this.enemyOne = enemies[0];
       this.enemyTwo = enemies[1];
       this.enemyThree = enemies[2];
       this.enemyFour = enemies[3];
       this.enemyFive = enemies[4];
       createBoard();
   }

   public void createBoard(){
       board = new ArrayList<>(side);
       for(int i = 0; i < side; i++){

           for(int j = 0; j < side; j++){
               row.add("#");
           }

        board.add(new ArrayList<>(row));
        row.clear();
       }
   }

    @Override
    public String toString() {
       String display = "";

        for (ArrayList<Object> strings : board) {
            display += strings + "\n";
        }

        return display;
    }

    public void setPlayerStart(){
        board.get(0).set(0, player);
    }

    public void setGoblins(){
       board.get(0).set(4, enemyOne);
       board.get(4).set(0, enemyTwo);
       board.get(4).set(4, enemyThree);
       board.get(3).set(2, enemyFour);
       board.get(2).set(3, enemyFive);
    }

    public int[] getCurrentPos(Object object){
       int[] pos = new int[2];

       for(int i = 0; i < board.size(); i++){
           if(board.get(i).contains(object)){
               pos[0] = i;
               pos[1] = board.get(i).indexOf(object);
           }
       }
       return pos;
    }

    public void move(Object object, String direction){
        int[] piecePos = getCurrentPos(object);
        int outer = piecePos[0];
        int inner = piecePos[1];

       if(checkDir(direction, inner, outer)){
           updatePos(object,direction,inner,outer);
       } else{
           battle(getEnemy(direction, inner, outer));
       }
    }

    private void updatePos(Object object, String direction,int inner,int outer){
     try{
         board.get(outer).set(inner, "#");
         switch (direction){
             case "e":
                 board.get(outer).set(inner + 1, object);
                 break;
             case "w":
                 board.get(outer).set(inner - 1, object);
                 break;
             case "n":
                 board.get(outer - 1).set(inner, object);
                 break;
             case "s":
                 board.get(outer + 1).set(inner, object);
                 break;
             default:
                 System.out.println("Something went wrong");
         }
     } catch(Exception e){
         board.get(outer).set(inner, object);
     }
    }

    public boolean checkDir(String direction, int inner, int outer){
       boolean isPresent;
       try{
           switch (direction){
               case "e":
                   isPresent = board.get(outer).get(inner + 1).getClass().getSimpleName().equals("String");
                   break;
               case "w":
                   isPresent = board.get(outer).get(inner - 1).getClass().getSimpleName().equals("String");
                   break;
               case "n":
                   isPresent = board.get(outer - 1).get(inner).getClass().getSimpleName().equals("String");
                   break;
               case "s":
                   isPresent = board.get(outer + 1).get(inner).getClass().getSimpleName().equals("String");
                   break;
               default:
                   isPresent = false;
           }
           return isPresent;
       } catch (Exception e){
           return true;
       }

    }

    public Goblin getEnemy(String direction, int inner,int outer){
       Goblin enemy = enemyOne;

        switch (direction){
            case "e":
                return (Goblin) board.get(outer).get(inner + 1);
            case "w":
                return (Goblin) board.get(outer).get(inner - 1);
            case "n":
                return (Goblin) board.get(outer - 1).get(inner);
            case "s":
                return (Goblin) board.get(outer + 1).get(inner);
            default:
                System.out.println("Something went wrong.");
        }
        return enemy;
    }

    public void battle(Object object){
        int[] piecePos;

        System.out.println("Battle start!!!");
        player.attack((Goblin) object);

        if(((Goblin) object).getHealth() <= 0){
            System.out.println("You have slain a goblin!");
            piecePos = getCurrentPos((Goblin) object);
            board.get(piecePos[0]).set(piecePos[1], "#");
        } else {
            ((Goblin) object).attack(player);
        }

        if(player.getHealth() <= 0){
            System.out.println("You have been slain.\nGame Over!");
            System.exit(0);
        }

    }
}

