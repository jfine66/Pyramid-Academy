import java.util.ArrayList;


public class Land {
    private final int side;
    private final ArrayList<String> row = new ArrayList<>();
    private ArrayList<ArrayList<Object>> board;


    public Land() {
        side = 5;
        createBoard();
    }

    public void createBoard() {
        board = new ArrayList<>(side);
        for (int i = 0; i < side; i++) {

            for (int j = 0; j < side; j++) {
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

    public void setPlayerStart(Object object) {
        board.get(0).set(0, object);
    }

    public void setGoblins(Object... goblins) {
        board.get(0).set(4, goblins[0]);
        board.get(4).set(0, goblins[1]);
        board.get(4).set(4, goblins[2]);
        board.get(3).set(2, goblins[3]);
        board.get(2).set(3, goblins[4]);
    }

    public int[] getCurrentPos(Object object) {
        int[] pos = new int[2];

        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).contains(object)) {
                pos[0] = i;
                pos[1] = board.get(i).indexOf(object);
            }
        }
        return pos;
    }

    public ArrayList<Object> getColumn(int col) {
        return board.get(col);
    }

    public Object getRow(int col, int row) {
        return board.get(col).get(row);
    }

    public void setPos(Object object, int col, int row) {
        board.get(col).set(row, object);
    }
}




