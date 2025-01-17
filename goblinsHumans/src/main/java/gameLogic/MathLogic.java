package gameLogic;

import java.util.Random;

public class MathLogic {

    public MathLogic() {
    }

    public int getRandomX(){
        Random rand = new Random();
        int x = rand.nextInt(961);
        while(x % 64 != 0){
            x = rand.nextInt(961);
        }
        return x;
    }

    public int getRandomY(){
        Random rand = new Random();
        int y = rand.nextInt(705);
        while(y % 64 != 0){
            y = rand.nextInt(705);
        }
        return y;
    }

}
