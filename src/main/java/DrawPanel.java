import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class DrawPanel extends JPanel {

    private static final Color LINE_COLOR = Color.BLACK;
    private static final Color ALIVE_CELL_COLOR = new Color(0, 153, 204);
    private static final Color DEAD_CELL_COLOR = new Color(224, 242, 255);
    private static final Color MOUSE_COLOR = Color.GREEN;
    private static final int SIZE = 800;

    private GameOfLive gameOfLive;
    private int LINE_THICKNESS = 0;

    private Point mousePos;

    public DrawPanel(GameOfLive gameOfLive) {

        Dimension dimension = new Dimension();
        dimension.setSize(SIZE, SIZE);
        this.setPreferredSize(dimension);
        this.gameOfLive = gameOfLive;

        MouseAdapter mouseAdapter = new MyMouseMotionListener(gameOfLive, this);
        this.addMouseMotionListener(mouseAdapter);
        this.addMouseListener(mouseAdapter);
        this.mousePos = new Point(0, 0);
    }

    @Override
    public void paint(Graphics g) {

        int w = this.getWidth();
        int h = this.getHeight();

        int xOffset = w / this.gameOfLive.getBoard().getCells()[0].length;
        int yOffset = h / this.gameOfLive.getBoard().getCells().length;

        super.paint(g);
        Cell[][] cells = gameOfLive.getBoard().getCells();
        for (int i = 0, cellsLength = cells.length; i < cellsLength; i++) {

            Cell[] row = cells[i];
            for (int j = 0, rowLength = row.length; j < rowLength; j++) {
                Cell cell = row[j];
                int x = xOffset * i;
                int y = yOffset * j;

                g.setColor(LINE_COLOR);
                g.fillRect(x, y, xOffset, yOffset);

                if (cell.isALive()) {
                    g.setColor(ALIVE_CELL_COLOR);
                    g.fillRect(2 * LINE_THICKNESS + x - LINE_THICKNESS, 2 * LINE_THICKNESS + y - LINE_THICKNESS, xOffset - LINE_THICKNESS, yOffset - LINE_THICKNESS);
                } else {
                    g.setColor(DEAD_CELL_COLOR);
                    g.fillRect(2 * LINE_THICKNESS + x - LINE_THICKNESS, 2 * LINE_THICKNESS + y - LINE_THICKNESS, xOffset - LINE_THICKNESS, yOffset - LINE_THICKNESS);
                }

                if (i == this.mousePos.x && j == this.mousePos.y) {
                    g.setColor(MOUSE_COLOR);
                    g.fillRect(2 * LINE_THICKNESS + x - LINE_THICKNESS, 2 * LINE_THICKNESS + y - LINE_THICKNESS, xOffset - LINE_THICKNESS, yOffset - LINE_THICKNESS);

                }

            }
        }

        g.setColor(Color.BLACK);
        g.drawString("Updates: " +String.valueOf(Main.getLastCalculatedRounds()), 0,10);
        g.drawString("Checked: " + gameOfLive.getCheckedAmount(), 0,20);
        g.drawString("Updated: " + gameOfLive.getUpdatedAmount(), 0,30);
    }

    public void setMousePos(Point mousePos) {
        this.mousePos = mousePos;
    }
}
