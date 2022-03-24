public class Human {
    private int health = 12;
    private int strength = 10;

    public Human(){

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
        int atk = (int) Math.floor(Math.random() * strength + 1);
        object.setHealth(object.getHealth() - atk);
        System.out.println("You attacked goblin for " + atk + " damage");
    }


    @Override
    public String toString() {
        return "P";
    }
}
