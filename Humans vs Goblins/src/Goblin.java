public class Goblin {
    private int health = 7;
    private int strength = 5;


    public Goblin(){

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

    public void attack(Human object){
        int atk = (int) Math.floor(Math.random() * strength + 1);
        object.setHealth(object.getHealth() - atk);
        System.out.println("Goblin attacked player for " + atk + " damage");
    }

    @Override
    public String toString() {
        return "G";
    }
}
