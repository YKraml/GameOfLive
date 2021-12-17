package frame;

import main.Main;
import model.AbstractGameOfLife;
import model.Formation;
import model.Board;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EastPanel extends MyPanel {

    private final AbstractGameOfLife gameOfLife;

    public EastPanel(AbstractGameOfLife gameOfLife) {
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
        dimension.setSize(250, 100);
        scrollPane.setPreferredSize(dimension);

        Formation.getValues().forEach(formation -> structuresPane.add(this.createFormationPanel(formation)));

    }

    private JPanel createFormationPanel(Formation formation) {

        Board board = formation.getBoardFromFormation();

        LittleDrawPanel littleDrawPanel = new LittleDrawPanel(board, 200);

        JButton infoButton = new JButton("Info");
        infoButton.addActionListener(e -> JOptionPane.showMessageDialog(this, formation.getDescription()));

        JPanel formationPanel = new JPanel();
        formationPanel.setLayout(new BoxLayout(formationPanel, BoxLayout.Y_AXIS));
        formationPanel.setBorder(new TitledBorder(formation.getName()));
        formationPanel.add(littleDrawPanel);
        formationPanel.add(infoButton);

        formationPanel.addMouseListener(new MouseListener() {
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

        return formationPanel;
    }


}
