package model;

import main.IntWrapper;

import java.util.Collection;

public abstract class AbstractGameOfLife {
    public abstract IntWrapper getUpdatedAmount();

    public abstract IntWrapper getCheckedAmount();

    public abstract void shuffle();

    public abstract void clear();

    public abstract void setCellAlive(int i, int i1);

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void makeRound();

    public abstract Board getBoard();

    public abstract Collection<MyPoint> getAlivePoints();
}
