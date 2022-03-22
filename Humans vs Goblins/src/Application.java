public class Application {
    static Land test = new Land(5,5);
    static Human player = new Human(test);

    public static void main(String[] args) {
        System.out.println(test);
        player.move("w");
        System.out.println("----");
        System.out.println(test);
    }
}
