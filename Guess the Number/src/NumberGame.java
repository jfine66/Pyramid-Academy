import java.util.Scanner;
import java.util.Random;

public class NumberGame {
    private static String playerName;
    private static int numberOfGuesses;
    private static int targetNumber;

    public static void main(String[] args) {
        System.out.println("Hello! What is your name?");
        playerName = getPlayerName();
        gameStart();

    }

    // get player input
    public static String getPlayerName(){
        Scanner nameObj = new Scanner(System.in);
        return nameObj.nextLine();
    }

    public static String getGuess(){
        Scanner guessObj = new Scanner(System.in);
        return guessObj.nextLine();
    }

    // Check player input
    public static void checkNumberOfGuesses(){
        if(numberOfGuesses == 0){
            displayLoser();
        }
    }

    public static void checkGuess(String guess){
        int guessNumber = Integer.parseInt(guess);
        if(guessNumber == targetNumber){
            displayWinner();
        } else if(guessNumber < targetNumber){
            System.out.println("Your guess is too low. Number of guesses reminding: " + (numberOfGuesses - 1) + ", take another guess: ");
        } else if(guessNumber > targetNumber){
            System.out.println("Your guess is too high. Number of guesses reminding: " + (numberOfGuesses - 1) + ", take another guess: ");
        }
    }

    //Starts the game
    public static void gameStart(){
        targetNumber = createTarget();
        numberOfGuesses = 6;

        System.out.println("Well, " + playerName + ", I am thinking of a number between 1 and 20. Take a guess.");

        while(numberOfGuesses > 0){
            checkGuess(getGuess());
            numberOfGuesses--;
            checkNumberOfGuesses();
        }
    }


    //Create The Numbers of the Game
    public static int createTarget(){
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9,10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        Random rand = new Random();
        int index = rand.nextInt(numbers.length);

        return numbers[index];
    }

    //Outcomes of the game
    public static void displayLoser(){
        System.out.println("Uh-oh, it looks like you ran out of guesses. You Lose. Would you like to play again? (y or n)");
        Scanner loseInputObj = new Scanner(System.in);
        endGame(loseInputObj.nextLine());
    }

    public static void displayWinner(){
        System.out.println("Good job! " + playerName + "! You guessed my number with " + numberOfGuesses + " guesses left! Would you like to play again? (y or n)");
        Scanner inputObj = new Scanner(System.in);
        endGame(inputObj.nextLine());
    }

    public static void endGame(String yayNay){
        Scanner command = new Scanner(System.in);

        if(yayNay.equals("y")){
            gameStart();
        } else if(yayNay.equals("n")){
            System.exit(0);
        } else {
            System.out.println("Please enter valid command: ");
            endGame(command.nextLine());
        }
    }






}


