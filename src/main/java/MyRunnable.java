public class MyRunnable implements Runnable {

    private final GameOfLive gameOfLive;

    public MyRunnable(GameOfLive gameOfLive) {
        this.gameOfLive = gameOfLive;
    }

    @Override
    public void run() {
        while (true) {

            long startTime = System.currentTimeMillis();

            Main.setCalculatedRounds(Main.getCalculatedRounds() + 1);
            if (!Main.isStop()) {
                gameOfLive.makeRound();
            }

            long endTime = System.currentTimeMillis();

            try {
                Thread.sleep(Math.max(0, Main.getTimeBetweenUpdates() - (endTime - startTime)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


