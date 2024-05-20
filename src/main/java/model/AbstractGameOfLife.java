package model;

import main.IntWrapper;

import java.util.Collection;

public abstract class AbstractGameOfLife {

    protected boolean wrapped = true;
    protected int size = 87;

    public abstract IntWrapper getUpdatedAmount();

    public abstract IntWrapper getCheckedAmount();

    public abstract void shuffle();

    public abstract void clear();

    public abstract void setCellAlive(int i, int i1);

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void makeRound();

    public abstract Collection<MyPoint> getAlivePoints();

    public boolean isWrapped() {
        return wrapped;
    }

    public void setWrapped(boolean wrapped) {
        this.wrapped = wrapped;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
