import java.util.*;

public class GameOfLive {

    private final Board board;

    private final Set<Cell> needsToBeChecked;
    private final Set<Cell> needsToBeUpdated;

    public GameOfLive(Board board) {
        this.board = board;
        needsToBeChecked = Collections.synchronizedSet(new HashSet<>());
        needsToBeChecked.addAll(board.getCellsAsCollection());
        needsToBeUpdated = Collections.synchronizedSet(new HashSet<>());
        needsToBeUpdated.addAll(board.getCellsAsCollection());
    }

    public void makeRound() {
        check();
        this.needsToBeChecked.clear();
        killAndRevive();
        this.needsToBeUpdated.clear();
    }

    private void killAndRevive() {

        this.needsToBeUpdated.forEach(cell ->{
            boolean changed = cell.update();
            if(changed){
                this.needsToBeChecked.add(cell);
                this.needsToBeChecked.addAll(board.getNeighborsFromCell(cell).getNeighbors());
            }
        });

    }

    private void check() {

        this.needsToBeChecked.forEach(cell -> {
            Neighborhood neighborhood = board.getNeighborsFromCell(cell);
            int aliveNeighborCount = neighborhood.getAliveNeighborsCount();
            if (aliveNeighborCount == 3) {
                cell.markToBeBorn();
                this.needsToBeUpdated.add(cell);
                this.needsToBeUpdated.addAll(board.getNeighborsFromCell(cell).getNeighbors());
            } else if (aliveNeighborCount < 2) {
                cell.markToBeKilled();
                this.needsToBeUpdated.add(cell);
                this.needsToBeUpdated.addAll(board.getNeighborsFromCell(cell).getNeighbors());
            } else if (aliveNeighborCount > 3) {
                cell.markToBeKilled();
                this.needsToBeUpdated.add(cell);
                this.needsToBeUpdated.addAll(board.getNeighborsFromCell(cell).getNeighbors());
            }
        });

    }

    public void shuffle() {
        this.board.shuffle();
        this.needsToBeChecked.addAll(this.board.getCellsAsCollection());
    }

    public void setCellAlive(int cellXPos, int cellYPos) {
        Cell cell = this.board.getCellAt(cellXPos, cellYPos);
        cell.markToBeBorn().update();

        this.needsToBeUpdated.add(this.board.getCellAt(cellXPos, cellYPos));
        this.needsToBeUpdated.addAll(board.getNeighborsFromCell(cell).getNeighbors());
        this.needsToBeChecked.add(this.board.getCellAt(cellXPos, cellYPos));
        this.needsToBeChecked.addAll(board.getNeighborsFromCell(cell).getNeighbors());
    }

    public int getSize() {
        return this.board.getWidth();
    }

    public Board getBoard() {
        return board;
    }

    public void clear(){
        this.needsToBeChecked.clear();
        this.needsToBeUpdated.clear();
        this.board.clear();
    }
}
