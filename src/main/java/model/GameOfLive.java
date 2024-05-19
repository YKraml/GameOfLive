package model;

import main.IntWrapper;

import java.util.*;

public class GameOfLive extends AbstractGameOfLife {


    private final Map<MyPoint, Cell> aliveCells;

    private final IntWrapper checkedAmount;
    private final IntWrapper updatedAmount;

    private final Set<MyPoint> pointsToBeRemoved;
    private final Set<MyPoint> pointsToBeAdded;

    public GameOfLive() {
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
        for (int i = 0; i < size * size / 2; i++) {
            Cell cell = new Cell();
            cell.markToBeBorn().update();
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
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
        return size;
    }

    @Override
    public int getHeight() {
        return size;
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
            double x = wrapCoordinate(myPoint.x() + location.getxOffset());
            double y = wrapCoordinate(myPoint.y() + location.getyOffset());
            MyPoint point = new MyPoint(x, y);
            if (this.aliveCells.containsKey(point) && !point.equals(myPoint)) {
                aliveNeighborsCount++;
            }
        }
        return aliveNeighborsCount;
    }

    private void checkCells() {
        IntWrapper amount = new IntWrapper();

        aliveCells.keySet().forEach(myPoint -> {
            if (wrapped && (myPoint.x() < 0 || myPoint.y() < 0 || myPoint.x() >= size || myPoint.y() >= size)) {
                this.pointsToBeRemoved.add(myPoint);
            }

            for (Location location : Location.values()) {
                double neighborXLoc = wrapCoordinate(myPoint.x() + location.getxOffset());
                double neighborYLoc = wrapCoordinate(myPoint.y() + location.getyOffset());
                MyPoint neighborPoint = new MyPoint(neighborXLoc, neighborYLoc);

                int aliveNeighborCount = getAliveNeighborCountFrom(neighborPoint);
                if (aliveNeighborCount == 3) {
                    this.pointsToBeAdded.add(neighborPoint);
                } else if (aliveNeighborCount < 2) {
                    this.pointsToBeRemoved.add(neighborPoint);
                } else if (aliveNeighborCount > 3) {
                    this.pointsToBeRemoved.add(neighborPoint);
                }
            }

            amount.addNumber(1);
        });

        this.checkedAmount.setNumber(amount.getNumber());
    }

    private double wrapCoordinate(double coordinate) {
        if (wrapped) {
            return ((coordinate % size) + size) % size;
        }
        return coordinate;
    }

    @Override
    public Board getBoard() {
        Board board = new Board(size);
        this.aliveCells.forEach((myPoint, cell) -> board.setCellAlive((int) myPoint.x(), (int) myPoint.y()));
        return board;
    }

    @Override
    public Collection<MyPoint> getAlivePoints() {
        return this.aliveCells.keySet();
    }
}
