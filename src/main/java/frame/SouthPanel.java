package frame;

import main.Main;
import model.AbstractGameOfLife;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SouthPanel extends MyPanel {

    private final AbstractGameOfLife gameOfLife;
    private final List<JComponent> componentsToDraw;

    public SouthPanel(AbstractGameOfLife gameOfLife) {
        this.componentsToDraw = Collections.synchronizedList(new ArrayList<>());
        this.gameOfLife = gameOfLife;
    }

    public void init() {

        //StatsPanel
        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(BorderFactory.createTitledBorder("Stats"));
        statsPanel.setLayout(new GridLayout(5, 2));
        JLabel calculatedRoundsLabel = new JLabel(Main.getCalculatedRounds().toString());
        JLabel calculatedFpsLabel = new JLabel(Main.getCalculatedFps().toString());
        JLabel updatedAmountLabel = new JLabel(String.valueOf(gameOfLife.getUpdatedAmount()));
        JLabel checkedAmountLabel = new JLabel(String.valueOf(gameOfLife.getCheckedAmount()));
        JLabel mousePosAmountLabel = new JLabel(String.valueOf(Main.getMousePos()));
        statsPanel.add(new JLabel("Frames per Second: "));
        statsPanel.add(calculatedFpsLabel);
        statsPanel.add(new JLabel("Ticks per Second: "));
        statsPanel.add(calculatedRoundsLabel);
        statsPanel.add(new JLabel("Cell-Updated per Tick: "));
        statsPanel.add(updatedAmountLabel);
        statsPanel.add(new JLabel("Cell-Checked per Tick: "));
        statsPanel.add(checkedAmountLabel);
        statsPanel.add(new JLabel("Mouse Position: "));
        statsPanel.add(mousePosAmountLabel);

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
        JSlider fpsSlider = new JSlider(0, 500, 30);
        fpsSlider.setMajorTickSpacing(100);
        fpsSlider.setMinorTickSpacing(10);
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


        JCheckBox jCheckBox = new JCheckBox("Wrap with size:");
        jCheckBox.setSelected(true);
        jCheckBox.addActionListener(e -> gameOfLife.setWrapped(((JCheckBox) e.getSource()).isSelected()));

        JTextField textField = new JTextField(String.valueOf(gameOfLife.getSize()), 5);
        textField.addActionListener(e -> {
            String input = ((JTextField) e.getSource()).getText();
            try {
                int size = Integer.parseInt(input);
                gameOfLife.setSize(size);
            } catch (NumberFormatException exception) {
                textField.setText(String.valueOf(gameOfLife.getSize()));
            }
        });

        //SouthPanel
        this.setBorder(BorderFactory.createTitledBorder("More"));
        this.add(statsPanel);
        this.add(button);
        this.add(stopButton);
        this.add(clearButton);
        this.add(fpsSlider);
        this.add(jCheckBox);
        this.add(textField);

        this.addLabelCouple(calculatedRoundsLabel, Main.getCalculatedRounds());
        this.addLabelCouple(calculatedFpsLabel, Main.getCalculatedFps());
        this.addLabelCouple(checkedAmountLabel, gameOfLife.getCheckedAmount());
        this.addLabelCouple(updatedAmountLabel, gameOfLife.getUpdatedAmount());
        this.addLabelCouple(mousePosAmountLabel, Main.getMousePos());
    }

}
