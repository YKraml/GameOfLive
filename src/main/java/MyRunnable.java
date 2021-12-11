public class MyRunnable implements Runnable {

    private final GameOfLive gameOfLive;

    public MyRunnable(GameOfLive gameOfLive) {
        this.gameOfLive = gameOfLive;
    }

    @Override
    public void run() {
        while (true) {

            long startTime = System.nanoTime();

            Main.setCalculatedRounds(Main.getCalculatedRounds() + 1);
            if (!Main.isStop()) {
                gameOfLive.makeRound();
            }

            long endTime = System.nanoTime();

            try {
                long timeDelta = endTime - startTime;
                long waitTimeNano = Main.getTimeBetweenUpdatesInNano() - timeDelta;
                waitTimeNano = Math.max(0, waitTimeNano);

                long waitTimeMilli = waitTimeNano / 1000000;
                long addedWaitTimeNano = waitTimeNano % 1000000;

                Thread.sleep(waitTimeMilli, (int) addedWaitTimeNano);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


