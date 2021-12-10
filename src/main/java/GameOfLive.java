import java.util.*;

public class GameOfLive {

    private final Board board;

    private final Set<Cell> needsToBeChecked;
    private final Set<Cell> needsToBeUpdated;

    public GameOfLive(Board board) {
        this.board = board;
        needsToBeChecked = new HashSet<>();
        needsToBeChecked.addAll(board.getCellsAsCollection());
        needsToBeUpdated = new HashSet<>();
        needsToBeUpdated.addAll(board.getCellsAsCollection());
    }

    public void makeRound() {
        check();
        this.needsToBeChecked.clear();
        killAndRevive();
        this.needsToBeUpdated.clear();
    }

    private void killAndRevive() {

        this.needsToBeUpdated.parallelStream().forEach(cell ->{
            boolean changed = cell.update();
            if(changed){
                this.needsToBeChecked.add(cell);
                this.needsToBeChecked.addAll(board.getNeighborsFromCell(cell).getNeighbors());
            }
        });

    }

    private void check() {

        this.needsToBeChecked.parallelStream().forEach(cell -> {
            Neighborhood neighborhood = board.getNeighborsFromCell(cell);
            int aliveNeighborCount = neighborhood.getAliveNeighborsCount();
            if (aliveNeighborCount == 3) {
                cell.markToBeBorn();
                this.needsToBeUpdated.add(cell);
            } else if (aliveNeighborCount < 2) {
                cell.markToBeKilled();
                this.needsToBeUpdated.add(cell);
            } else if (aliveNeighborCount > 3) {
                cell.markToBeKilled();
                this.needsToBeUpdated.add(cell);
            }
        });

    }

    public void addBoard() {
        this.needsToBeChecked.addAll(this.board.getCellsAsCollection());
    }
}
