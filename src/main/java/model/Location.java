package model;

public enum Location {

    TOP_LEFT(-1, -1),
    TOP_MIDDLE(-1, 0),
    TOP_RIGHT(-1, 1),
    LEFT(0, -1),
    MIDDLE(0, 0),
    RIGHT(0, 1),
    BOTTOM_LEFT(1, -1),
    BOTTOM_MIDDLE(1, 0),
    BOTTOM_RIGHT(1, 1);

    private final int yOffset;
    private final int xOffset;

    Location(int yOffset, int xOffset) {
        this.yOffset = yOffset;
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public int getxOffset() {
        return xOffset;
    }
}
