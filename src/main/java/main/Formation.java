package main;

import java.util.ArrayList;
import java.util.Collection;

public class Formation {

    private static final Collection<Formation> values = new ArrayList<>();

    private final String name;
    private final String description;
    private final String formation;

    public Formation(String name, String description, String formation) {
        this.name = name;
        this.description = description;
        this.formation = formation;
        values.add(this);
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

    public static Collection<Formation> getValues() {
        return values;
    }
}
