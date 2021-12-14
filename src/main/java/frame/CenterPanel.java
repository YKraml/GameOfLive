package frame;

import model.GameOfLife;

import javax.swing.*;

public class CenterPanel extends MyPanel {

    private final GameOfLife gameOfLife;

    public CenterPanel(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }

    public void init(){

        DrawPanel drawPanel = new DrawPanel(this.gameOfLife);
        this.setBorder(BorderFactory.createTitledBorder("Center"));
        this.add(drawPanel);
        this.componentsToDraw.add(drawPanel);
    }
}
