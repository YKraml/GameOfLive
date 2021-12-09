import java.awt.*;
import java.awt.geom.Point2D;

public class Board {

    private static final double SHUFFLE_CONSTANT = 0.8;
    private final Cell[][] cells;

    public Board(int size) {
        this.cells = new Cell[size][size];
        initCells();
    }

    private void initCells() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void shuffle() {
        for (Cell[] row : this.cells) {
            for (Cell cell : row) {
                if (Math.random() >= SHUFFLE_CONSTANT) {
                    cell.markToBeBorn();
                    cell.update();
                }
            }
        }
    }

    public Neighborhood getNeighborsFromCell(Cell cell) {

        Point point = this.getPositionOfCell(cell);

        Cell[] neighbors = new Cell[Location.values().length];
        Location[] values = Location.values();
        for (Location location : values) {

            int xPosNeighbor = calcNormalisedPosition(point.x, this.getWidth(), location.getxOffset());
            int yPosNeighbor = calcNormalisedPosition(point.y, this.getHeight(), location.getyOffset());

            neighbors[location.getLocationInArray()] = this.cells[xPosNeighbor][yPosNeighbor];

        }

        return new Neighborhood(neighbors);
    }

    private Point getPositionOfCell(Cell targetCell) {
        for (int i = 0; i < this.cells.length; i++) {
            Cell[] row = this.cells[i];
            for (int j = 0; j < row.length; j++) {
                Cell cell = row[j];
                if (targetCell.equals(cell)) {
                    return new Point(i, j);
                }
            }
        }
        throw new RuntimeException("Could not find cell");
    }

    private int calcNormalisedPosition(int pos, int size, int offset) {
        return (((pos + offset) % size) + size) % size;
    }

    public int getHeight() {
        return this.cells.length;
    }

    public int getWidth() {
        return this.cells[0].length;
    }
}