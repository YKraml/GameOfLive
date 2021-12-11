import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import static java.awt.BorderLayout.*;

public class Main {

    private static final int BOARD_SIZE = 100;
    private static int TIME_BETWEEN_UPDATES = 30;
    private static boolean stop = true;

    private static int calculatedRounds;
    private static int lastCalculatedRounds;

    public static void main(String[] args) {

        MyFrame myFrame = new MyFrame();
        myFrame.setLayout(new BorderLayout());

        Board board = new Board(BOARD_SIZE);
        board.shuffle();
        GameOfLive gameOfLive = new GameOfLive(board);


        //DrawPanel
        DrawPanel drawPanel = new DrawPanel(gameOfLive);
        myFrame.add(drawPanel, BorderLayout.CENTER);

        //Timer
        Timer timer = new Timer(0, e -> {
            Main.setCalculatedRounds(Main.getCalculatedRounds() + 1);
            if (!Main.isStop()) {
                gameOfLive.makeRound();
            }
        });
        timer.setDelay(Main.TIME_BETWEEN_UPDATES);
        timer.start();


        //ShuffleButton
        JButton button = new JButton("Shuffle");
        button.addActionListener(e -> gameOfLive.shuffle());

        //StopButton
        JButton stopButton = new JButton("Start | Stop");
        stopButton.addActionListener(e -> stop = !stop);

        //ClearButton
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> gameOfLive.clear());

        //FpsSlider
        JSlider fpsSlider = new JSlider(0, 150, 30);
        fpsSlider.setMajorTickSpacing(30);
        fpsSlider.setMinorTickSpacing(5);
        fpsSlider.setPaintTicks(true);
        fpsSlider.setPaintLabels(true);
        fpsSlider.addChangeListener(e -> {
            JSlider source = ((JSlider) e.getSource());
            if (source.getValueIsAdjusting()) {
                return;
            }
            int value = source.getValue();
            if (value == 0) {
                return;
            }
            Main.setTimeBetweenUpdates(1000 / value);
            timer.setDelay(Main.getTimeBetweenUpdates());
            timer.restart();
        });


        //GameTickCounter counter
        Timer gameTickCounter = new Timer(0, e -> {
            Main.setLastCalculatedRounds(Main.getCalculatedRounds());
            Main.setCalculatedRounds(0);
        });
        gameTickCounter.setDelay(1000);
        gameTickCounter.start();


        //Drawpanel zeichnen
        Timer drawTimer = new Timer(0, e -> drawPanel.repaint());
        drawTimer.setDelay(36);
        drawTimer.start();


        //SouthPanel
        JPanel southPanel = new JPanel();
        myFrame.add(southPanel, SOUTH);
        southPanel.add(button);
        southPanel.add(stopButton);
        southPanel.add(clearButton);
        southPanel.add(fpsSlider);

        myFrame.pack();
        myFrame.setVisible();
    }


    public static int getSIZE() {
        return BOARD_SIZE;
    }

    public static int getTimeBetweenUpdates() {
        return TIME_BETWEEN_UPDATES;
    }

    public static boolean isStop() {
        return stop;
    }

    public static void setCalculatedRounds(int calculatedRounds) {
        Main.calculatedRounds = calculatedRounds;
    }

    public static int getCalculatedRounds() {
        return calculatedRounds;
    }

    public static void setLastCalculatedRounds(int lastCalculatedRounds) {
        Main.lastCalculatedRounds = lastCalculatedRounds;
    }

    public static int getLastCalculatedRounds() {
        return lastCalculatedRounds;
    }

    public static void setTimeBetweenUpdates(int timeBetweenUpdates) {
        TIME_BETWEEN_UPDATES = timeBetweenUpdates;
    }
}
