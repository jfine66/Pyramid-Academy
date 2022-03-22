public class Human {
    private final Land board;



    public Human(Land board){
        this.board = board;
        board.defaultPosition(this);
    }

    @Override
    public String toString() {
        return "P ";
    }

    public void move(String direction){
        if(direction.equals("w")){
           int start = board.getCurrentLocation(this);
           int end = board.getCurrentLocation(this) + 2;
           board.resetSquare(start,end);
           board.setLocation(start + 2, end + 2, this);
        }
    }
}
