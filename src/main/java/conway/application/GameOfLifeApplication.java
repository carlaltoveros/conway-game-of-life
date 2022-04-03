package conway.application;

import conway.helper.Cell;
import conway.helper.CoordinateConverter;

import java.util.*;

/** TODO: GameOfLife.java
 *          Test with higher board dimensions
 *          Optimize to allow a board with max 64-bit dimension
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
        Random random = new Random();
        while (currentAliveCells.size() < Math.pow(GameOfLife.DIMENSION, 2) / 2) {
            Cell cell = new Cell(random.nextInt(GameOfLife.DIMENSION), random.nextInt(GameOfLife.DIMENSION));
            currentAliveCells.add(cell);
        }

        GameOfLife gameOfLife = new GameOfLife(currentAliveCells);
        System.out.printf("Found %d alive cells%n", currentAliveCells.size());
        gameOfLife.simulate(5, 1000);
    }
}
