import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Application {
    static Human player = new Human();
    static Goblin goblin = new Goblin();
    static Goblin goblinOne = new Goblin();
    static Goblin goblinTwo = new Goblin();
    static Goblin goblinThree = new Goblin();
    static Goblin goblinFour = new Goblin();
    static Land board = new Land(player, goblin, goblinOne, goblinTwo, goblinThree, goblinFour);
    static int goblins = 5;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        gameStart();
    }

    public static void gameStart(){
        board.setPlayerStart();
        board.setGoblins();
        while(goblins > 0){
            System.out.println(board);
            System.out.println("use n/w/s/e to move: ");
            board.move(goblin,input.nextLine());
            System.out.println(player.getHealth());
        }
    }

    public static String gMove(){
        ArrayList<String> dir = new ArrayList<>(Arrays.asList("n","w","s","e"));
        Collections.shuffle(dir);
        return dir.get(0);
    }
}
