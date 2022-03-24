import java.util.ArrayList;


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
       StringBuilder display = new StringBuilder();

        for (ArrayList<Object> strings : board) {
            display.append(strings).append("\n");
        }

        return display.toString();
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

        updatePos(object, direction, inner,outer);
    }


    private void updatePos(Object object, String direction,int inner,int outer){
         boolean isHuman = object.getClass().getSimpleName().equals("Human");
         boolean isGoblin = object.getClass().getSimpleName().equals("Goblin");

         try{
             board.get(outer).set(inner, "#");
             switch (direction){
                 case "e":
                     if(board.get(outer).get(inner + 1).getClass().getSimpleName().equals("String")){
                         board.get(outer).set(inner + 1, object);

                     } else if(isHuman && board.get(outer).get(inner + 1).getClass().getSimpleName().equals("Goblin") ){
                         battle(board.get(outer).get(inner + 1));
                         board.get(outer).set(inner, object);

                     } else if(isGoblin && board.get(outer).get(inner + 1).getClass().getSimpleName().equals("Human")) {
                         battle(object);
                         //((Goblin) object).attack((Human) board.get(outer).get(inner + 1));
                         board.get(outer).set(inner, object);

                     } else {
                         System.out.println("Catch");
                         board.get(outer).set(inner, object);
                     }
                     break;
                 case "w":
                     if(board.get(outer).get(inner - 1).getClass().getSimpleName().equals("String")){
                         board.get(outer).set(inner - 1, object);

                     } else if(isHuman && board.get(outer).get(inner - 1).getClass().getSimpleName().equals("Goblin")){
                         battle(board.get(outer).get(inner - 1));
                         board.get(outer).set(inner, object);

                     } else if(isGoblin && board.get(outer).get(inner - 1).getClass().getSimpleName().equals("Human")){
                         battle(board.get(outer).get(inner - 1));
                         //((Goblin) object).attack((Human) board.get(outer).get(inner - 1));
                         board.get(outer).set(inner, object);

                     } else{
                         board.get(outer).set(inner, object);
                     }
                     break;
                 case "n":
                     if(board.get(outer - 1).get(inner).getClass().getSimpleName().equals("String")){
                         board.get(outer - 1).set(inner, object);

                     } else if(isHuman && board.get(outer - 1).get(inner).getClass().getSimpleName().equals("Goblin")){
                         ((Human) object).attack((Goblin) board.get(outer - 1).get(inner));
                         board.get(outer).set(inner, object);

                     } else if(isGoblin && board.get(outer - 1).get(inner).getClass().getSimpleName().equals("Human")){
                         ((Goblin) object).attack((Human) board.get(outer - 1).get(inner));
                         board.get(outer).set(inner, object);

                     } else{
                         board.get(outer).set(inner, object);
                     }
                     break;
                 case "s":
                     if(board.get(outer + 1).get(inner).getClass().getSimpleName().equals("String")){
                         board.get(outer + 1).set(inner, object);

                     } else if(isHuman && board.get(outer + 1).get(inner).getClass().getSimpleName().equals("Goblin")){
                         ((Human) object).attack((Goblin) board.get(outer + 1).get(inner));
                         board.get(outer).set(inner, object);

                     } else if(isGoblin && board.get(outer + 1).get(inner).getClass().getSimpleName().equals("Human")){
                         ((Goblin) object).attack((Human) board.get(outer + 1).get(inner));
                         board.get(outer).set(inner, object);

                     } else {
                         board.get(outer).set(inner, object);
                     }
                     break;
                 default:
                     board.get(outer).set(inner, object);
             }
         } catch(Exception e){
             board.get(outer).set(inner, object);
         }
    }



    public void battle(Object object){
        int[] piecePos;

        if(player.getHealth() <= 0){
            System.out.println("You have been slain.\nGame Over!");
            System.exit(0);
        }

        System.out.println("Battle start!!!");
        player.attack((Goblin) object);

        if(((Goblin) object).getHealth() <= 0){
            piecePos = getCurrentPos(object);
            board.get(piecePos[0]).set(piecePos[1], "#");
            System.out.println("You have slain a goblin!");
        } else {
            ((Goblin) object).attack(player);
        }
    }
}

