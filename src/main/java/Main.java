import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.BorderLayout.SOUTH;

public class Main {

    private static final int SIZE = 200;
    private static final long TIME_BETWEEN_UPDATES = 70;

    public static void main(String[] args) {

        MyFrame myFrame = new MyFrame();
        myFrame.setLayout(new BorderLayout());

        Board board = new Board(SIZE);
        board.shuffle();

        DrawPanel drawPanel = new DrawPanel(board);
        myFrame.add(drawPanel, BorderLayout.CENTER);

        drawPanel.paintImmediately(0, 0, drawPanel.getWidth(), drawPanel.getHeight());

        GameOfLive gameOfLive = new GameOfLive(board);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                gameOfLive.makeRound();
                myFrame.repaint();

            }
        }, 0, TIME_BETWEEN_UPDATES);


        JButton button = new JButton("Shuffle");
        myFrame.add(button, SOUTH);
        button.addActionListener(e -> board.shuffle());


        myFrame.setVisible();

    }

}
