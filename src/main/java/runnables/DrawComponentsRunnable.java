package runnables;

import main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;

public class DrawComponentsRunnable extends MyLoopRunnable {


    private final Collection<JComponent> componentsToDraw;
    private final long fps;

    public DrawComponentsRunnable(Collection<JComponent> componentsToDraw, long fps) {
        this.componentsToDraw = componentsToDraw;
        this.fps = fps;
    }

    @Override
    protected long getWaitTimeInNano() {
        return 1000000000L / this.fps;
    }

    @Override
    protected void toToInLoop() {
        Main.getCalculatedFps().addNumber(1);
        this.componentsToDraw.forEach(Component::repaint);
    }
}
