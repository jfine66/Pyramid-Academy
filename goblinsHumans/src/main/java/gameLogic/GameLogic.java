package gameLogic;

import model.Goblin;
import model.Human;
import view.FirstLevel;

public class GameLogic {
    private Human player;
    private Goblin enemy;
    private FirstLevel levelOne;

    public GameLogic(Human player, Goblin enemy, FirstLevel one){
        this.player = player;
        this.enemy = enemy;
        this.levelOne = one;
        levelOne.setPosition(player, 64, 64);
    }


}
