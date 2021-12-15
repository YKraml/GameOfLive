package runnables;

import main.IntWrapper;
import main.Main;

import javax.swing.*;
import java.util.Map;
import java.util.function.BiConsumer;

public class UpdateLabelsRunnable extends MyLoopRunnable {


    private final Map<JLabel, IntWrapper> labels;

    public UpdateLabelsRunnable(Map<JLabel, IntWrapper> labels) {
        this.labels = labels;
    }

    @Override
    protected long getWaitTimeInNano() {
        return 1000 * 1000 * 1000;
    }

    @Override
    protected void toToInLoop() {

        labels.forEach((jLabel, intWrapper) -> {
            jLabel.setText(intWrapper.toString());
            intWrapper.setNumber(0);
        });
    }
}
