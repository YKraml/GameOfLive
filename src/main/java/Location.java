public enum Location {

    TOP_LEFT(0, -1, -1),
    TOP_MIDDLE(1, -1, 0),
    TOP_RIGHT(2, -1, 1),
    LEFT(3, 0, -1),
    MIDDLE(4, 0, 0),
    RIGHT(5, 0, 1),
    BOTTOM_LEFT(6, 1, -1),
    BOTTOM_MIDDLE(7, 1, 0),
    BOTTOM_RIGHT(8, 1, 1);

    private final int locationInArray;
    private final int yOffset;
    private final int xOffset;

    Location(int locationInArray, int yOffset, int xOffset) {
        this.locationInArray = locationInArray;
        this.yOffset = yOffset;
        this.xOffset = xOffset;
    }

    public int getLocationInArray() {
        return locationInArray;
    }

    public int getyOffset() {
        return yOffset;
    }

    public int getxOffset() {
        return xOffset;
    }
}
