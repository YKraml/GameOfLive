package runnables;

import main.Main;
import model.GameOfLife;
import runnables.MyLoopRunnable;


public class MakeRoundRunnable extends MyLoopRunnable {

    private final GameOfLife gameOfLife;

    public MakeRoundRunnable(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }

    @Override
    protected long getWaitTimeInNano() {
        return Main.getTimeBetweenUpdatesInNano();
    }

    @Override
    protected void toToInLoop() {
        Main.getCalculatedRounds().addNumber(1);
        if (!Main.isStop()) {
            gameOfLife.makeRound();
        }
    }

}


