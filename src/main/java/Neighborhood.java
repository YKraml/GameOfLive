public class Neighborhood {

    private final Cell[] neighbors;

    public Neighborhood(Cell[] neighbors) {
        this.neighbors = neighbors;
    }


    public int getAliveNeighborsCount() {
        int aliveNeighborsCount = 0;
        for (Cell cell : this.neighbors) {

            if (cell.isALive()) {
                aliveNeighborsCount++;
            }

        }
        return aliveNeighborsCount;
    }
}
