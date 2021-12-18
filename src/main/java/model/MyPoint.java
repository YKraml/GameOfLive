package model;

import java.util.Objects;

public class MyPoint {

    private final double x;
    private final double y;

    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPoint myPoint = (MyPoint) o;
        return x == myPoint.x && y == myPoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
