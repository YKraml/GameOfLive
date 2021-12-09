public class GameOfLive {

    private final Board board;

    public GameOfLive(Board board) {
        this.board = board;
    }

    public void makeRound() {
        check();
        killAndRevive();
    }

    private void killAndRevive() {
        Cell[][] cells = board.getCells();

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.update();
            }
        }
    }

    private void check() {

        Cell[][] cells = board.getCells();

        for (Cell[] row : cells) {

            for (Cell cell : row) {
                Neighborhood neighborhood = board.getNeighborsFromCell(cell);
                int aliveNeighborCount = neighborhood.getAliveNeighborsCount();

                if (aliveNeighborCount == 3) {
                    cell.markToBeBorn();
                } else if (aliveNeighborCount < 2) {
                    cell.markToBeKilled();
                } else if (aliveNeighborCount > 3) {
                    cell.markToBeKilled();
                }

            }

        }
    }

}
