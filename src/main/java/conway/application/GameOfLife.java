package conway.application;

import conway.helper.Cell;

import java.util.HashSet;
import java.util.Set;

/**
 * Game of Life Coding Sample Take-home Question
 * So, here's the challenge:
 *
 * Implement Conway's Game of Life in 64-bit integer-space using the language of your choice.
 *
 * Imagine a 2D grid - each cell (coordinate) can be either "alive" or "dead". Every generation of the simulation, the system ticks forward. Each cell's value changes according to the following:
 * If an "alive" cell had less than 2 or more than 3 alive neighbors (in any of the 8 surrounding cells), it becomes dead.
 * If a "dead" cell had *exactly* 3 alive neighbors, it becomes alive.
 * Your input is a list of alive (x, y) integer coordinates. They could be anywhere in the signed 64-bit range. This means the board could be very large!
 *
 * Sample input:
 * (0, 1)
 * (1, 2)
 * (2, 0)
 * (2, 1)
 * (2, 2)
 * (-2000000000000, -2000000000000)
 * (-2000000000001, -2000000000001)
 * -----------------------------------------
 */

public class GameOfLife {

    // 2D grid || 0 is dead, 1 is alive
    // TODO: uncomment this max value and delete this current value
//    public static final int DIMENSION = Integer.MAX_VALUE;
    public static final int DIMENSION = 1001;
    public static final int CENTER = DIMENSION / 2;

    private Set<Cell> currentAliveCells;

    public GameOfLife(Set<Cell> currentAliveCells) {
        this.currentAliveCells = currentAliveCells;
    }

    public void simulate(int iterations, long sleepTime) throws InterruptedException {
        for (int i = 0; i < iterations; i++) {
            try {
                System.out.printf("Iteration: %2d%n", i + 1);
                simulateNext();
                System.out.printf("Found %d alive cells%n", currentAliveCells.size());
                //printBoard();
                Thread.sleep(sleepTime);
            } catch(OutOfMemoryError e) {
                System.out.printf("Found %d alive cells%n", currentAliveCells.size());
                throw e;
            }
        }
    }

    public void printBoard() {
        StringBuilder sb;
        for (int row = 0; row < DIMENSION; row++) {
            sb = new StringBuilder();
            sb.append("|");
            for (int column = 0; column < DIMENSION; column++) {
                Cell cell = new Cell(column, row);
                int alive =  currentAliveCells.contains(cell) ? 1 : 0;
                sb.append(String.format("%2d", alive)); // print the values of the board
            }
            sb.append(" |");
            System.out.println(sb.toString());
        }
    }
    /**
     * Simulates the next iteration
     */
    private void simulateNext() {
        Set<Cell> newAliveCells = new HashSet<>();
        Set<Cell> newDeadCells = new HashSet<>();
        for (int row = 0; row < DIMENSION; row++) {
            for (int column = 0; column < DIMENSION; column++) {
                Cell cell = new Cell(column, row);
                Set<int[]> pointsToCheck = getPointsToCheck(cell);
                int aliveNeighbors = getNumAliveNeighbors(cell, pointsToCheck);
                if (currentAliveCells.contains(cell)) {
                    // alive cell
                    if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                        newDeadCells.add(cell);
                    }
                } else {
                    // dead cell
                    if (aliveNeighbors == 3) {
                        newAliveCells.add(cell);
                    }
                }
            }
        }

        currentAliveCells.removeAll(newDeadCells);
        currentAliveCells.addAll(newAliveCells);
    }

    /**
     * Get the number of alive neighbors based on the given cells and points to check
     * points to check is a calculated Set of points to check around the given cells
     *      most of the time this is the 8 points around that cell, but in some cases like edges/corners,
     *      we only check a subset to avoid index oob errors
     * @param cell
     * @param pointsToCheck
     * @return
     */
    private int getNumAliveNeighbors(Cell cell, Set<int[]> pointsToCheck) {
        int count = 0;

        int column = cell.getColumn();
        int row = cell.getRow();
        for (int[] points : pointsToCheck) {
            Cell checkCell = new Cell(column + points[0], row + points[1]);
            if (currentAliveCells.contains(checkCell)) {
                count+=1;
            }
        }

        return count;
    }

    /**
     * Return a set of int[] arrays where the first value represents an int to add to a column and the second value represents an int to add to the row
     * this covers all 8 surrounding sides of a point by default
     *  and removes points to check from the set if:
     *      column cell is the left or right most
     *      row cell is the top or bottom most
     *
     * @param cell
     * @return
     */
    private Set<int[]> getPointsToCheck(Cell cell) {
        int column = cell.getColumn();
        int row = cell.getRow();

        Set<int[]> pointsToCheck = new HashSet<>();
        pointsToCheck.add(new int[] {0, 1}); // one up
        pointsToCheck.add(new int[] {0, -1}); // one down
        pointsToCheck.add(new int[] {-1, 0}); // one left
        pointsToCheck.add(new int[] {1, 0}); // one right

        pointsToCheck.add(new int[] {1, 1}); // up + right
        pointsToCheck.add(new int[] {-1, 1}); // up + left
        pointsToCheck.add(new int[] {1, -1}); // down + right
        pointsToCheck.add(new int[] {-1, -1}); // down + left

        if (column == 0) {
            // we are at the left most column, remove all the points that subtract 1 from column
            pointsToCheck.removeIf( point -> point[0]==-1 );
        } else if (column == DIMENSION) {
            // we are at the left most column, remove all points that add 1 to column
            pointsToCheck.removeIf( point -> point[0]==1 );
        }

        if (row == 0) {
            // we are at the top most row, remove all points that subtract 1 from row
            pointsToCheck.removeIf( point -> point[1]==-1 );
        } else if (row == DIMENSION) {
            // we are at the bottom most row, remove all points that add 1 to row
            pointsToCheck.removeIf( point -> point[1]==1 );
        }

        return pointsToCheck;
    }

}
