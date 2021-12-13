package runnables;

import main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrawFrameRunnable extends MyLoopRunnable {


    private final List<JComponent> componentsToDraw;
    private final long fps;

    public DrawFrameRunnable(List<JComponent> componentsToDraw, long fps) {
        this.componentsToDraw = componentsToDraw;
        this.fps = fps;
    }

    @Override
    protected long getWaitTimeInNano() {
        return 1000000000L / this.fps;
    }

    @Override
    protected void toToInLoop() {
        Main.setCalculatedFps(Main.getCalculatedFps() + 1);
        this.componentsToDraw.forEach(Component::repaint);
    }
}
