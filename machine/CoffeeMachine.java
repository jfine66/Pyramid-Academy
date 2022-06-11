package machine;

import java.util.Scanner;

public class CoffeeMachine {
    static Scanner scanner = new Scanner(System.in);

    static int baseWater = 400;
    static int baseMilk = 540;
    static int baseCoffeeBeans = 120;
    static int baseMoney = 550;
    static int disposableCups = 9;


    public static void main(String[] args) {
        activeCoffeeMachine();
    }

    public static void activeCoffeeMachine(){
        System.out.println("Write action (buy, fill, take, remaining, exit):");

        String input = scanner.nextLine();

        switch (input) {
            case "buy":
                buy();
                activeCoffeeMachine();
                break;
            case "fill":
                fill();
                activeCoffeeMachine();
                break;
            case "take":
                take();
                activeCoffeeMachine();
                break;
            case "remaining":
                getMachineStats();
                activeCoffeeMachine();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Not valid");
                break;
        }
    }

    public static void getMachineStats(){
        System.out.println("The coffee machine has:");
        System.out.println(baseWater + " ml of water");
        System.out.println(baseMilk + " ml of milk");
        System.out.println(baseCoffeeBeans + " g of coffee beans");
        System.out.println(disposableCups + " disposable cups");
        System.out.println("$" + baseMoney + " of money");
    }


    public static void fill(){
        System.out.println("Write how many ml of water you want to add:");
        baseWater += scanner.nextInt();

        System.out.println("Write how many ml of milk you want to add:");
        baseMilk += scanner.nextInt();

        System.out.println("Write how many grams of coffee beans you want to add:");
        baseCoffeeBeans += scanner.nextInt();

        System.out.println("Write how many disposable cups of coffee you want to add:");
        disposableCups += scanner.nextInt();

        scanner.nextLine();
    }

    public static void take(){
        System.out.println("I gave you $" + baseMoney);
        baseMoney = 0;

    }

    public static void buy(){
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                makeEspresso();
                break;
            case "2":
                makeLatte();
                break;
            case "3":
                makeCappuccino();
                break;
            default:
                System.out.println("Invalid entry");
                break;
        }
    }

    public static void makeEspresso() {
        if (disposableCups == 0) {
            System.out.println("Sorry, not enough cups!");
            return;
        }

        if (baseWater < 250) {
            System.out.println("Sorry, not enough water!");
            return;
        }

        if (baseCoffeeBeans < 16) {
            System.out.println("Sorry, not enough coffee beans!");
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");

        disposableCups -= 1;
        baseWater -= 250;
        baseCoffeeBeans -= 16;
        baseMoney += 4;
    }

    public static void makeLatte(){
        if (disposableCups == 0) {
            System.out.println("Sorry, not enough cups!");
            return;
        }

        if (baseWater < 350) {
            System.out.println("Sorry, not enough water!");
            return;
        }

        if (baseMilk < 75) {
            System.out.println("Sorry, not enough milk!");
            return;
        }

        if (baseCoffeeBeans < 20) {
            System.out.println("Sorry, not enough coffee beans!");
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");

        disposableCups -= 1;
        baseWater -= 350;
        baseMilk -= 75;
        baseCoffeeBeans -= 20;
        baseMoney += 7;
    }

    public static void makeCappuccino(){
        if (disposableCups == 0) {
            System.out.println("Sorry, not enough cups!");
            return;
        }

        if (baseWater < 200) {
            System.out.println("Sorry, not enough water!");
            return;
        }

        if (baseMilk < 100) {
            System.out.println("Sorry, not enough milk!");
            return;
        }

        if (baseCoffeeBeans < 12) {
            System.out.println("Sorry, not enough coffee beans!");
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");

        disposableCups -= 1;
        baseWater -= 200;
        baseMilk -= 100;
        baseCoffeeBeans -= 12;
        baseMoney += 6;
    }
}
