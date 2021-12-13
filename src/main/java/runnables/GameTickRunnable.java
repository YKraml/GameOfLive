package runnables;

import main.Main;
import model.GameOfLife;
import runnables.MyLoopRunnable;


public class GameTickRunnable extends MyLoopRunnable {

    private final GameOfLife gameOfLife;

    public GameTickRunnable(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }

    @Override
    protected long getWaitTimeInNano() {
        return Main.getTimeBetweenUpdatesInNano();
    }

    @Override
    protected void toToInLoop() {
        Main.setCalculatedRounds(Main.getCalculatedRounds() + 1);
        if (!Main.isStop()) {
            gameOfLife.makeRound();
        }
    }

}


