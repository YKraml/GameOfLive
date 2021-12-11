import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class MyMouseMotionListener extends MouseAdapter {

    private final DrawPanel drawPanel;
    private final GameOfLive gameOfLive;

    public MyMouseMotionListener(GameOfLive gameOfLive, DrawPanel drawPanel) {
        this.gameOfLive = gameOfLive;
        this.drawPanel = drawPanel;
    }


    @Override
    public void mouseDragged(MouseEvent e) {

        Point point = calcCellPosition(e.getX(), e.getY());

        gameOfLive.setCellAlive(point.x, point.y);
        drawPanel.setMousePos(point);
        drawPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        Point point = calcCellPosition(e.getX(), e.getY());

        drawPanel.setMousePos(point);
        drawPanel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = calcCellPosition(e.getX(), e.getY());

        drawPanel.setMousePos(point);
        gameOfLive.setCellAlive(point.x, point.y);
        drawPanel.repaint();
    }

    private Point calcCellPosition(double mouseXPos, double mouseYPos) {

        int panelWidth = drawPanel.getWidth();
        int panelHeight = drawPanel.getHeight();

        int boardSize = this.gameOfLive.getSize();

        double normalisedMouseXPos = mouseXPos / panelWidth;
        double normalisedMouseYPos = mouseYPos / panelHeight;

        int cellXPos = (int) (normalisedMouseXPos * boardSize);
        int cellYPos = (int) (normalisedMouseYPos * boardSize);

        return new Point(Math.max(Math.min(cellXPos, boardSize - 1), 0), Math.max(Math.min(cellYPos, boardSize - 1), 0));
    }
}
