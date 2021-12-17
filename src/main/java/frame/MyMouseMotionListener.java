package frame;

import main.Main;
import model.AbstractGameOfLife;
import model.Board;
import model.Cell;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseMotionListener extends MouseAdapter {

    private final DrawPanel drawPanel;
    private final AbstractGameOfLife gameOfLife;

    public MyMouseMotionListener(AbstractGameOfLife gameOfLife, DrawPanel drawPanel) {
        this.gameOfLife = gameOfLife;
        this.drawPanel = drawPanel;
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getButton() == 3){
            Main.setCurrentlyChosenPattern(null);
            return;
        }

        Point point = calcCellPosition(e.getX(), e.getY());
        drawPanel.setMousePos(point);

        Board board = Main.getCurrentlyChosenPattern();
        if (board != null) {

            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getHeight(); j++) {

                    Cell cell = board.getCellAt(i, j);

                    if (cell.isALive()) {
                        gameOfLife.setCellAlive(i + point.x - board.getWidth() / 2, j + point.y - board.getHeight() / 2);
                    }

                }
            }

        } else {
            gameOfLife.setCellAlive(point.x, point.y);
        }
        drawPanel.setMousePos(point);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = calcCellPosition(e.getX(), e.getY());
        drawPanel.setMousePos(point);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getButton() == 3){
            Main.setCurrentlyChosenPattern(null);
            return;
        }

        Point point = calcCellPosition(e.getX(), e.getY());
        drawPanel.setMousePos(point);

        Board board = Main.getCurrentlyChosenPattern();
        if (board != null) {

            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getHeight(); j++) {

                    Cell cell = board.getCellAt(i, j);

                    if (cell.isALive()) {
                        gameOfLife.setCellAlive(i + point.x - board.getWidth() / 2, j + point.y - board.getHeight() / 2);
                    }

                }
            }

        } else {
            gameOfLife.setCellAlive(point.x, point.y);
        }


    }

    private Point calcCellPosition(double mouseXPos, double mouseYPos) {

        int panelWidth = drawPanel.getWidth();
        int panelHeight = drawPanel.getHeight();

        int width = this.gameOfLife.getWidth();
        int height = this.gameOfLife.getHeight();

        double normalisedMouseXPos = mouseXPos / panelWidth;
        double normalisedMouseYPos = mouseYPos / panelHeight;

        int cellXPos = (int) (normalisedMouseXPos * width);
        int cellYPos = (int) (normalisedMouseYPos * height);

        return new Point(Math.max(Math.min(cellXPos, width - 1), 0), Math.max(Math.min(cellYPos, height - 1), 0));
    }
}
