package model;

import java.util.Arrays;
import java.util.Collection;

public class Neighborhood {

    private final Cell middleCell;
    private final Cell[] neighbors;

    public Neighborhood(Cell middleCell, Cell[] neighbors) {
        this.middleCell = middleCell;
        this.neighbors = neighbors;
    }


    public int getAliveNeighborsCount() {
        int aliveNeighborsCount = 0;
        for (Cell cell : this.neighbors) {

            if (cell.isALive() && !cell.equals(this.middleCell)) {
                aliveNeighborsCount++;
            }

        }
        return aliveNeighborsCount;
    }

    public Collection<Cell> getNeighbors(){
        return Arrays.asList(neighbors);
    }
}
