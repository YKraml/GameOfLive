package model;

import main.IntWrapper;

import java.util.*;

public class GameOfLive extends AbstractGameOfLife {

    private final IntWrapper checkedAmount;
    private final IntWrapper updatedAmount;

    private final Set<MyPoint> alivePoints;
    private final Set<MyPoint> pointsToBeKilled;
    private final Set<MyPoint> pointsToBeBorn;

    public GameOfLive() {
        this.pointsToBeKilled = Collections.synchronizedSet(new HashSet<>());
        this.pointsToBeBorn = Collections.synchronizedSet(new HashSet<>());
        this.alivePoints = Collections.synchronizedSet(new HashSet<>());

        checkedAmount = new IntWrapper();
        updatedAmount = new IntWrapper();
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
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            MyPoint point = new MyPoint(x, y);
            this.alivePoints.add(point);
        }
    }

    @Override
    public void clear() {
        this.alivePoints.clear();
        this.pointsToBeBorn.clear();
        this.pointsToBeKilled.clear();
    }

    @Override
    public void setCellAlive(int xPos, int yPos) {
        MyPoint myPoint = new MyPoint(xPos, yPos);
        this.alivePoints.add(myPoint);
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

        this.pointsToBeKilled.clear();
        this.pointsToBeBorn.clear();
    }

    private void updateCells() {
        IntWrapper amount = new IntWrapper();

        this.pointsToBeKilled.forEach(key -> {
            alivePoints.remove(key);
            amount.addOne();
        });
        this.pointsToBeBorn.forEach(myPoint -> {
            alivePoints.add(myPoint);
            amount.addOne();
        });

        this.updatedAmount.setNumber(amount.getNumber());

    }

    private int getAliveNeighborCountFrom(MyPoint myPoint) {
        int aliveNeighborsCount = 0;
        for (Location location : Location.values()) {
            double x = wrapCoordinate(myPoint.x() + location.getxOffset());
            double y = wrapCoordinate(myPoint.y() + location.getyOffset());
            MyPoint point = new MyPoint(x, y);
            if (this.alivePoints.contains(point) && !point.equals(myPoint)) {
                aliveNeighborsCount++;
            }
        }
        return aliveNeighborsCount;
    }

    private void checkCells() {
        IntWrapper amount = new IntWrapper();
        alivePoints.forEach(myPoint -> {
            if (wrapped && (myPoint.x() < 0 || myPoint.y() < 0 || myPoint.x() >= size || myPoint.y() >= size)) {
                this.pointsToBeKilled.add(myPoint);
            }

            for (Location location : Location.values()) {
                amount.addOne();

                double neighborXLoc = wrapCoordinate(myPoint.x() + location.getxOffset());
                double neighborYLoc = wrapCoordinate(myPoint.y() + location.getyOffset());
                MyPoint neighborPoint = new MyPoint(neighborXLoc, neighborYLoc);

                int aliveNeighborCount = getAliveNeighborCountFrom(neighborPoint);
                if (aliveNeighborCount == 3) {
                    this.pointsToBeBorn.add(neighborPoint);
                } else if (aliveNeighborCount < 2 || aliveNeighborCount > 3) {
                    this.pointsToBeKilled.add(neighborPoint);
                }
            }
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
    public Collection<MyPoint> getAlivePoints() {
        return Collections.unmodifiableCollection(alivePoints);
    }
}
