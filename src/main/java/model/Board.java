package model;

public class Board {

    private final Cell[][] cells;

    public Board(Cell[][] cells) {
        this.cells = cells;
    }

    public int getHeight() {
        return this.cells[0].length;
    }

    public int getWidth() {
        return this.cells.length;
    }

    public Cell getCellAt(int cellXPos, int cellYPos) {
        return this.cells[cellXPos][cellYPos];
    }

    public Board getRotatedBoard() {
        Cell[][] cells = new Cell[this.getHeight()][this.getWidth()];

        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                int x = (((-j - 1) % this.getHeight()) + this.getHeight()) % this.getHeight();
                int y = (((i) % this.getWidth()) + this.getWidth()) % this.getWidth();

                cells[x][y] = this.cells[i][j];
            }
        }

        return new Board(cells);
    }
}
