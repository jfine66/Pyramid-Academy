import java.util.ArrayList;

public class Land {
    private static int area;
    private static int side;
    private static ArrayList<String> row = new ArrayList<>();
    private static ArrayList<ArrayList<String>> board;


   public Land(int width, int height){
       area = width * height;
       side = height;
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

        for (ArrayList<String> strings : board) {
            display += strings + "\n";
        }

        return display;
    }

    public void setPlayerStart(){
        board.get(0).set(0, "P");
    }

    public void setGoblins(){
       int outer = board.size() - 1;

       board.get(0).set(side - 1, "G");
    }

    public int[] getCurrentPos(String token){
       int[] pos = new int[2];

       for(int i = 0; i < board.size(); i++){
           if(board.get(i).contains(token)){
               pos[0] = i;
               pos[1] = board.get(i).indexOf(token);
           }
       }
       return pos;
    }

    public void updatePos(String token, int outer, int inner){
       int[] playerPos = getCurrentPos(token);
       if(board.get(outer).get(inner).equals("#")){
           board.get(playerPos[0]).set(playerPos[1], "#");
           board.get(outer).set(inner, token);
       } else{
           System.out.println("Battle start!!!");
       }
    }
}
