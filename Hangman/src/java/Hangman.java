import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class Hangman {
    private static final Scanner input = new Scanner(System.in);
    private static int numGuesses;
    private static StringBuilder gallows;
    private static String targetWord;
    private static StringBuilder displayedWord;
    private static ArrayList<String> letters;
    private static ArrayList<String> words = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        gameStart();
    }

    public static void gameStart() throws FileNotFoundException {
        numGuesses = 6;
        letters = new ArrayList<>();
        gallows = new StringBuilder(" +---+\n    | \n    | \n    | \n ===");
        targetWord = getTargetWord();
        displayedWord = new StringBuilder(targetWord.replaceAll(".", "_"));

        while(numGuesses > 0){
            display();
            System.out.println("Please enter your guess: ");
            checkGuess(input.nextLine());
            result();
        }
    }

    public static String getTargetWord() throws FileNotFoundException {
        //targetWord = "cat";
        Scanner scanner = new Scanner(new File("src/java/words.txt"));
        while(scanner.hasNext()){
            words.add(scanner.nextLine());
        }

        Collections.shuffle(words);

        Random rand = new Random();

        return words.get(rand.nextInt(words.size()));
    }


    public static void display(){
        System.out.println(gallows);
        System.out.println(displayedWord);
    }

    public static void checkGuess(String guess){
        checkLetter(guess);

        if(targetWord.contains(guess)){

         for(int index = targetWord.indexOf(guess); index >= 0 ; index = targetWord.indexOf(guess, index + 1)){
             displayedWord.setCharAt(index, guess.charAt(0));
         }

        } else {
            numGuesses--;
            updateGallows(numGuesses);
        }
    }

    public static void checkLetter(String letter){
        if(letters.contains(letter)){
            System.out.println("You already guesses that letter, please enter another: ");
            numGuesses++;
            checkGuess(input.nextLine());
        } else{
            letters.add(letter);
        }

    }

    public static void updateGallows(int guessesLeft){
        switch (guessesLeft){
            case 5:
                gallows.replace(8,9,"0");
                break;
            case 4:
                gallows.replace(15,16, "|");
                break;
            case 3:
                gallows.replace(14,15,"-");
                break;
            case 2:
                gallows.replace(16,17,"-");
                break;
            case 1:
                gallows.replace(21,22, "/");
                break;
            case 0:
                gallows.replace(23, 24, "\\");
                break;
            default:
        }
    }

    public static void result() throws FileNotFoundException {
        if(displayedWord.toString().equals(targetWord)){
            System.out.println("You win!!! Would you like to play again? y/n");
            playAgain(input.nextLine());
        } else if(numGuesses == 0){
            System.out.println(gallows);
            System.out.println("You lose! Too bad! So sad! The word was: " + targetWord + " Would you like to play again? y/n");
            playAgain(input.nextLine());
        }
    }

    public static void playAgain(String yayNay) throws FileNotFoundException {
        if(yayNay.equals("y")){
            gameStart();
        } else if(yayNay.equals("n")){
            System.exit(0);
        } else {
            System.out.println("Please enter a valid choice: ");
            playAgain(input.nextLine());
        }
    }
}
