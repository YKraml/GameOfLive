package frame;

import main.Main;
import model.GameOfLife;
import runnables.StatsUpdateRunnable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SouthPanel extends MyPanel {

    private final GameOfLife gameOfLife;
    private final List<JComponent> componentsToDraw;

    public SouthPanel(GameOfLife gameOfLife){
        this.componentsToDraw = Collections.synchronizedList(new ArrayList<>());
        this.gameOfLife = gameOfLife;
    }

    public void init(){

        //StatsPanel
        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(BorderFactory.createTitledBorder("Stats"));
        statsPanel.setLayout(new GridLayout(4, 2));
        JLabel lastCalculatedRoundsLabel = new JLabel(String.valueOf(Main.getCalculatedRounds()));
        JLabel lastCalculatedFpsLabel = new JLabel(String.valueOf(Main.getCalculatedFps()));
        JLabel updatedAmountLabel = new JLabel(String.valueOf(gameOfLife.getUpdatedAmount()));
        JLabel checkedAmountLabel = new JLabel(String.valueOf(gameOfLife.getCheckedAmount()));
        statsPanel.add(new JLabel("Frames per Second: "));
        statsPanel.add(lastCalculatedFpsLabel);
        statsPanel.add(new JLabel("Updates per Second: "));
        statsPanel.add(lastCalculatedRoundsLabel);
        statsPanel.add(new JLabel("Cell-Updates per Tick: "));
        statsPanel.add(updatedAmountLabel);
        statsPanel.add(new JLabel("Cell-Checked per Tick: "));
        statsPanel.add(checkedAmountLabel);

        //ShuffleButton
        JButton button = new JButton("Shuffle");
        button.addActionListener(e -> gameOfLife.shuffle());

        //StopButton
        JButton stopButton = new JButton("Start | Stop");
        stopButton.addActionListener(e -> Main.setStop(!Main.isStop()));

        //ClearButton
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> gameOfLife.clear());

        //FpsSlider
        JSlider fpsSlider = new JSlider(0, 150, 30);
        fpsSlider.setMajorTickSpacing(30);
        fpsSlider.setMinorTickSpacing(5);
        fpsSlider.setPaintTicks(true);
        fpsSlider.setPaintLabels(true);
        fpsSlider.setSnapToTicks(true);
        fpsSlider.addChangeListener(e -> {
            JSlider source = ((JSlider) e.getSource());
            if (source.getValueIsAdjusting()) {
                return;
            }
            double value = source.getValue();
            if (value == 0) {
                value = 1;
            }
            Main.setTimeBetweenUpdatesInNano((long) (1000000000L / value));
        });


        //SouthPanel
        this.setBorder(BorderFactory.createTitledBorder("South"));
        this.add(statsPanel);
        this.add(button);
        this.add(stopButton);
        this.add(clearButton);
        this.add(fpsSlider);


        this.componentsToDraw.add(checkedAmountLabel);
        this.componentsToDraw.add(updatedAmountLabel);
        this.componentsToDraw.add(lastCalculatedRoundsLabel);
        this.componentsToDraw.add(lastCalculatedFpsLabel);


        new Thread(new StatsUpdateRunnable(lastCalculatedRoundsLabel, updatedAmountLabel, checkedAmountLabel, lastCalculatedFpsLabel, gameOfLife)).start();
    }

}
