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
        DrawPanel drawPanel = new DrawPanel(700,700, this.gameOfLife);

        MouseAdapter mouseAdapter = new MyMouseAdapter(gameOfLife, drawPanel);
        drawPanel.addMouseMotionListener(mouseAdapter);
        drawPanel.addMouseListener(mouseAdapter);
        drawPanel.addMouseWheelListener(mouseAdapter);

        this.setBorder(BorderFactory.createTitledBorder("Board"));
        this.add(drawPanel);
        this.addComponentToDraw(drawPanel);
        this.addInnerMyPanel(drawPanel);
    }
}
