package model;

import main.IntWrapper;

import java.awt.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GameOfLife2 extends AbstractGameOfLife {

    private static final int STANDARD_SIZE = 100;
    private final Map<MyPoint, Cell> aliveCells;

    private final IntWrapper checkedAmount;
    private final IntWrapper updatedAmount;

    private final Set<MyPoint> pointsToBeRemoved;
    private final Set<MyPoint> pointsToBeAdded;

    public GameOfLife2() {

        this.pointsToBeRemoved = Collections.synchronizedSet(new HashSet<>());
        this.pointsToBeAdded = Collections.synchronizedSet(new HashSet<>());
        this.aliveCells = Collections.synchronizedMap(new HashMap<>());


        checkedAmount = new IntWrapper();
        updatedAmount = new IntWrapper();

        this.shuffle();
    }

    @Override
    public IntWrapper getUpdatedAmount() {
        return this.updatedAmount;
    }

    @Override
    public IntWrapper getCheckedAmount() {
        return this.checkedAmount;
    }

    @Override
    public void shuffle() {
        this.clear();
        for (int i = 0; i < 2000; i++) {
            Cell cell = new Cell();
            cell.markToBeBorn().update();
            int x = (int) (Math.random() * 100);
            int y = (int) (Math.random() * 100);
            MyPoint point = new MyPoint(x, y);
            this.aliveCells.put(point, cell);
        }
    }

    @Override
    public void clear() {
        this.aliveCells.clear();
        this.pointsToBeAdded.clear();
        this.pointsToBeRemoved.clear();
    }

    @Override
    public void setCellAlive(int xPos, int yPos) {
        Cell cell = new Cell();
        cell.markToBeBorn().update();
        MyPoint myPoint = new MyPoint(xPos, yPos);
        this.aliveCells.put(myPoint, cell);
    }

    @Override
    public int getWidth() {
        return STANDARD_SIZE;
    }

    @Override
    public int getHeight() {
        return STANDARD_SIZE;
    }

    @Override
    public void makeRound() {
        this.checkCells();
        this.updateCells();

        this.pointsToBeRemoved.clear();
        this.pointsToBeAdded.clear();
    }

    private void updateCells() {

        IntWrapper amount = new IntWrapper();

        this.pointsToBeRemoved.forEach(key -> {
            aliveCells.remove(key);
            amount.addNumber(1);
        });
        this.pointsToBeAdded.forEach(myPoint -> {
            Cell cell = new Cell();
            cell.markToBeBorn().update();
            aliveCells.put(myPoint, cell);
            amount.addNumber(1);
        });

        this.updatedAmount.setNumber(amount.getNumber());

    }

    private int getAliveNeighborCountFrom(MyPoint myPoint) {

        int aliveNeighborsCount = 0;
        for (Location location : Location.values()) {
            MyPoint point = new MyPoint(myPoint.getX() + location.getxOffset(), myPoint.getY() + location.getyOffset());
            if (this.aliveCells.containsKey(point) && !point.equals(myPoint)) {
                aliveNeighborsCount++;
            }

        }
        return aliveNeighborsCount;
    }

    private void checkCells() {

        IntWrapper amount = new IntWrapper();

        this.aliveCells.forEach((myPoint, cell) -> {

            for (Location location : Location.values()) {

                MyPoint neighborPoint = new MyPoint(myPoint.getX() + location.getxOffset(), myPoint.getY() + location.getyOffset());

                amount.addNumber(1);

                int aliveNeighborCount = getAliveNeighborCountFrom(neighborPoint);
                if (aliveNeighborCount == 3) {
                    this.pointsToBeAdded.add(neighborPoint);
                } else if (aliveNeighborCount < 2) {
                    this.pointsToBeRemoved.add(neighborPoint);
                } else if (aliveNeighborCount > 3) {
                    this.pointsToBeRemoved.add(neighborPoint);
                }

            }
        });

        this.checkedAmount.setNumber(amount.getNumber());

    }

    @Override
    public Board getBoard() {
        Board board = new Board(STANDARD_SIZE);
        this.aliveCells.forEach((myPoint, cell) -> board.setCellAlive(myPoint.getX(), myPoint.getY()));
        return board;
    }

    @Override
    public Collection<MyPoint> getAlivePoints() {
        return this.aliveCells.keySet();
    }


}
