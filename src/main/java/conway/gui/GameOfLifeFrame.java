package conway.gui;

import conway.logic.GameOfLife;

import javax.swing.*;
import java.awt.*;

/**
 * Unfinished, still learning Swing
 */
public class GameOfLifeFrame extends JPanel {

    private JFrame frame;
    private GameOfLife gameOfLife;
    private static final int DIMENSION = 101;
    private static final Color background = new Color(48, 48, 48);
    private JButton[][] gridCells = new JButton[DIMENSION][DIMENSION];

    public GameOfLifeFrame(GameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;

        setBackground(background);
        setLayout(new GridLayout(DIMENSION, DIMENSION));
        setBorder(BorderFactory.createEmptyBorder());
        JPanel[][] panels = new JPanel[DIMENSION][DIMENSION];

        for (int column = 0; column < panels.length; column++) {
            for (int row = 0; row < panels[column].length; row++) {
                panels[column][row] = new JPanel(new GridLayout(DIMENSION, DIMENSION));
                add(panels[column][row]);
            }
        }

        for (int column = 0; column < gridCells.length; column++) {

        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(new Color(48, 48, 48));
        frame.setLayout(new GridLayout(DIMENSION, DIMENSION));
        frame.setVisible(true);


    }








}
