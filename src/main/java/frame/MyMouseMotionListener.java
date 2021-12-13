package frame;

import model.GameOfLife;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseMotionListener extends MouseAdapter {

    private final DrawPanel drawPanel;
    private final GameOfLife gameOfLife;

    public MyMouseMotionListener(GameOfLife gameOfLife, DrawPanel drawPanel) {
        this.gameOfLife = gameOfLife;
        this.drawPanel = drawPanel;
    }


    @Override
    public void mouseDragged(MouseEvent e) {

        Point point = calcCellPosition(e.getX(), e.getY());

        gameOfLife.setCellAlive(point.x, point.y);
        drawPanel.setMousePos(point);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        Point point = calcCellPosition(e.getX(), e.getY());

        drawPanel.setMousePos(point);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = calcCellPosition(e.getX(), e.getY());

        drawPanel.setMousePos(point);
        gameOfLife.setCellAlive(point.x, point.y);
    }

    private Point calcCellPosition(double mouseXPos, double mouseYPos) {

        int panelWidth = drawPanel.getWidth();
        int panelHeight = drawPanel.getHeight();

        int boardSize = this.gameOfLife.getSize();

        double normalisedMouseXPos = mouseXPos / panelWidth;
        double normalisedMouseYPos = mouseYPos / panelHeight;

        int cellXPos = (int) (normalisedMouseXPos * boardSize);
        int cellYPos = (int) (normalisedMouseYPos * boardSize);

        return new Point(Math.max(Math.min(cellXPos, boardSize - 1), 0), Math.max(Math.min(cellYPos, boardSize - 1), 0));
    }
}
