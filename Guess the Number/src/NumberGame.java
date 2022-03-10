import java.util.Scanner;
import java.util.Random;

public class NumberGame {
    private static String playerName;
    private static int numberOfGuesses;
    private static int targetNumber;
    private static int guessedNumber;

    public static void main(String[] args) {
        System.out.println("Hello! What is your name?");
        playerName = getPlayerName();
        gameStart();

    }

    // get player input includes try & catch block
    public static String getPlayerName(){
        try {
            Scanner nameObj = new Scanner(System.in);
            return nameObj.nextLine();
        } catch (Exception e){
            System.out.println("Something went wrong. Please restart application");
            System.exit(0);
        }
        return "Player";
    }

    public static int getGuess(){
        try{
            Scanner guessObj = new Scanner(System.in);
            String guess = guessObj.nextLine();
            guessedNumber = Integer.parseInt(guess);
            return guessedNumber;
        } catch(Exception e){
            System.out.println("That input is not a valid choice. Please enter a number.");
            getGuess();
        }
        return guessedNumber;
    }

    // Check to see if player lost
    public static void checkNumberOfGuesses(){
        if(numberOfGuesses == 0){
            displayLoser();
        }
    }

    //Check player input
    public static void checkGuess(int guess){
        //System.out.println(guess);
        if(guess == targetNumber){
            displayWinner();
        } else if(guess < targetNumber){
            System.out.println("Your guess is too low. Number of guesses reminding: " + (numberOfGuesses - 1) + ", take another guess: ");
        } else if(guess > targetNumber){
            System.out.println("Your guess is too high. Number of guesses reminding: " + (numberOfGuesses - 1) + ", take another guess: ");
        }
    }

    //Starts the game
    public static void gameStart(){
        targetNumber = createTarget();
        numberOfGuesses = 6;

        //System.out.println(targetNumber);
        System.out.println("Well, " + playerName + ", I am thinking of a number between 1 and 20. Take a guess.");

        while(numberOfGuesses > 0 && targetNumber != guessedNumber){
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
        endGame();
    }

    public static void displayWinner(){
        System.out.println("Good job! " + playerName + "! You guessed my number with " + numberOfGuesses + " guesses left! Would you like to play again? (y or n)");
        endGame();
    }

    public static void endGame(){
        try {
            Scanner command = new Scanner(System.in);
            String yayNay = command.nextLine();

            if (yayNay.equals("y")) {
                gameStart();
            } else if (yayNay.equals("n")) {
                System.exit(0);
            } else {
                System.out.println("Please enter valid command: y or n");
                endGame();
            }
        } catch(Exception e){
            System.out.print(e.getMessage());
        }
    }

}


