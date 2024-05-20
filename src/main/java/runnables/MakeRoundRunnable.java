package runnables;

import main.Main;
import model.AbstractGameOfLife;


public class MakeRoundRunnable extends MyLoopRunnable {

    private final AbstractGameOfLife gameOfLife;

    public MakeRoundRunnable(AbstractGameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }

    @Override
    protected long getWaitTimeInNano() {
        return Main.getTimeBetweenUpdatesInNano();
    }

    @Override
    protected void toToInLoop() {
        Main.getCalculatedRounds().addOne();
        if (!Main.isStop()) {
            gameOfLife.makeRound();
        }
    }
}


