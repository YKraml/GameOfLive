package frame;

import main.Main;
import model.Formation;
import model.Board;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class EastPanel extends MyPanel {

    private String searchedFormation;
    private JScrollPane scrollPane;

    public EastPanel() {
        this.searchedFormation = "";
    }

    @Override
    protected void init() {
        this.setLayout(new BorderLayout());
        this.setBorder(new TitledBorder("Strukturen"));

        createScrollPane();

        JTextField searchField = new JTextField();
        this.add(searchField, BorderLayout.NORTH);
        searchField.requestFocus();
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                searchedFormation = searchField.getText();

                remove(scrollPane);
                createScrollPane();

                revalidate();
                repaint();
            }
        });
    }


    private void createScrollPane() {
        JPanel structuresPane = new JPanel();
        structuresPane.setLayout(new BoxLayout(structuresPane, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(structuresPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);

        this.add(scrollPane, BorderLayout.CENTER);

        Dimension dimension = new Dimension();
        dimension.setSize(250, 100);
        scrollPane.setPreferredSize(dimension);

        Formation.getValues().stream().filter(formation -> formation.getName().contains(searchedFormation.toUpperCase())).forEach(formation -> structuresPane.add(createFormationPanel(formation)));

        Formation.getValues().stream().filter(formation -> !formation.getName().contains(searchedFormation.toUpperCase())).forEach(formation -> structuresPane.add(createFormationPanel(formation)));
    }

    private JPanel createFormationPanel(Formation formation) {
        Board board = formation.getBoardFromFormation();
        JPanel formationPanel = getLittleDrawPanel(formation, board);
        formationPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Main.setCurrentlyChosenPattern(board);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Main.setCurrentlyChosenPattern(board);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Main.setCurrentlyChosenPattern(board);
            }
        });

        return formationPanel;
    }

    private JPanel getLittleDrawPanel(Formation formation, Board board) {
        LittleDrawPanel littleDrawPanel = new LittleDrawPanel(board, 200);

        JButton infoButton = new JButton("Info");
        infoButton.addActionListener(e -> JOptionPane.showMessageDialog(this, formation.getDescription()));

        JPanel formationPanel = new JPanel();
        formationPanel.setLayout(new BoxLayout(formationPanel, BoxLayout.Y_AXIS));
        formationPanel.setBorder(new TitledBorder(formation.getName()));
        formationPanel.add(littleDrawPanel);
        formationPanel.add(infoButton);
        return formationPanel;
    }


}
