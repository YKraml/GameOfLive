package main;

import frame.DrawPanel;
import frame.MyFrame;
import model.Board;
import model.GameOfLife;
import runnables.DrawFrameRunnable;
import runnables.GameTickRunnable;
import runnables.StatsUpdateRunnable;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;

public class Main {

    private static final int BOARD_SIZE = 100;
    private static long TIME_BETWEEN_UPDATES_IN_NANO = (long) (33.3333333 * 1000 * 1000);
    private final static long FPS = 60;
    private static boolean stop = true;

    private static int calculatedRounds;
    private static int lastCalculatedRounds;

    private static int calculatedFps;
    private static int lastCalculatedFps;

    public static void main(String[] args) {

        Board board = new Board(BOARD_SIZE);
        board.shuffle();
        GameOfLife gameOfLife = new GameOfLife(board);

        //DraPanel
        DrawPanel drawPanel = new DrawPanel(gameOfLife);


        //StatsPanel
        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(BorderFactory.createTitledBorder("Stats"));
        statsPanel.setLayout(new GridLayout(4, 2));
        JLabel lastCalculatedRoundsLabel = new JLabel(String.valueOf(Main.getLastCalculatedRounds()));
        JLabel lastCalculatedFpsLabel = new JLabel(String.valueOf(Main.getLastCalculatedFps()));
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


        //CenterPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createTitledBorder("Center"));
        centerPanel.add(drawPanel);

        //SouthPanel
        JPanel southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createTitledBorder("South"));
        southPanel.add(statsPanel);
        southPanel.add(button);
        southPanel.add(stopButton);
        southPanel.add(clearButton);
        southPanel.add(fpsSlider);


        MyFrame myFrame = new MyFrame();
        myFrame.setLayout(new BorderLayout());
        myFrame.add(southPanel, SOUTH);
        myFrame.add(centerPanel, CENTER);
        myFrame.pack();
        myFrame.setVisible();


        List<JComponent> componentsToDraw = new ArrayList<>();
        componentsToDraw.add(checkedAmountLabel);
        componentsToDraw.add(updatedAmountLabel);
        componentsToDraw.add(lastCalculatedRoundsLabel);
        componentsToDraw.add(drawPanel);
        componentsToDraw.add(lastCalculatedFpsLabel);

        new Thread(new GameTickRunnable(gameOfLife)).start();
        new Thread(new DrawFrameRunnable(componentsToDraw, FPS)).start();
        new Thread(new StatsUpdateRunnable(lastCalculatedRoundsLabel, updatedAmountLabel, checkedAmountLabel, lastCalculatedFpsLabel, gameOfLife)).start();

    }


    public static long getTimeBetweenUpdatesInNano() {
        return TIME_BETWEEN_UPDATES_IN_NANO;
    }

    public static void setTimeBetweenUpdatesInNano(long timeBetweenUpdatesInNano) {
        TIME_BETWEEN_UPDATES_IN_NANO = timeBetweenUpdatesInNano;
    }

    public static boolean isStop() {
        return stop;
    }

    public static void setStop(boolean stop) {
        Main.stop = stop;
    }

    public static int getCalculatedRounds() {
        return calculatedRounds;
    }

    public static void setCalculatedRounds(int calculatedRounds) {
        Main.calculatedRounds = calculatedRounds;
    }

    public static int getLastCalculatedRounds() {
        return lastCalculatedRounds;
    }

    public static void setLastCalculatedRounds(int lastCalculatedRounds) {
        Main.lastCalculatedRounds = lastCalculatedRounds;
    }

    public static int getCalculatedFps() {
        return calculatedFps;
    }

    public static void setCalculatedFps(int calculatedFps) {
        Main.calculatedFps = calculatedFps;
    }

    public static int getLastCalculatedFps() {
        return lastCalculatedFps;
    }

    public static void setLastCalculatedFps(int lastCalculatedFps) {
        Main.lastCalculatedFps = lastCalculatedFps;
    }
}
