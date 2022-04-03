package conway.application;

import conway.gui.GameOfLifeFrame;
import conway.helper.Cell;
import conway.helper.CoordinateConverter;

import java.util.*;

/**
 *  TODO: GameOfLifeFrame.java
 *          GUI work
 *          learn Swing lol
 *  TODO: GameOfLifeApplication
 *          input handling, no hardcoded stuff
 *          large inputs
 */
public class GameOfLifeApplication {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initializing Mini Universe");

        Set<Cell> currentAliveCells = new HashSet<>();
        currentAliveCells.add(CoordinateConverter.coordinateToIndeces(1, 0, GameOfLife.CENTER));
        currentAliveCells.add(CoordinateConverter.coordinateToIndeces(0, 1, GameOfLife.CENTER));
        currentAliveCells.add(CoordinateConverter.coordinateToIndeces(0, -1, GameOfLife.CENTER));
        currentAliveCells.add(CoordinateConverter.coordinateToIndeces(1, -1, GameOfLife.CENTER));
        currentAliveCells.add(CoordinateConverter.coordinateToIndeces(-1, -1, GameOfLife.CENTER));
//        currentAliveCells.add(new Cell(0, GameOfLife.DIMENSION - 1));
//        currentAliveCells.add(new Cell(0, GameOfLife.DIMENSION - 2));
//        currentAliveCells.add(new Cell(0, GameOfLife.DIMENSION - 3));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 1, 0));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 1, 1));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 1, 2));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 2, 0));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 2, 1));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 2, 2));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 3, 0));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 3, 1));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 3, 2));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 2, GameOfLife.DIMENSION - 1));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 3, GameOfLife.DIMENSION - 1));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 1, GameOfLife.DIMENSION - 1));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 1, GameOfLife.DIMENSION - 2));
//        currentAliveCells.add(new Cell(GameOfLife.DIMENSION - 1, GameOfLife.DIMENSION - 3));
//        currentAliveCells.add(new Cell(0, 0));
//        currentAliveCells.add(new Cell(0, 1));
//        currentAliveCells.add(new Cell(0, 2));
//        currentAliveCells.add(new Cell(1, 0));
//        currentAliveCells.add(new Cell(1, 1));
//        currentAliveCells.add(new Cell(1, 2));
//        currentAliveCells.add(new Cell(2, 0));
//        currentAliveCells.add(new Cell(2, 1));
//        currentAliveCells.add(new Cell(2, 2));

        GameOfLife gameOfLife = new GameOfLife(currentAliveCells);
        System.out.printf("Found %d alive cells%n", currentAliveCells.size());
        gameOfLife.printCenterBoard();
        gameOfLife.simulate(20, 1000);

        GameOfLifeFrame frame = new GameOfLifeFrame(gameOfLife);
    }
}
