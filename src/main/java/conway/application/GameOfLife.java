package conway.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private int width;
    private int height;
    private int middleColumn;
    private int middleRow;
    private int maxColumnIndex;
    private int maxRowIndex;

    private int[][] gameBoard;

    public GameOfLife(int width, int height) {
        setWidth(width);
        setHeight(height);
        this.gameBoard = new int[width][height ];
    }

    public void setWidth(int width) {
        this.width = width;
        this.maxColumnIndex = width -1;
        this.middleColumn = width / 2;
    }

    public void setHeight(int height) {
        this.height = height;
        this.maxRowIndex = height - 1;
        this.middleRow = height / 2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setToAlive(int column, int row) {
        this.gameBoard[column][row] = 1;
    }

    public void setToAlive(int[] pair) {
        this.gameBoard[pair[0]][pair[1]] = 1;
    }

    public void setToDead(int column, int row) {
        this.gameBoard[column][row] = 0;
    }

    public void setToDead(int[] pair) {
        this.gameBoard[pair[0]][pair[1]] = 0;
    }

    public void simulate(int iterations, long sleepTime) throws InterruptedException {
        for (int i = 0; i < iterations; i++) {
            System.out.println(String.format("Iteration: %2d", i));
            simulateNext();
            printGameBoard();
            Thread.sleep(sleepTime);
        }
    }

    public void printGameBoard() {
        StringBuilder sb;
        for (int row = 0; row < height; row++) {
            sb = new StringBuilder();
            sb.append("|");
            for (int column = 0; column < width; column++) {
                int[] coordinates = indecesToCoordinates(column, row);
                // sb.append(String.format(" %2d,%2d ", coordinates[0], coordinates[1])); // print a graph representation of the board with coordinates
                sb.append(String.format("%2d", gameBoard[column][row])); // print the values of the board
            }
            sb.append(" |");
            System.out.println(sb.toString());
        }
    }

    /**
     * Simulates the next iteration
     */
    private void simulateNext() {
        List<int[]> futureDeadCells = new ArrayList<>();
        List<int[]> futureAliveCells = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                int aliveNeighbors = getNumAliveNeighbors(column, row);
                if (gameBoard[column][row] == 1) {
                    // alive cell
                    if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                        futureDeadCells.add(new int[]{column, row});
                    }
                } else if (gameBoard[column][row] == 0) {
                    // dead cell
                    if (aliveNeighbors == 3) {
                        futureAliveCells.add(new int[]{column, row});
                    }
                }
            }
        }

        updateCells(futureDeadCells, futureAliveCells);
    }

    /**
     * Updates cells to dead or alive given two lists of cells to update
     * @param futureDeadCells
     * @param futureAliveCells
     */
    private void updateCells(List<int[]> futureDeadCells, List<int[]> futureAliveCells) {
        for (int[] cell : futureDeadCells) {
            setToDead(cell);
        }
        for (int[] cell: futureAliveCells) {
            setToAlive(cell);
        }
    }

    /**
     * Return a set of int[] arrays where the first value represents an int to add to a column and the second value represents an int to add to the row
     * this covers all 8 surrounding sides of a point by default
     *  and removes points to check from the set if:
     *      column coordinate is the left or right most
     *      row coordinate is the top or bottom most
     *
     * @param column
     * @param row
     * @return
     */
    private Set<int[]> getPointsToCheck(int column, int row) {
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
        } else if (column == maxColumnIndex) {
            // we are at the left most column, remove all points that add 1 to column
            pointsToCheck.removeIf( point -> point[0]==1 );
        }

        if (row == 0) {
            // we are at the top most row, remove all points that subtract 1 from row
            pointsToCheck.removeIf( point -> point[1]==-1 );
        } else if (row == maxRowIndex) {
            // we are at the bottom most row, remove all points that add 1 to row
            pointsToCheck.removeIf( point -> point[1]==1 );
        }

        return pointsToCheck;
    }

    /**
     * Get the number of alive neighbors based on the column and row
     * @param column
     * @param row
     * @return
     */
    private int getNumAliveNeighbors(int column, int row) {
        int count = 0;

        Set<int[]> pointsToCheck = getPointsToCheck(column, row);

        for (int[] points : pointsToCheck) {
            count += gameBoard[column + points[0]][row + points[1]];
        }

        return count;
    }

    /**
     * Converts given coordinates to the indeces representation in the 2D array
     * for example:
     *  in a 11x11 board MIDDLE = rounded down width / 2 and height / 2
     *    (0, 0) represents the the middle of the board so      (0, 0) is column 5 row 5
     *    (5, 5) represents the top right of the board so       (5, 5) is column 10, row 0
     *    (-5, 5) represents the top left of the board so       (-5, 5) is column 0, row 0
     *    (5, -5) represents the bottom right of the board so   (5, -5) is column 10, row 10
     *    (-5, -5) represents the bottom left of the board so   (-5, -5) is column 0, row 10
     *
     *      FIRST QUADRANT (top right)
     *                                                          (1, 1) is column 6, row 4       (middle + x, middle - y)
     *                                                          (2, 3) is column 7, row 2       (middle + x, middle - y)
     *      SECOND QUADRANT (top left)
     *                                                          (-3, 4) is column 1, row 1      (middle + x, middle - y)
     *                                                          (-1, 2) is column 4, row 3      (middle + x, middle - y)
     *      THIRD QUADRANT (bottom left)
     *                                                          (-2, -5) is column 3, row 10    (middle + x, middle - y)
     *                                                          (-3, -1) is column 2, row 6     (middle + x, middle - y)
     *                                                          (-2, -2) is column 3, row 7     (middle + x, middle - y)
     *      FOURTH QUADRANT (bottom right)
     *                                                          (4, -4) is column 9, row 9      (middle + x, middle - y)
     *                                                          (3, -2) is column 8, row 7      (middle + x, middle - y)
     * @param x
     * @param y
     * @return
     */
    public int[] coordinateToIndeces(int x, int y) {
        return new int[] {middleColumn + x, middleRow - y};
    }

    /**
     * Inverse of the above function
     *   x = column - middle
     *   y = row - middle
     * @param column
     * @param row
     * @return
     */
    public int[] indecesToCoordinates(int column, int row) {
        return new int[]{ column - middleColumn, middleRow - row};
    }

}
