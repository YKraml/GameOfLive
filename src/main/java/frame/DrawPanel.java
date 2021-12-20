package frame;

import main.Main;
import model.AbstractGameOfLife;
import model.Board;
import model.MyPoint;

import java.awt.*;
import java.util.Collection;
import java.util.Optional;

public class DrawPanel extends MyPanel {

    private static final Color BACKGROUND_COLOR = Color.gray;
    private static final Color ALIVE_CELL_COLOR = new Color(0, 153, 204);
    private static final Color DEAD_CELL_COLOR = new Color(224, 242, 255);
    private static final Color FOREIGN_CELL_COLOR = Color.ORANGE;
    private static final Color MOUSE_COLOR = Color.GREEN;
    private static final int MAX_LINES = 150;

    private final AbstractGameOfLife gameOfLive;
    private final int width;
    private final int height;

    private double mouseXPos;
    private double mouseYPos;

    private double worldXOffset;
    private double worldYOffset;

    private double zoomX = 8;
    private double zoomY = 8;

    public DrawPanel(int width, int height, AbstractGameOfLife gameOfLive) {
        this.gameOfLive = gameOfLive;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Collection<MyPoint> myPointCollection = gameOfLive.getAlivePoints();

        g.setColor(ALIVE_CELL_COLOR);
        myPointCollection.forEach(myPoint -> {
            drawCell(g, myPoint);
        });

        g.setColor(FOREIGN_CELL_COLOR);
        Optional<Board> foreignBoardOptional = Main.getCurrentlyChosenPattern();
        foreignBoardOptional.ifPresent(foreignBoard -> {

            for (int i = 0; i < foreignBoard.getWidth(); i++) {
                for (int j = 0; j < foreignBoard.getHeight(); j++) {

                    if (foreignBoard.getCellAt(i, j).isALive()) {

                        int xCord = (int) (i + this.mouseXPos - foreignBoard.getWidth() / 2);
                        int yCord = (int) (j + this.mouseYPos - foreignBoard.getHeight() / 2);
                        drawCell(g, new MyPoint(xCord, yCord));

                    }
                }
            }
        });

        g.setColor(BACKGROUND_COLOR);
        int verticalLineCount = (int) (this.getHeight() / zoomY) + 2;
        for (int i = 0; i < verticalLineCount; i++) {
            if (verticalLineCount < MAX_LINES) {
                int lineYPixel = (int) (zoomY * i + this.worldYOffset * zoomY % zoomY);
                g.drawLine(0, lineYPixel, this.getWidth(), lineYPixel);
            }
        }
        int horizontalLineCount = (int) (this.getWidth() / zoomX) + 2;
        for (int i = 0; i < horizontalLineCount; i++) {
            if (horizontalLineCount < MAX_LINES) {
                int lineXPixel = (int) (zoomX * i + this.worldXOffset * zoomX % zoomX);
                g.drawLine(lineXPixel, 0, lineXPixel, this.getHeight());
            }
        }

        if (gameOfLive.isWrapped()) {
            g.setColor(BACKGROUND_COLOR);
            MyPoint topLeft = calcWorldToScreen(new MyPoint(0, 0));
            MyPoint topRight = calcWorldToScreen(new MyPoint(gameOfLive.getWidth(), 0));
            MyPoint bottomLeft = calcWorldToScreen(new MyPoint(0, gameOfLive.getHeight()));

            g.fillRect(0, 0, getWidth(), (int) (topRight.getY()));
            g.fillRect(0, (int) bottomLeft.getY(), getWidth(), (int) (getHeight() - bottomLeft.getY()));
            g.fillRect(0, 0, (int) topLeft.getX(), getHeight());
            g.fillRect((int) topRight.getX(), 0, (int) (getWidth() - topRight.getX()), getHeight());

        }

        g.setColor(MOUSE_COLOR);
        MyPoint mousePos = new MyPoint((int) mouseXPos, (int) mouseYPos);
        Main.getMousePos().setLocation(mouseXPos, mouseYPos);
        drawCell(g, mousePos);


    }

    private void drawCell(Graphics g, MyPoint worldPoint) {
        MyPoint screenPoint = this.calcWorldToScreen(worldPoint);
        int xPixel = (int) screenPoint.getX();
        int yPixel = (int) screenPoint.getY();
        int width = (int) (zoomX + 1 - zoomX % 1);
        int height = (int) (zoomY + 1 - zoomY % 1);
        g.fillRect(xPixel, yPixel, width, height);
    }

    @Override
    protected void init() {
        Dimension dimension = new Dimension();
        dimension.setSize(this.width, this.height);
        this.setPreferredSize(dimension);
        this.setBackground(DEAD_CELL_COLOR);

    }

    private MyPoint calcWorldToScreen(MyPoint worldPoint) {

        double screenXPos = (this.getWorldXOffset() + worldPoint.getX()) * this.getZoomX();
        double screenYPos = (this.getWorldYOffset() + worldPoint.getY()) * this.getZoomY();

        return new MyPoint(screenXPos, screenYPos);
    }

    public double getZoomX() {
        return zoomX;
    }

    public void setZoomX(double zoomX) {
        this.zoomX = zoomX;
    }

    public double getZoomY() {
        return zoomY;
    }

    public void setZoomY(double zoomY) {
        this.zoomY = zoomY;
    }

    public double getWorldXOffset() {
        return worldXOffset;
    }

    public void setWorldXOffset(double worldXOffset) {
        this.worldXOffset = worldXOffset;
    }

    public double getWorldYOffset() {
        return worldYOffset;
    }

    public void setWorldYOffset(double worldYOffset) {
        this.worldYOffset = worldYOffset;
    }

    public double getMouseXPos() {
        return mouseXPos;
    }

    public void setMouseXPos(double mouseXPos) {
        this.mouseXPos = mouseXPos;
    }

    public double getMouseYPos() {
        return mouseYPos;
    }

    public void setMouseYPos(double mouseYPos) {
        this.mouseYPos = mouseYPos;
    }
}
