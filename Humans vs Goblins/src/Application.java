import java.util.Scanner;


public class Application {
    static Human player = new Human();
    static Goblin goblin = new Goblin();
    static Goblin goblinOne = new Goblin();
    static Goblin goblinTwo = new Goblin();
    static Goblin goblinThree = new Goblin();
    static Goblin goblinFour = new Goblin();
    static Land board = new Land();
    static GameLogic game = new GameLogic(board, player, goblin, goblinOne, goblinTwo, goblinThree, goblinFour);
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
      gameStart();
    }

    public static void gameStart(){
        game.defaultPos();
        while(game.goblinsAlive()){
            game.display();
            System.out.println("Player Turn!\nMove with n/e/s/w: ");
            game.playerTurn(input.nextLine());
            game.goblinsTurn();
            System.out.println("Careful the goblins have made their move!");
        }
        System.out.println("YOU WIN!!! THE MOST FEARSOME WARRIOR I HAVE EVERY SEEN");
        System.out.println("Would you like to play again?");
        playAgain(input.nextLine());

    }

    public static void playAgain(String yayNay){
        if(yayNay.equals("n")){
            System.exit(0);
        } else if(yayNay.equals("y")){
            gameStart();
        } else {
            System.out.println("Please enter valid choice: ");
            playAgain(input.nextLine());
        }
    }
}
