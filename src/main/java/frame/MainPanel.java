package frame;

import model.AbstractGameOfLife;

import java.awt.*;

import static java.awt.BorderLayout.*;

public class MainPanel extends MyPanel {

    private final AbstractGameOfLife gameOfLife;

    public MainPanel(AbstractGameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }

    public void init() {
        SouthPanel southPanel = new SouthPanel(gameOfLife);
        CenterPanel centerPanel = new CenterPanel(gameOfLife);
        EastPanel eastPanel = new EastPanel();

        this.setLayout(new BorderLayout());
        this.add(southPanel, SOUTH);
        this.add(centerPanel, CENTER);
        this.add(eastPanel, EAST);

        this.addInnerMyPanel(centerPanel);
        this.addInnerMyPanel(southPanel);
        this.addInnerMyPanel(eastPanel);
    }
}
