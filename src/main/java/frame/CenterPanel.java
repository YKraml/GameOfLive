package frame;

import model.AbstractGameOfLife;

import javax.swing.*;
import java.awt.event.MouseAdapter;

public class CenterPanel extends MyPanel {

    private final AbstractGameOfLife gameOfLife;

    public CenterPanel(AbstractGameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }


    public void init() {
        DrawPanel drawPanel = new DrawPanel(800,800, this.gameOfLife);

        MouseAdapter mouseAdapter = new MyMouseMotionListener(gameOfLife, drawPanel);
        drawPanel.addMouseMotionListener(mouseAdapter);
        drawPanel.addMouseListener(mouseAdapter);

        this.setBorder(BorderFactory.createTitledBorder("Board"));
        this.add(drawPanel);
        this.addComponentToDraw(drawPanel);
        this.addInnerMyPanel(drawPanel);
    }
}
