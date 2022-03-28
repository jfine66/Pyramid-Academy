import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hangman {
    static Scanner fileRead;
    private static final Scanner input = new Scanner(System.in);
    private static int numGuesses;
    private static String targetWord;
    private static StringBuilder displayedWord;
    private static ArrayList<String> letters = new ArrayList<>();
    private static final ArrayList<String> words = new ArrayList<>();

    static {
        try {
            fileRead = new Scanner(new File("src/art.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Hangman() {
    }

    public static void main(String[] args) throws IOException {
        gameStart();
    }

    public static void gameStart() throws IOException {
        numGuesses = 6;
        letters = new ArrayList<>();
        targetWord = getTargetWord();
        displayedWord = new StringBuilder(targetWord.replaceAll(".", "-"));

        while(numGuesses > 0){
            updateGallows(numGuesses);
            System.out.println(displayedWord);
            System.out.println("Please enter your guess: ");
            checkGuess(input.nextLine());
            result();
        }
    }

    public static String getTargetWord() throws IOException {
        Stream<String> stream = Files.lines(Paths.get("src/words.txt"));
        stream.forEach(words::add);

        Collections.shuffle(words);
        Random rand = new Random();

        return words.get(rand.nextInt(words.size()));
    }

    public static void checkGuess(String guess){
        try{
            checkLetter(guess);

            if(targetWord.contains(guess)){
                ArrayList<String> myList = new ArrayList<>(Arrays.asList(targetWord.split("")));
                ArrayList<String> displayList = new ArrayList<>(Arrays.asList(displayedWord.toString().split("")));
                ArrayList<String> finalDisplayList = displayList;

                displayList = (ArrayList<String>) myList.stream()
                        .map(letter -> finalDisplayList.contains(letter) ? letter : guess.equals(letter) ? guess : "-")
                        .collect(Collectors.toList());

                displayedWord = new StringBuilder(String.join("", displayList));

            } else {
                numGuesses--;
            }
        } catch (Exception e){
            System.out.println("Something went wrong please restart application.");
            System.exit(0);
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

    public static void updateGallows(int guessLeft) throws IOException {
        switch(guessLeft){
            case 6:
                Files.lines(Paths.get("src/art.txt")).limit(5).forEach(System.out::println);
                break;
            case 5:
                Files.lines(Paths.get("src/art.txt")).skip(5).limit(5).forEach(System.out::println);
                break;
            case 4:
                Files.lines(Paths.get("src/art.txt")).skip(10).limit(5).forEach(System.out::println);
                break;
            case 3:
                Files.lines(Paths.get("src/art.txt")).skip(15).limit(5).forEach(System.out::println);
                break;
            case 2:
                Files.lines(Paths.get("src/art.txt")).skip(20).limit(5).forEach(System.out::println);
                break;
            case 1:
                Files.lines(Paths.get("src/art.txt")).skip(25).limit(5).forEach(System.out::println);
                break;
            case 0:
                Files.lines(Paths.get("src/art.txt")).skip(30).limit(5).forEach(System.out::println);
                break;
            default:
                System.out.println("PROBLEM!!!");
        }
    }

    public static void result() throws IOException {
        if(displayedWord.toString().equals(targetWord)){
            System.out.println("You win!!! Would you like to play again? y/n");
            playAgain(input.nextLine());
        } else if(numGuesses == 0){
           updateGallows(numGuesses);
            System.out.println("You lose! Too bad! So sad! The word was: " + targetWord + " Would you like to play again? y/n");
            playAgain(input.nextLine());
        }
    }

    public static void playAgain(String yayNay) throws IOException {
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

