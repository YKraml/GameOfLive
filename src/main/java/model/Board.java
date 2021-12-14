package model;

import model.Location;
import model.Neighborhood;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

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

    public Neighborhood getNeighborsFromCell(Cell cell) {

        Point point = this.getPositionOfCell(cell);

        Cell[] neighbors = new Cell[Location.values().length];
        Location[] values = Location.values();
        for (Location location : values) {

            int xPosNeighbor = calcNormalisedPosition(point.x, this.getWidth(), location.getxOffset());
            int yPosNeighbor = calcNormalisedPosition(point.y, this.getHeight(), location.getyOffset());

            neighbors[location.getLocationInArray()] = this.cells[xPosNeighbor][yPosNeighbor];

        }

        return new Neighborhood(cell, neighbors);
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

    public Collection<? extends Cell> getCellsAsCollection() {

        Collection<Cell> cellCollection = new HashSet<>();
        for (Cell[] row : this.cells) {
            cellCollection.addAll(Arrays.asList(row));
        }
        return cellCollection;
    }

    public void clear() {
        for (Cell[] row : this.cells) {
            for (Cell cell : row) {
                cell.markToBeKilled();
                cell.update();
            }
        }
    }

    public void setCellAlive(int cellXPos, int cellYPos) {
        this.cells[cellXPos][cellYPos].markToBeBorn().update();
    }

    public Cell getCellAt(int cellXPos, int cellYPos) {
        return this.cells[cellXPos][cellYPos];
    }
}
