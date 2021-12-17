package frame;

import main.Main;
import model.AbstractGameOfLife;
import model.Board;
import model.Cell;
import model.MyPoint;

import java.awt.*;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

public class DrawPanel extends MyPanel {

    private static final Color BACKGROUND_COLOR = Color.gray;
    private static final Color ALIVE_CELL_COLOR = new Color(0, 153, 204);
    private static final Color DEAD_CELL_COLOR = new Color(224, 242, 255);
    private static final Color FOREIGN_CELL_COLOR = Color.ORANGE;
    private static final Color MOUSE_COLOR = Color.GREEN;

    private final int width;
    private final int height;

    private final AbstractGameOfLife gameOfLife;
    private final int LINE_THICKNESS = 1;
    private Point mousePos;

    public DrawPanel(int width, int height, AbstractGameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
        this.width = width;
        this.height = height;
        this.mousePos = new Point(0, 0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Board board = this.gameOfLife.getBoard();
        Collection<MyPoint> myPoints = this.gameOfLife.getAlivePoints();

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                int cellWidth = this.getWidth() / board.getCells().length;
                int cellHeight = this.getHeight() / board.getCells()[0].length;

                Cell cell = board.getCellAt(i, j);
                int pixelX = cellWidth * i;
                int cellPosY = cellHeight * j;

                g.setColor(BACKGROUND_COLOR);
                g.fillRect(pixelX, cellPosY, cellWidth, cellHeight);

                if (cell.isALive()) {
                    g.setColor(ALIVE_CELL_COLOR);
                } else {
                    g.setColor(DEAD_CELL_COLOR);
                }
                drawSquare(g, pixelX, cellPosY, cellWidth, cellHeight);
            }
        }


        Board foreignBoard = Main.getCurrentlyChosenPattern();

        int xOffset = this.getWidth() / board.getCells().length;
        int yOffset = this.getHeight() / board.getCells()[0].length;

        g.setColor(FOREIGN_CELL_COLOR);
        if (foreignBoard != null) {
            for (int cellXPos = 0; cellXPos < board.getWidth(); cellXPos++) {
                for (int cellYPos = 0; cellYPos < board.getHeight(); cellYPos++) {


                    int pixelX = xOffset * cellXPos;
                    int pixelY = yOffset * cellYPos;

                    int foreignCellPosX = cellXPos - mousePos.x + foreignBoard.getWidth() / 2;
                    int foreignCellPosY = cellYPos - mousePos.y + foreignBoard.getHeight() / 2;

                    boolean cellAtPositionExists = foreignCellPosX >= 0 && foreignCellPosY >= 0 && foreignCellPosX < foreignBoard.getWidth() && foreignCellPosY < foreignBoard.getHeight();
                    if (cellAtPositionExists) {

                        Cell cell = foreignBoard.getCellAt(foreignCellPosX, foreignCellPosY);
                        if (cell.isALive()) {
                            drawSquare(g, pixelX, pixelY, xOffset, yOffset);
                        }
                    }
                }
            }

        }

        g.setColor(MOUSE_COLOR);
        drawSquare(g, this.mousePos.x * xOffset, this.mousePos.y * yOffset, xOffset, yOffset);


        g.setColor(BACKGROUND_COLOR);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());

    }


    private void drawSquare(Graphics g, int pixelX, int pixel, int cellWidth, int cellHeight) {
        g.fillRect(2 * LINE_THICKNESS + pixelX - LINE_THICKNESS, 2 * LINE_THICKNESS + pixel - LINE_THICKNESS, cellWidth - LINE_THICKNESS, cellHeight - LINE_THICKNESS);
    }

    public void setMousePos(Point mousePos) {
        this.mousePos = mousePos;
    }

    @Override
    protected void init() {
        Dimension dimension = new Dimension();
        dimension.setSize(this.width, this.height);
        this.setPreferredSize(dimension);
    }
}
