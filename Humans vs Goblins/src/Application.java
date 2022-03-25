import java.util.Scanner;


public class Application {
    static Human player = new Human();
    static Goblin goblin = new Goblin();
    static Goblin goblinOne = new Goblin();
    static Goblin goblinTwo = new Goblin();
    static Goblin goblinThree = new Goblin();
    static Goblin goblinFour = new Goblin();
    static Land board = new Land();
    static int goblins = 5;
    static GameLogic game = new GameLogic(board, player, goblin, goblinOne, goblinTwo, goblinThree, goblinFour);
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
      gameStart();
    }

    public static void gameStart(){
        game.defaultPos();
        int test = 5;
        while(test > 0){
            game.display();
            System.out.println("Move with n/w/s/e: ");
            game.move(player, input.nextLine());
        }
    }
}
