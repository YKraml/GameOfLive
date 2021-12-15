package frame;

import main.Main;
import main.Formation;
import model.Board;
import model.Cell;
import model.GameOfLife;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EastPanel extends MyPanel {

    private final GameOfLife gameOfLife;

    public EastPanel(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }

    @Override
    protected void init() {

        this.setLayout(new BorderLayout());
        this.setBorder(new TitledBorder("Strukturen"));

        JPanel structuresPane = new JPanel();
        structuresPane.setLayout(new BoxLayout(structuresPane, BoxLayout.Y_AXIS));


        JScrollPane scrollPane = new JScrollPane(structuresPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);
        this.add(scrollPane, BorderLayout.CENTER);
        Dimension dimension = new Dimension();
        dimension.setSize(250, 600);
        scrollPane.setPreferredSize(dimension);

        for (Formation patter : Formation.getValues()) {
            extracted(patter, structuresPane);
        }


    }

    private void extracted(Formation formation, JPanel structuresPane) {
        String[] rows = formation.getFormation().replace("\t", "").split("\n");

        Cell[][] cells = new Cell[rows.length][rows[0].length()];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                char character = rows[i].toCharArray()[j];
                Cell cell = new Cell();
                cells[i][j] = cell;
                if (character == '*') {
                    cell.markToBeBorn().update();
                }
            }
        }

        Board board = new Board(cells);
        DrawPanel drawPanel = new DrawPanel(board, 200, false);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder(formation.getName()));
        panel.add(drawPanel);
        structuresPane.add(panel);
        JButton infoButton = new JButton("Info");
        infoButton.addActionListener(e -> JOptionPane.showMessageDialog(this, formation.getDescription()));
        panel.add(infoButton);
        this.addInnerMyPanel(drawPanel);

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.setCurrentlyChosenPattern(board);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

}