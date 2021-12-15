package frame;

import model.GameOfLife;

import javax.swing.*;
import java.awt.event.MouseAdapter;

public class CenterPanel extends MyPanel {

    private final GameOfLife gameOfLife;

    public CenterPanel(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }


    public void init() {
        DrawPanel drawPanel = new DrawPanel(this.gameOfLife.getBoard(), 800, 800, true);

        MouseAdapter mouseAdapter = new MyMouseMotionListener(gameOfLife, drawPanel);
        drawPanel.addMouseMotionListener(mouseAdapter);
        drawPanel.addMouseListener(mouseAdapter);

        this.setBorder(BorderFactory.createTitledBorder("Center"));
        this.add(drawPanel);
        this.addComponentToDraw(drawPanel);
        this.addInnerMyPanel(drawPanel);
    }
}
