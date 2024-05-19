package model;

import main.Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formation {

    private static final Collection<Formation> values = new ArrayList<>();
    private static final char ALIVE_CHAR = '*';

    private final String name;
    private final String description;
    private final String formation;

    public Formation(String name, String description, String formation) {
        this.name = name;
        this.description = description;
        this.formation = formation;
        values.add(this);
    }

    public static void init() {
        Formation.readFormationsFromFile();
    }

    public String getFormation() {
        return formation;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Board getBoardFromFormation() {
        String[] rows = this.getFormation().replace("\t", "").split("\n");

        Cell[][] cells = new Cell[rows.length][rows[0].length()];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                char character = rows[i].toCharArray()[j];
                Cell cell = new Cell();
                cells[i][j] = cell;
                if (character == ALIVE_CHAR) {
                    cell.markToBeBorn().update();
                }
            }
        }

        return new Board(cells);
    }

    public static Collection<Formation> getValues() {
        return values;
    }

    private static void readFormationsFromFile() {

        Collection<String> formationCollection = new ArrayList<>();
        String formattedData = Main.readDataFromFile();

        String regex2 = ":(.)+:(.)*\n(\\s\\s\\s(.)*\\n)*((\\t([\\*\\.])+\\n)+\\n)";

        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(formattedData);
        while (matcher.find()) {
            formationCollection.add(matcher.group());
        }

        formationCollection.forEach(formationString -> {

            String regexName = ":(.)+:";
            String regexFormation = "((\\t([\\*\\.])+\\n)+\\n)";

            Matcher matcherName = Pattern.compile(regexName).matcher(formationString);
            Matcher matcherFormation = Pattern.compile(regexFormation).matcher(formationString);

            String name = matcherName.find() ? matcherName.group() : "NAME";
            String formation = matcherFormation.find() ? matcherFormation.group() : "FORMATION";
            String description = formationString.replace(name, "").replace(formation, " ").replace("   ", " ").replace("  ", "");

            name = name.replace(":", "").toUpperCase(Locale.ROOT);

            new Formation(name, description, formation);
        });
    }
}
