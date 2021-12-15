package main;

import frame.MainFrame;
import frame.MainPanel;
import model.Board;
import model.GameOfLife;
import runnables.DrawComponentsRunnable;
import runnables.MakeRoundRunnable;
import runnables.UpdateLabelsRunnable;

public class Main {

    private static final int BOARD_SIZE = 200;
    private static long TIME_BETWEEN_UPDATES_IN_NANO = (long) (33.3333333 * 1000 * 1000);
    private final static long FPS = 60;
    private static boolean stop = true;

    private static final IntWrapper calculatedRounds = new IntWrapper();
    private static final IntWrapper calculatedFps = new IntWrapper();

    private static Board currentlyChosenPattern;

    public static void main(String[] args) {

        GameOfLife gameOfLife = new GameOfLife(new Board(BOARD_SIZE).shuffle());

        MainPanel mainPanel = new MainPanel(gameOfLife);
        mainPanel.initAll();

        MainFrame myFrame = new MainFrame();
        myFrame.setVisible();
        myFrame.add(mainPanel);
        myFrame.pack();


        new Thread(new MakeRoundRunnable(gameOfLife)).start();
        new Thread(new DrawComponentsRunnable(mainPanel.getComponentsToDraw(), FPS)).start();
        new Thread(new UpdateLabelsRunnable(mainPanel.getLabelCouples())).start();

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


    public static IntWrapper getCalculatedFps() {
        return calculatedFps;
    }

    public static IntWrapper getCalculatedRounds() {
        return calculatedRounds;
    }

    public static void setCurrentlyChosenPattern(Board currentlyChosenPattern) {
        Main.currentlyChosenPattern = currentlyChosenPattern;
    }

    public static Board getCurrentlyChosenPattern() {
        return currentlyChosenPattern;
    }
}
