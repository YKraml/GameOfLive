package frame;

import main.Main;
import model.Board;
import model.Cell;

import java.awt.*;

public class DrawPanel extends MyPanel {

    private static final Color BACKGROUND_COLOR = Color.gray;
    private static final Color ALIVE_CELL_COLOR = new Color(0, 153, 204);
    private static final Color DEAD_CELL_COLOR = new Color(224, 242, 255);
    private static final Color FOREIGN_CELL_COLOR = Color.ORANGE;
    private static final Color MOUSE_COLOR = Color.GREEN;

    private final int width;
    private final int height;

    private final Board board;
    private final int LINE_THICKNESS = 1;
    private Point mousePos;
    private final boolean showMousePos;

    public DrawPanel(Board board, int width, int height, boolean showMousePos) {
        this.board = board;
        this.width = width;
        this.height = height;
        this.showMousePos = showMousePos;
        this.mousePos = new Point(0, 0);
    }

    public DrawPanel(Board board, int width, boolean b) {
        this(board, width, width / board.getWidth() * board.getHeight(), b);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int xOffset = this.getWidth() / this.board.getCells().length;
        int yOffset = this.getHeight() / this.board.getCells()[0].length;

        this.setSize(xOffset * board.getWidth(), yOffset * board.getHeight());

        Board foreignBoard = Main.getCurrentlyChosenPattern();

        for (int i = 0; i < this.board.getWidth(); i++) {
            for (int j = 0; j < this.board.getHeight(); j++) {

                Cell cell = this.board.getCellAt(i, j);
                int pixelX = xOffset * i;
                int cellPosY = yOffset * j;

                g.setColor(BACKGROUND_COLOR);
                g.fillRect(pixelX, cellPosY, xOffset, yOffset);

                if (cell.isALive()) {
                    g.setColor(ALIVE_CELL_COLOR);
                } else {
                    g.setColor(DEAD_CELL_COLOR);
                }
                drawSquare(g, pixelX, cellPosY, xOffset, yOffset);
            }
        }

        g.setColor(FOREIGN_CELL_COLOR);
        for (int i = 0; i < this.board.getWidth(); i++) {
            for (int j = 0; j < this.board.getHeight(); j++) {
                if (this.showMousePos && foreignBoard != null) {

                    int pixelX = xOffset * i;
                    int pixel = yOffset * j;
                    int cellPosX = i - mousePos.x + foreignBoard.getWidth() / 2;
                    int cellPosY = j - mousePos.y + foreignBoard.getHeight() / 2;

                    if (cellPosX >= 0 && cellPosY >= 0 && cellPosX < foreignBoard.getWidth() && cellPosY < foreignBoard.getHeight()) {

                        Cell cell = foreignBoard.getCellAt(cellPosX, cellPosY);
                        boolean cell2Alive = cell.isALive();
                        if (cell2Alive) {
                            drawSquare(g, pixelX, pixel, xOffset, yOffset);
                        }
                    }
                }
            }
        }

        if (this.showMousePos) {
            g.setColor(MOUSE_COLOR);
            drawSquare(g, this.mousePos.x * xOffset, this.mousePos.y * yOffset, xOffset, yOffset);
        }

        g.setColor(BACKGROUND_COLOR);
        g.drawRect(0,0, this.getWidth(), this.getHeight());

    }

    private void drawSquare(Graphics g, int pixelX, int pixel, int xOffset, int yOffset) {
        g.fillRect(2 * LINE_THICKNESS + pixelX - LINE_THICKNESS, 2 * LINE_THICKNESS + pixel - LINE_THICKNESS, xOffset -  LINE_THICKNESS, yOffset -  LINE_THICKNESS);
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
