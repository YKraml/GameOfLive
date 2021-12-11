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

    public Cell markToBeBorn() {
        this.willBeBorn = true;
        return this;
    }


    public boolean update() {

        boolean changed = this.isALive && this.willBeKilled || !this.isALive && this.willBeBorn;

        if (this.willBeKilled) {
            this.isALive = false;
        }

        if (this.willBeBorn) {
            this.isALive = true;
        }

        this.willBeBorn = false;
        this.willBeKilled = false;

        return changed;
    }
}
