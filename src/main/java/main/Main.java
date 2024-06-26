package main;

import frame.MainFrame;
import frame.MainPanel;
import model.*;
import runnables.DrawComponentsRunnable;
import runnables.MakeRoundRunnable;
import runnables.UpdateLabelsRunnable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Main {

    private static long timeBetweenUpdatesInNano = (long) (33.3333333 * 1000 * 1000);
    private final static long FPS = 60;
    private static boolean stop = true;

    private static final IntWrapper calculatedRounds = new IntWrapper();
    private static final IntWrapper calculatedFps = new IntWrapper();
    private static final Point mousePos = new Point(0, 0);

    private static Board currentlyChosenPattern;

    public static void main(String[] args) {
        Formation.init();
        AbstractGameOfLife gameOfLife = new GameOfLive();
        gameOfLife.shuffle();

        MainPanel mainPanel = new MainPanel(gameOfLife);
        mainPanel.initAll();

        MainFrame myFrame = new MainFrame();
        myFrame.setVisible();
        myFrame.add(mainPanel);
        myFrame.pack();

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event.getID() == KeyEvent.KEY_PRESSED) {
                KeyEvent kEvent = (KeyEvent) event;
                char pressedKey = kEvent.getKeyChar();
                if (pressedKey == 'r') {
                    getCurrentlyChosenPattern().ifPresent(board -> setCurrentlyChosenPattern(board.getRotatedBoard()));
                }

            }
        }, AWTEvent.KEY_EVENT_MASK);

        new Thread(new MakeRoundRunnable(gameOfLife)).start();
        new Thread(new DrawComponentsRunnable(mainPanel.getComponentsToDraw(), FPS)).start();
        new Thread(new UpdateLabelsRunnable(mainPanel.getLabelCouples())).start();
    }


    public static String readDataFromFile() {
        try {
            String formattedData = Files.readString(Path.of("lexicon.txt"));
            return formattedData.replace("\r", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static long getTimeBetweenUpdatesInNano() {
        return timeBetweenUpdatesInNano;
    }

    public static void setTimeBetweenUpdatesInNano(long timeBetweenUpdatesInNano) {
        Main.timeBetweenUpdatesInNano = timeBetweenUpdatesInNano;
    }

    public static boolean isStop() {
        return stop;
    }

    public static void setStop(boolean stop) {
        Main.stop = stop;
    }


    public static IntWrapper getCalculatedFps() {
        return calculatedFps;
    }

    public static IntWrapper getCalculatedRounds() {
        return calculatedRounds;
    }

    public static void setCurrentlyChosenPattern(Board currentlyChosenPattern) {
        Main.currentlyChosenPattern = currentlyChosenPattern;
    }

    public static Optional<Board> getCurrentlyChosenPattern() {
        return Optional.ofNullable(currentlyChosenPattern);
    }

    public static Point getMousePos() {
        return mousePos;
    }


}
