import java.util.Scanner;

public class Dragon {
    public static void main(String[] args) {
        Prologue();
    }

    public static void Prologue(){
        System.out.println("You are in a land full of dragons. In front of you,");
        System.out.println("you see two caves. In one cave, the dragon is friendly");
        System.out.println("and will share his treasure with you. The other dragon");
        System.out.println("is greedy and hungry and will eat you on sight");
        System.out.println("Which cave will you go into? Enter 1 or 2");
        userInput();
    }

    public static void userInput(){
        Scanner myObj = new Scanner(System.in);
        String choice = myObj.next();
        outcome(choice);
    }

    public static void outcome(String choice){
        if(choice.equals("1")){
            System.out.println("You approach the cave...");
            System.out.println("It is dark and spooky...");
            System.out.println("A large dragon jumps out in front of you! He opens his jaws and...");
            System.out.println("Gobbles you down in one bite!");
        } else if(choice.equals("2")){
            System.out.println("You approach the cave...");
            System.out.println("You hear a sweet and soft melody");
            System.out.println("A large dragon appears in front of you. It takes a moment to take your measure");
            System.out.println("You seemed to have impressed her, she offers you have of her treasure");
        } else {
            System.out.println("Please enter valid command: ");
            userInput();
        }
    }



}
