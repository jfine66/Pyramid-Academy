public class Land {
    private static int area;
    private static int side;
    private static StringBuilder board = new StringBuilder("");


   public Land(int width, int height){
       area = width * height;
       side = width;
       createBoard();
   }

   public void createBoard(){
       for(int i = 0; i < area; i++){
           if(i % side == 0){
               board.append("\n");
           }
           board.append("[]");
       }
   }

   public void defaultPosition(Human player){
       board.replace(1,3, player.toString());
   }

   public int getCurrentLocation(Human player){
       return board.indexOf(player.toString());
   }

   public void setLocation(int start, int end, Human player){
       board.replace(start,end,player.toString());
   }

   public void resetSquare(int start, int end){
       board.replace(start, end, "[]");
   }

    @Override
    public String toString() {
        return board.toString();
    }
}
