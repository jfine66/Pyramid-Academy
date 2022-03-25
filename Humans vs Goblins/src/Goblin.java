public class Goblin {
    private int health = 7;


    public Goblin(){

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void attack(Human object){
        int strength = 5;
        int atk = (int) Math.floor(Math.random() * strength + 1);
        object.setHealth(object.getHealth() - atk);
        System.out.println("GOBLIN ATTACKED PLAYER FOR " + atk + " DAMAGE");
    }

    @Override
    public String toString() {
        return "G";
    }
}
