package main;

public class IntWrapper {

    private int number;

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void addNumber(int numberToBeAdded) {
        this.number += numberToBeAdded;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
