import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {

    private static final Color LINE_COLOR = Color.BLACK;
    private static final Color ALIVE_CELL_COLOR = new Color(0, 153, 204);
    private static final Color DEAD_CELL_COLOR = new Color(224, 242, 255);
    private Board board;
    private int LINE_THICKNESS = 0;

    public DrawPanel(Board board) {
        this.board = board;
    }

    @Override
    public void paint(Graphics g) {

        int w = this.getWidth();
        int h = this.getHeight();

        int xOffset = w / this.board.getCells()[0].length;
        int yOffset = h / this.board.getCells().length;

        super.paint(g);
        Cell[][] cells = board.getCells();
        for (int i = 0, cellsLength = cells.length; i < cellsLength; i++) {

            Cell[] row = cells[i];
            for (int j = 0, rowLength = row.length; j < rowLength; j++) {
                Cell cell = row[j];
                int x = xOffset * j;
                int y = yOffset * i;

                g.setColor(LINE_COLOR);
                g.fillRect(x, y, xOffset, yOffset);

                if (cell.isALive()) {
                    g.setColor(ALIVE_CELL_COLOR);
                    g.fillRect(2 * LINE_THICKNESS + x - LINE_THICKNESS, 2 * LINE_THICKNESS + y - LINE_THICKNESS, xOffset - LINE_THICKNESS, yOffset - LINE_THICKNESS);
                } else {
                    g.setColor(DEAD_CELL_COLOR);
                    g.fillRect(2 * LINE_THICKNESS + x - LINE_THICKNESS, 2 * LINE_THICKNESS + y - LINE_THICKNESS, xOffset - LINE_THICKNESS, yOffset - LINE_THICKNESS);
                }
            }
        }
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
