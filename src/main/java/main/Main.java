package main;

import frame.MainFrame;
import frame.MainPanel;
import model.*;
import runnables.DrawComponentsRunnable;
import runnables.MakeRoundRunnable;
import runnables.UpdateLabelsRunnable;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final int BOARD_SIZE = 100;
    private static long TIME_BETWEEN_UPDATES_IN_NANO = (long) (33.3333333 * 1000 * 1000);
    private final static long FPS = 60;
    private static boolean stop = true;

    private static final IntWrapper calculatedRounds = new IntWrapper();
    private static final IntWrapper calculatedFps = new IntWrapper();
    private static final Point mousePos = new Point(0,0);

    private static Board currentlyChosenPattern;

    public static void main(String[] args) {


        Formation.init();

        //AbstractGameOfLife gameOfLife = new GameOfLife(new Board(BOARD_SIZE));
        AbstractGameOfLife gameOfLife = new GameOfLive();

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


    public static String readDataFromFile() {
        String formattedData = "";
        File file = new File("lexicon.txt");
        try {
            BufferedInputStream bufferedReader = new BufferedInputStream(new FileInputStream(file));

            Scanner sc = new Scanner(bufferedReader);

            StringBuilder data = new StringBuilder();
            while (sc.hasNextLine()) {
                data.append(sc.nextLine());
                data.append("\n");
            }

            formattedData = data.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return formattedData;
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

    public static Optional<Board> getCurrentlyChosenPattern() {
        return Optional.ofNullable(currentlyChosenPattern);
    }

    public static Point getMousePos() {
        return mousePos;
    }


}
