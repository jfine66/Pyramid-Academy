public class Land {
    private static int area;
    private static int side;
    private static StringBuilder board = new StringBuilder("");


   public Land(int width, int height){
       area = width * height;
       side = width;
       createBoard();
   }

    public static void main(String[] args) {

    }

   public void createBoard(){
       for(int i = 0; i < area; i++){
           if(i % side == 0){
               board.append("\n");
           }
           board.append("[]");
       }
   }

    @Override
    public String toString() {
        return board.toString();
    }
}
