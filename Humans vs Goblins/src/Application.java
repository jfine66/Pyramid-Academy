public class Application {
    static Land board = new Land(3,3);
    static Human player = new Human(board);
    static Goblin goblin = new Goblin(board);

    public static void main(String[] args) {
        board.setPlayerStart();
        board.setGoblins();
        System.out.println(board);
        player.move("e");
        player.move("e");
        System.out.println(board);
    }
}
