package conway.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Majority of front end Swing code was copied from:
 *   https://rosettacode.org/wiki/Conway%27s_Game_of_Life/Java/Swing
 *
 * I added some stylistic changes as well as used my own backend for which to determine which cells are alive
 */
public class GameOfLifeFrame extends JFrame implements ActionListener {

    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(1200, 800);
    private static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(800, 600);
    public static int MOVES_PER_SECOND = 3;

    private JMenuBar menu;
    private JMenu fileMenu, gameMenu;
    private JMenuItem fileOptions, fileExit;
    private JMenuItem playGame, stopGame, resetGame, clearGame;
    private GameOfLifeBoard gameBoard;
    private Thread game;

    public void showGui() {
        JFrame game = new GameOfLifeFrame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("Mini Universe");
        game.setSize(DEFAULT_WINDOW_SIZE);
        game.setMinimumSize(MINIMUM_WINDOW_SIZE);
        game.setLocationRelativeTo(null); // centers the window
        game.getContentPane().setBackground(new Color(48, 48, 48));
        game.setVisible(true);
    }

    public GameOfLifeFrame() {
        // Setup menu
        menu = new JMenuBar();
        setJMenuBar(menu);
        fileMenu = new JMenu("File");
        menu.add(fileMenu);
        gameMenu = new JMenu("Game");
        menu.add(gameMenu);
        fileOptions = new JMenuItem("Options");
        fileOptions.addActionListener(this);
        fileExit = new JMenuItem("Exit");
        fileExit.addActionListener(this);
        fileMenu.add(fileOptions);
        fileMenu.add(new JSeparator());
        fileMenu.add(fileExit);
        playGame = new JMenuItem("Play");
        playGame.addActionListener(this);
        stopGame = new JMenuItem("Stop");
        stopGame.setEnabled(false);
        stopGame.addActionListener(this);
        resetGame = new JMenuItem("Reset");
        resetGame.addActionListener(this);
        clearGame = new JMenuItem("Clear");
        clearGame.addActionListener(this);
        gameMenu.add(playGame);
        gameMenu.add(stopGame);
        gameMenu.add(resetGame);
        gameMenu.add(clearGame);
        // Setup game board
        gameBoard = new GameOfLifeBoard();
        add(gameBoard);
    }

    public void setRunning(boolean running) {
        if (running) {
            playGame.setEnabled(false);
            stopGame.setEnabled(true);
            game = new Thread(gameBoard);
            game.start();
        } else {
            playGame.setEnabled(true);
            stopGame.setEnabled(false);
            game.interrupt();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(fileExit)) {
            // Exit the game
            System.exit(0);
        } else if (ae.getSource().equals(fileOptions)) {
            // Put up an options panel to change the number of moves per second
            final JFrame frameOptions = new JFrame();
            frameOptions.setTitle("Options");
            frameOptions.setSize(250,150);
            frameOptions.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frameOptions.getWidth())/2,
                    (Toolkit.getDefaultToolkit().getScreenSize().height - frameOptions.getHeight())/2);
            frameOptions.setResizable(false);
            JPanel panelOptions = new JPanel();
            panelOptions.setOpaque(false);
            frameOptions.add(panelOptions);
            panelOptions.add(new JLabel("Number of moves per second:"));
            Integer[] secondOptions = {1,2,3,4,5,10,15,20};
            final JComboBox comboBox = new JComboBox(secondOptions);
            panelOptions.add(comboBox);
            comboBox.setSelectedItem(MOVES_PER_SECOND);
            comboBox.addActionListener(ae1 -> {
                MOVES_PER_SECOND = (Integer)comboBox.getSelectedItem();
                frameOptions.dispose();
            });
            frameOptions.setVisible(true);
        } else if (ae.getSource().equals(resetGame)) {
            setRunning(false);
            gameBoard.resetBoard();
            gameBoard.repaint();
        } else if (ae.getSource().equals(clearGame)) {
            setRunning(false);
            gameBoard.clearBoard();
            gameBoard.repaint();
        } else if (ae.getSource().equals(playGame)) {
            setRunning(true);
        } else if (ae.getSource().equals(stopGame)) {
            setRunning(false);
        }
    }

}
