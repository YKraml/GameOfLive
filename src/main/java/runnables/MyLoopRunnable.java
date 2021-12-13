package runnables;

public abstract class MyLoopRunnable implements Runnable {

    protected abstract long getWaitTimeInNano();

    protected abstract void toToInLoop();

    @Override
    public void run() {

        while (true) {

            long startTime = System.nanoTime();

            this.toToInLoop();

            long endTime = System.nanoTime();

            long timeDelta = endTime - startTime;
            long waitTimeNano = this.getWaitTimeInNano() - timeDelta;
            waitTimeNano = Math.max(0, waitTimeNano);

            long waitTimeMilli = waitTimeNano / 1000000;
            long addedWaitTimeNano = waitTimeNano % 1000000;

            try {
                Thread.sleep(waitTimeMilli, (int) addedWaitTimeNano);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
