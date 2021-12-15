package main;

import frame.MainFrame;
import frame.MainPanel;
import model.Board;
import model.GameOfLife;
import runnables.DrawComponentsRunnable;
import runnables.MakeRoundRunnable;
import runnables.UpdateLabelsRunnable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final int BOARD_SIZE = 100;
    private static long TIME_BETWEEN_UPDATES_IN_NANO = (long) (33.3333333 * 1000 * 1000);
    private final static long FPS = 60;
    private static boolean stop = true;

    private static final IntWrapper calculatedRounds = new IntWrapper();
    private static final IntWrapper calculatedFps = new IntWrapper();

    private static Board currentlyChosenPattern;

    public static void main(String[] args) {
        readFormationsFromFile();


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

    private static void readFormationsFromFile() {

        Collection<String> formationCollection = new ArrayList<>();
        String formattedData = readDataFromFile();

        String regex2 = ":(.)+:(.)*\n(\\s\\s\\s(.)*\\n)*((\\t([\\*\\.])+\\n)+\\n)"; //Regex2: :(.)+:(.)*\n(\s\s\s(.)*\n)*((\t([\\.\\*])+\n)+\n)

        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(formattedData);
        while (matcher.find()) {
            formationCollection.add(matcher.group());
        }


        formationCollection.forEach(formationString -> {

            String regexName = ":(.)+:";
            String regexDescription = "(.)*\n(\\s\\s\\s(.)*\\n)*";
            String regexFormation = "((\\t([\\*\\.])+\\n)+\\n)";

            Matcher matcherName = Pattern.compile(regexName).matcher(formationString);
            Matcher matcherFormation = Pattern.compile(regexFormation).matcher(formationString);

            String name = "NAME";
            String description = "DESCRIPTION";
            String formation = "FORMATION";
            while (matcherName.find()) {
                name = matcherName.group();
            }

            while (matcherFormation.find()) {
                formation = matcherFormation.group();
            }

            description = formationString.replace(name, "").replace(formation, " ").replace("   ", " ").replace("  ", "");
            name = name.replace(":", "").toUpperCase(Locale.ROOT);


            new Formation(name,description,formation);

        });
    }

    private static String readDataFromFile() {
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

    public static Board getCurrentlyChosenPattern() {
        return currentlyChosenPattern;
    }
}
