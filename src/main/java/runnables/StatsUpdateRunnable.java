package runnables;

import main.Main;
import model.GameOfLife;

import javax.swing.*;
import java.awt.*;

public class StatsUpdateRunnable extends MyLoopRunnable {


    private final JLabel lastCalculatedRoundsLabel;
    private final JLabel updatedAmountLabel;
    private final JLabel checkedAmountLabel;
    private final JLabel lastCalculatedFpsLabel;
    private final GameOfLife gameOfLife;

    public StatsUpdateRunnable(JLabel lastCalculatedRoundsLabel, JLabel updatedAmountLabel, JLabel checkedAmountLabel, JLabel lastCalculatedFpsLabel, GameOfLife gameOfLife) {
        this.lastCalculatedRoundsLabel = lastCalculatedRoundsLabel;
        this.updatedAmountLabel = updatedAmountLabel;
        this.checkedAmountLabel = checkedAmountLabel;
        this.lastCalculatedFpsLabel = lastCalculatedFpsLabel;
        this.gameOfLife = gameOfLife;
    }

    @Override
    protected long getWaitTimeInNano() {
        return 1000 * 1000 * 1000;
    }

    @Override
    protected void toToInLoop() {

        lastCalculatedRoundsLabel.setText(String.valueOf(Main.getCalculatedRounds()));
        updatedAmountLabel.setText(String.valueOf(gameOfLife.getUpdatedAmount()));
        checkedAmountLabel.setText(String.valueOf(gameOfLife.getCheckedAmount()));
        lastCalculatedFpsLabel.setText(String.valueOf(Main.getCalculatedFps()));

        Main.setCalculatedRounds(0);
        Main.setCalculatedFps(0);
    }
}
