public class Human {
    private Land board;
    private String token = "P";
    private int health = 12;
    private int strength = 10;

    public Human(Land board){
        this.board = board;
    }

    public void move(String direction){
        try{
            switch (direction){
                case "e":
                    board.updatePos(token, board.getCurrentPos(token)[0],board.getCurrentPos(token)[1] + 1);
                    break;
                case "w":
                    board.updatePos(token, board.getCurrentPos(token)[0], board.getCurrentPos(token)[1] - 1);
                    break;
                case "s":
                    board.updatePos(token, board.getCurrentPos(token)[0] + 1, board.getCurrentPos(token)[1]);
                    break;
                case "n":
                    board.updatePos(token, board.getCurrentPos(token)[0] - 1, board.getCurrentPos(token)[1]);
                    break;
                default:
                    System.out.println("Wrong");
            }
        } catch (Exception e){
            System.out.println("invalid command");
        }
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public void attack(Goblin object){
        object.setHealth(object.getHealth() - strength);
    }

}
