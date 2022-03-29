import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Hangman {
    static Scanner fileRead;
    private static final Scanner input = new Scanner(System.in);
    private static int numGuesses;
    private static String targetWord;
    private static StringBuilder displayedWord;
    private static ArrayList<String> letters = new ArrayList<>();
    private static final ArrayList<String> words = new ArrayList<>();
    private static List<String> data;

    static {
        try {
            data = Files.readAllLines(Paths.get("src/scoreCard.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String playerName;
    private static int score;

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
        System.out.println("What is your name?");
        playerName = input.nextLine();
        getHighScore();

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

    public static List<String> updateScoreCard(){
        if(data.contains(playerName)){
            int localScore = Integer.parseInt(data.get(data.indexOf(playerName) + 1));
            score += localScore;
            data.set(data.indexOf(playerName) + 1, String.valueOf(score));
        } else{
            data.add(playerName);
            data.add(String.valueOf(score));
        }

        return data;
    }

    public static void getHighScore(){
        List<String> scores = IntStream.range(0, data.size())
                .filter(n -> n % 2 != 0).mapToObj(data::get).collect(Collectors.toList());

        ArrayList<Integer> scoreNumbers = new ArrayList<>();

        scores.forEach(line -> scoreNumbers.add(Integer.valueOf(line)));

        int highScore = Collections.max(scoreNumbers);

        int indexOfName = data.indexOf(String.valueOf(highScore)) - 1;

        System.out.println("The current high-score is " + highScore + ". It is held by " + data.get(indexOfName));
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


                score += 10;
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
            System.out.println("You're score will be kept track of if you press y but not updated in the file until you hit n");
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
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/scoreCard.txt"));
                updateScoreCard().forEach(line -> {
                    try {
                        writer.write(line + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            writer.close();
            System.exit(0);
        } else {
            System.out.println("Please enter a valid choice: ");
            playAgain(input.nextLine());
        }
    }
}

