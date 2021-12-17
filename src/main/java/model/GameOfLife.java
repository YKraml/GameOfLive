package model;

import main.IntWrapper;

import java.util.*;

public class GameOfLife extends AbstractGameOfLife {

    private Board board;

    private final Set<Cell> needsToBeChecked;
    private final Set<Cell> needsToBeUpdated;
    private final Set<Cell> needsToBeRevived;

    private final IntWrapper checkedAmount;
    private final IntWrapper updatedAmount;

    private boolean clearAll;
    private boolean shuffled;

    public GameOfLife(Board board) {
        this.board = board;

        this.needsToBeChecked = Collections.synchronizedSet(new HashSet<>());
        this.needsToBeChecked.addAll(board.getCellsAsCollection());
        this.needsToBeUpdated = Collections.synchronizedSet(new HashSet<>());
        this.needsToBeUpdated.addAll(board.getCellsAsCollection());

        this.needsToBeRevived = Collections.synchronizedSet(new HashSet<>());

        checkedAmount = new IntWrapper();
        updatedAmount = new IntWrapper();

    }

    public synchronized void makeRound() {

        if (this.clearAll) {
            this.needsToBeChecked.clear();
            this.needsToBeUpdated.clear();
            this.needsToBeRevived.clear();
            this.board.clear();
            this.clearAll = false;
            return;
        }

        if (this.shuffled) {
            this.board.shuffle();
            this.needsToBeUpdated.addAll(this.getBoard().getCellsAsCollection());
            this.needsToBeChecked.addAll(this.board.getCellsAsCollection());
            this.shuffled = false;
            return;
        }

        reviveMarkedCells();
        check();
        killAndRevive();

    }

    private void reviveMarkedCells() {
        this.needsToBeRevived.forEach(this::reviveMarkedCell);
        this.needsToBeRevived.clear();
    }

    private void reviveMarkedCell(Cell cell) {
        this.needsToBeUpdated.add(cell);
        this.needsToBeChecked.add(cell);
        this.needsToBeUpdated.addAll(board.getNeighborsFromCell(cell).getNeighbors());
        this.needsToBeChecked.addAll(board.getNeighborsFromCell(cell).getNeighbors());
    }

    private void killAndRevive() {
        this.needsToBeUpdated.parallelStream().forEach(this::killAndReviveCell);
        this.updatedAmount.setNumber(this.needsToBeUpdated.size());
        this.needsToBeUpdated.clear();
    }

    private void killAndReviveCell(Cell cell) {
        boolean changed = cell.update();
        if (changed) {
            this.needsToBeChecked.add(cell);
            this.needsToBeChecked.addAll(board.getNeighborsFromCell(cell).getNeighbors());
        }
    }

    private void check() {
        this.needsToBeChecked.parallelStream().forEach(this::checkCell);
        this.checkedAmount.setNumber(this.needsToBeChecked.size());
        this.needsToBeChecked.clear();
    }

    private void checkCell(Cell cell) {
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
    }

    public synchronized void shuffle() {
        this.shuffled = true;
        this.makeRound();
    }

    public void setCellAlive(int cellXPos, int cellYPos) {
        Cell cell = this.board.getCellAt(cellXPos, cellYPos);
        cell.markToBeBorn().update();
        this.needsToBeRevived.add(cell);
    }

    public int getWidth() {
        return this.board.getWidth();
    }

    public int getHeight() {
        return this.board.getHeight();
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public Collection<MyPoint> getAlivePoints() {
        Collection<MyPoint> collection = new HashSet<>();

        for (int i = 0; i < this.board.getWidth(); i++) {
            for (int j = 0; j < this.board.getHeight(); j++) {
                if (board.getCellAt(i, j).isALive()) {
                    collection.add(new MyPoint(i, j));
                }
            }
        }

        return collection;
    }

    public synchronized void clear() {
        this.clearAll = true;
        this.makeRound();
    }

    public IntWrapper getCheckedAmount() {
        return checkedAmount;
    }

    public IntWrapper getUpdatedAmount() {
        return updatedAmount;
    }

}
