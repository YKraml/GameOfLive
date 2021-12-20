package model;

public class Board {

    private static final double SHUFFLE_CONSTANT = 0.8;
    private Cell[][] cells;

    public Board(int size) {
        this.cells = new Cell[size][size];
        initCells();
    }

    public Board(Cell[][] cells) {
        this.cells = cells;
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

    public Board shuffle() {
        for (Cell[] row : this.cells) {
            for (Cell cell : row) {
                if (Math.random() >= SHUFFLE_CONSTANT) {
                    cell.markToBeBorn();
                } else {
                    cell.markToBeKilled();
                }
                cell.update();
            }
        }
        return this;
    }

    public int getHeight() {
        return this.cells[0].length;
    }

    public int getWidth() {
        return this.cells.length;
    }

    public void setCellAlive(int cellXPos, int cellYPos) {
        try {
            this.cells[cellXPos][cellYPos].markToBeBorn().update();
        } catch (IndexOutOfBoundsException e) {

        }
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
