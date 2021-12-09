public class Cell {

    private boolean isALive;
    private boolean willBeKilled;
    private boolean willBeBorn;

    public boolean isALive() {
        return isALive;
    }

    public void markToBeKilled() {
        this.willBeKilled = true;
    }

    public void markToBeBorn() {
        this.willBeBorn = true;
    }


    public void update() {

        if (this.willBeKilled) {
            this.isALive = false;
        }

        if (this.willBeBorn) {
            this.isALive = true;
        }

        this.willBeBorn = false;
        this.willBeKilled = false;

    }
}
