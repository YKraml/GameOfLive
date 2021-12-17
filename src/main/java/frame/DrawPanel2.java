package frame;

import model.AbstractGameOfLife;

import java.awt.*;

public class DrawPanel2 extends MyPanel{

    private final AbstractGameOfLife gameOfLive;
    private final int width;
    private final int height;

    public DrawPanel2(AbstractGameOfLife gameOfLive, int width, int height) {
        this.gameOfLive = gameOfLive;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);




    }

    @Override
    protected void init() {

    }
}
