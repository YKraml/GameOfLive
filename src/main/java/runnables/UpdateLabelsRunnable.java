package runnables;

import main.IntWrapper;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.function.BiConsumer;

public class UpdateLabelsRunnable extends MyLoopRunnable {


    private final Map<JLabel, ?> labels;

    public UpdateLabelsRunnable(Map<JLabel, ?> labels) {
        this.labels = labels;
    }

    @Override
    protected long getWaitTimeInNano() {
        return 1000 * 1000 * 1000;
    }

    @Override
    protected void toToInLoop() {

        labels.forEach((jLabel, intWrapper) -> {

            if (intWrapper instanceof IntWrapper) {
                jLabel.setText(intWrapper.toString());
                ((IntWrapper) intWrapper).setNumber(0);
            }
            if (intWrapper instanceof Point) {
                jLabel.setText("(" + ((Point) intWrapper).x + "|" + ((Point) intWrapper).y + ")");
            }
        });
    }
}
