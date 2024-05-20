package runnables;

import main.IntWrapper;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class UpdateLabelsRunnable extends MyLoopRunnable {


    private final Map<JLabel, ?> labels;
    private int counter;

    public UpdateLabelsRunnable(Map<JLabel, ?> labels) {
        this.labels = labels;
    }

    @Override
    protected long getWaitTimeInNano() {
        return 1000 * 1000 * 100;
    }

    @Override
    protected void toToInLoop() {
        counter++;

        //Values, every second
        if (counter % 10 == 0) {
            labels.forEach((jLabel, value) -> {
                if (value instanceof IntWrapper intWrapper) {
                    jLabel.setText(value.toString());
                    intWrapper.setNumber(0);
                }
            });
        }

        //Mouse, every 100 ms;
        labels.forEach((jLabel, value) -> {
            if (value instanceof Point point) {
                jLabel.setText("(" + point.x + "|" + point.y + ")");
            }
        });

    }
}
