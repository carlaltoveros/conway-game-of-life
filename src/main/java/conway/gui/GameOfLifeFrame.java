package conway.gui;

import conway.application.GameOfLife;

import javax.swing.*;
import java.awt.*;

/**
 * Unfinished, still learning Swing
 */
public class GameOfLifeFrame extends JFrame {

    private JFrame frame;
    private GameOfLife gameOfLife;

    public GameOfLifeFrame(GameOfLife gameOfLife) {

        this.gameOfLife = gameOfLife;
        frame = new JFrame("The Secret of the Universe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(new Color(48, 48, 48));
        frame.setLayout(new GridLayout(Integer.MAX_VALUE, Integer.MAX_VALUE));
        frame.setVisible(true);

    }






}
