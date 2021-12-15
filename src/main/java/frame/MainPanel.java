package frame;

import model.GameOfLife;

import java.awt.*;

import static java.awt.BorderLayout.*;

public class MainPanel extends MyPanel{

    private final GameOfLife gameOfLife;

    public MainPanel(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }


    public void init(){

        SouthPanel southPanel = new SouthPanel(gameOfLife);
        CenterPanel centerPanel = new CenterPanel(gameOfLife);
        EastPanel eastPanel = new EastPanel(gameOfLife);

        this.setLayout(new BorderLayout());
        this.add(southPanel, SOUTH);
        this.add(centerPanel, CENTER);
        this.add(eastPanel, EAST);

        this.addInnerMyPanel(centerPanel);
        this.addInnerMyPanel(southPanel);
        this.addInnerMyPanel(eastPanel);

    }
}
