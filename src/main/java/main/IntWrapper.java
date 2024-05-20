package main;

public class IntWrapper {

    private int number;

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void addOne() {
        this.number += 1;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
