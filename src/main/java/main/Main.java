package main;

import frame.CenterPanel;
import frame.DrawPanel;
import frame.MyFrame;
import frame.SouthPanel;
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

    private static Integer calculatedRounds;
    private static Integer calculatedFps;

    public static void main(String[] args) {

        GameOfLife gameOfLife = new GameOfLife(new Board(BOARD_SIZE).shuffle());

        //Southpanel
        SouthPanel southPanel = new SouthPanel(gameOfLife);
        southPanel.init();

        //CenterPanel
        CenterPanel centerPanel = new CenterPanel(gameOfLife);
        centerPanel.init();

        MyFrame myFrame = new MyFrame();
        myFrame.setLayout(new BorderLayout());
        myFrame.add(southPanel, SOUTH);
        myFrame.add(centerPanel, CENTER);
        myFrame.pack();
        myFrame.setVisible();


        List<JComponent> componentsToDraw = new ArrayList<>();
        componentsToDraw.addAll(centerPanel.getComponentsToDraw());
        componentsToDraw.addAll(southPanel.getComponentsToDraw());

        new Thread(new GameTickRunnable(gameOfLife)).start();
        new Thread(new DrawFrameRunnable(componentsToDraw, FPS)).start();


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

    public static Integer getCalculatedRounds() {
        return calculatedRounds;
    }

    public static void setCalculatedRounds(Integer calculatedRounds) {
        Main.calculatedRounds = calculatedRounds;
    }

    public static Integer getCalculatedFps() {
        return calculatedFps;
    }

    public static void setCalculatedFps(Integer calculatedFps) {
        Main.calculatedFps = calculatedFps;
    }

}
