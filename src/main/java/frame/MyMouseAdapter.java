package frame;

import main.Main;
import model.AbstractGameOfLife;
import model.Board;
import model.Cell;
import model.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Optional;

public class MyMouseAdapter extends MouseAdapter {


    private final DrawPanel drawPanel;
    private final AbstractGameOfLife gameOfLife;

    private boolean cameraIsMoving;

    private double originalMousePositionX;
    private double originalMousePositionY;


    public MyMouseAdapter(AbstractGameOfLife gameOfLife, DrawPanel drawPanel) {
        this.gameOfLife = gameOfLife;
        this.drawPanel = drawPanel;
    }


    @Override
    public void mouseDragged(MouseEvent e) {

        if (cameraIsMoving && SwingUtilities.isRightMouseButton(e)) {

            double newOffsetX = drawPanel.getWorldXOffset() + (e.getX() - originalMousePositionX) / drawPanel.getZoomX();
            double newOffsetY = drawPanel.getWorldYOffset() + (e.getY() - originalMousePositionY) / drawPanel.getZoomY();

            originalMousePositionX = e.getX();
            originalMousePositionY = e.getY();

            this.drawPanel.setWorldXOffset(newOffsetX);
            this.drawPanel.setWorldYOffset(newOffsetY);
            return;
        }

        if (e.getButton() == 3) {
            Main.setCurrentlyChosenPattern(null);
            return;
        }

        MyPoint point = calcScreenToWorld(e.getPoint());
        drawPanel.setMouseXPos(point.x());
        drawPanel.setMouseYPos(point.y());
        if (SwingUtilities.isLeftMouseButton(e)) {
            this.setCellAliveOrAddBoard(point);
        }
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        MyPoint point = calcScreenToWorld(e.getPoint());
        drawPanel.setMouseXPos(point.x());
        drawPanel.setMouseYPos(point.y());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getButton() == 3) {
            Main.setCurrentlyChosenPattern(null);
            return;
        }


        MyPoint point = calcScreenToWorld(e.getPoint());
        drawPanel.setMouseXPos(point.x());
        drawPanel.setMouseYPos(point.y());

        if (SwingUtilities.isLeftMouseButton(e)) {
            this.setCellAliveOrAddBoard(point);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (SwingUtilities.isRightMouseButton(e)) {
            this.cameraIsMoving = true;

            this.originalMousePositionX = e.getX();
            this.originalMousePositionY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        this.cameraIsMoving = false;
    }


    private void setCellAliveOrAddBoard(MyPoint point) {

        Optional<Board> boardOptional = Main.getCurrentlyChosenPattern();
        boardOptional.ifPresentOrElse(board -> {
            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getHeight(); j++) {

                    Cell cell = board.getCellAt(i, j);

                    if (cell.isALive()) {

                        int xCord = i + (int) point.x() - board.getWidth() / 2;
                        int yCord = j + (int) point.y() - board.getHeight() / 2;

                        if (gameOfLife.isWrapped()) {
                            xCord = ((xCord % gameOfLife.getWidth()) + gameOfLife.getWidth()) % gameOfLife.getWidth();
                            yCord = ((yCord % gameOfLife.getWidth()) + gameOfLife.getWidth()) % gameOfLife.getWidth();
                        }

                        gameOfLife.setCellAlive(xCord, yCord);
                    }

                }
            }
        }, () -> gameOfLife.setCellAlive((int) point.x(), (int) point.y()));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);

        MyPoint mousePosBefore = this.calcScreenToWorld(e.getPoint());

        int rotation = e.getWheelRotation();
        if (rotation < 0) {
            drawPanel.setZoomX(drawPanel.getZoomX() * 1.05);
            drawPanel.setZoomY(drawPanel.getZoomY() * 1.05);
        } else if (rotation > 0) {
            drawPanel.setZoomX(drawPanel.getZoomX() * 0.95);
            drawPanel.setZoomY(drawPanel.getZoomY() * 0.95);
        }
        MyPoint mousePosAfter = this.calcScreenToWorld(e.getPoint());

        double xVector = mousePosBefore.x() - mousePosAfter.x();
        double yVector = mousePosBefore.y() - mousePosAfter.y();

        drawPanel.setWorldXOffset(drawPanel.getWorldXOffset() - xVector);
        drawPanel.setWorldYOffset(drawPanel.getWorldYOffset() - yVector);

        MyPoint point = calcScreenToWorld(e.getPoint());
        drawPanel.setMouseXPos(point.x());
        drawPanel.setMouseYPos(point.y());
    }


    private MyPoint calcScreenToWorld(Point screenPoint) {

        double worldXPos = screenPoint.getX() / drawPanel.getZoomX() - this.drawPanel.getWorldXOffset();
        double worldYPos = screenPoint.getY() / drawPanel.getZoomY() - this.drawPanel.getWorldYOffset();

        return new MyPoint(worldXPos, worldYPos);
    }

}
