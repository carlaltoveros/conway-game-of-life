package conway.gui;

import conway.helper.Cell;
import conway.logic.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

public class GameOfLifeBoard extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, Runnable  {

    private static final int BLOCK_SIZE = 20;
    private Dimension boardSize = null;
    private Set<Cell> cells = new HashSet<>();
    private Set<Cell> startingCells = new HashSet<>();
    boolean first = true;

    public GameOfLifeBoard() {
        // Add resizing listener
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.setBackground(new Color(90, 94, 97));
    }

    private void updateArraySize() {
        Set<Cell> removeList = new HashSet<>();
        for (Cell cellToCheck : cells) {
            if ((cellToCheck.getColumn() > boardSize.width - 1) || (cellToCheck.getRow() > boardSize.height - 1)) {
                removeList.add(cellToCheck);
            }
        }
        cells.removeAll(removeList);
        repaint();
    }

    private void deleteCellsOutsideGrid() {
        Set<Cell> cellsToRemove = new HashSet<>();
        for (Cell cellToCheck : cells) {
            if (cellToCheck.getColumn() > boardSize.width - 1 || cellToCheck.getColumn() < 0
                || cellToCheck.getRow() > boardSize.height - 1 || cellToCheck.getRow() < 0) {
                cellsToRemove.add(cellToCheck);
            }
        }
        cells.removeAll(cellsToRemove);
    }

    public void toggleCell(int x, int y) {
        Cell cell = new Cell(x, y);
        if (cells.contains(cell)) {
            cells.remove(cell);
        } else {
            cells.add(cell);
        }
        repaint();
    }

    public void toggleCell(MouseEvent me) {
        int x = me.getPoint().x / BLOCK_SIZE-1;
        int y = me.getPoint().y / BLOCK_SIZE-1;
        if ((x >= 0) && (x < boardSize.width) && (y >= 0) && (y < boardSize.height)) {
            toggleCell(x,y);
        }
    }

    public void addCell(int x, int y) {
        cells.add(new Cell(x, y));
        repaint();
    }

    public void addCell(MouseEvent me) {
        int x = me.getPoint().x / BLOCK_SIZE-1;
        int y = me.getPoint().y / BLOCK_SIZE-1;
        if ((x >= 0) && (x < boardSize.width) && (y >= 0) && (y < boardSize.height)) {
            addCell(x,y);
        }
    }

    public void removeCell(int x, int y) {
        cells.remove(new Cell(x,y));
    }

    public void clearBoard() {
        cells.clear();
    }

    public void resetBoard() {
        cells = startingCells;
        first = true;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            for (Cell newCell : cells) {
                // Draw new cell
                g.setColor(Color.PINK);
                g.fillRect(BLOCK_SIZE + (BLOCK_SIZE*newCell.getColumn()), BLOCK_SIZE + (BLOCK_SIZE*newCell.getRow()), BLOCK_SIZE, BLOCK_SIZE);
            }
        } catch (ConcurrentModificationException cme) {}
        // Setup grid
        g.setColor(Color.BLACK);
        for (int i = 0; i<= boardSize.width; i++) {
            g.drawLine(((i*BLOCK_SIZE)+BLOCK_SIZE), BLOCK_SIZE, (i*BLOCK_SIZE)+BLOCK_SIZE, BLOCK_SIZE + (BLOCK_SIZE* boardSize.height));
        }
        for (int i = 0; i<= boardSize.height; i++) {
            g.drawLine(BLOCK_SIZE, ((i*BLOCK_SIZE)+BLOCK_SIZE), BLOCK_SIZE*(boardSize.width+1), ((i*BLOCK_SIZE)+BLOCK_SIZE));
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // Setup the game board size with proper boundries
        boardSize = new Dimension(getWidth()/BLOCK_SIZE-2, getHeight()/BLOCK_SIZE-2);
        updateArraySize();
    }
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {
        // Mouse was released (user clicked)
        toggleCell(e);
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        // Mouse is being dragged, user wants multiple selections
        addCell(e);
    }
    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void run() {
        if (first) {
            first = false;
            startingCells = new HashSet<>(cells);
        }
        GameOfLife gameOfLife = new GameOfLife(cells);
        cells = gameOfLife.simulateNext();
        deleteCellsOutsideGrid();
        repaint();

        try {
            Thread.sleep(1000 / GameOfLifeFrame.MOVES_PER_SECOND);
            run();
        } catch (InterruptedException ex) {
            // do nothing
        }
    }

}
