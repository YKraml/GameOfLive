package frame;

import model.Board;
import model.Cell;

import javax.swing.*;
import java.awt.*;

public class LittleDrawPanel extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.gray;
    private static final Color ALIVE_CELL_COLOR = new Color(0, 153, 204);
    private static final Color DEAD_CELL_COLOR = new Color(224, 242, 255);
    private static final int LINE_THICKNESS = 1;

    private final Board board;
    private final int width;
    private final int height;

    public LittleDrawPanel(Board board, int width) {
        this.board = board;
        this.width = width;
        this.height = this.width / board.getWidth() * board.getHeight();
        Dimension dimension = new Dimension();
        dimension.setSize(width, height);
        this.setPreferredSize(dimension);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int xOffset = this.getWidth() / this.board.getWidth();
        int yOffset = this.getHeight() / this.board.getHeight();

        this.setSize(xOffset * board.getWidth(), yOffset * board.getHeight());


        for (int i = 0; i < this.board.getWidth(); i++) {
            for (int j = 0; j < this.board.getHeight(); j++) {

                Cell cell = this.board.getCellAt(i, j);
                int pixelX = xOffset * i;
                int pixelY = yOffset * j;

                g.setColor(BACKGROUND_COLOR);
                g.fillRect(pixelX, pixelY, xOffset, yOffset);

                if (cell.isALive()) {
                    g.setColor(ALIVE_CELL_COLOR);
                } else {
                    g.setColor(DEAD_CELL_COLOR);
                }
                g.fillRect(2 * LINE_THICKNESS + pixelX - LINE_THICKNESS, 2 * LINE_THICKNESS + pixelY - LINE_THICKNESS, xOffset - LINE_THICKNESS, yOffset - LINE_THICKNESS);
            }
        }
    }
}
