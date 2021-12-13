package model;

import model.Neighborhood;

import java.util.*;

public class GameOfLife {

    private final Board board;

    private final Set<Cell> needsToBeChecked;
    private final Set<Cell> needsToBeUpdated;
    private final Set<Cell> needsToBeRevived;

    private int checkedAmount;
    private int updatedAmount;

    private boolean clearAll;
    private boolean shuffled;

    public GameOfLife(Board board) {
        this.board = board;

        this.needsToBeChecked = Collections.synchronizedSet(new HashSet<>());
        this.needsToBeChecked.addAll(board.getCellsAsCollection());
        this.needsToBeUpdated = Collections.synchronizedSet(new HashSet<>());
        this.needsToBeUpdated.addAll(board.getCellsAsCollection());

        this.needsToBeRevived = Collections.synchronizedSet(new HashSet<>());

    }

    public synchronized void makeRound() {

        if(this.clearAll){
            this.needsToBeChecked.clear();
            this.needsToBeUpdated.clear();
            this.needsToBeRevived.clear();
            this.board.clear();
            this.clearAll = false;
            return;
        }

        if(this.shuffled){
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
        this.updatedAmount = this.needsToBeUpdated.size();
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
        this.checkedAmount = this.needsToBeChecked.size();
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

    public int getSize() {
        return this.board.getWidth();
    }

    public Board getBoard() {
        return board;
    }

    public synchronized void clear() {
        this.clearAll = true;
        this.makeRound();
    }

    public int getCheckedAmount() {
        return checkedAmount;
    }

    public int getUpdatedAmount() {
        return updatedAmount;
    }
}
