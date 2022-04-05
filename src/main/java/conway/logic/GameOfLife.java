package conway.logic;

import conway.helper.Cell;
import conway.helper.CoordinateToCellConverter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public static final int DIMENSION = Integer.MAX_VALUE;
    public static final int CENTER = DIMENSION / 2;

    private Set<Cell> currentAliveCells;

    public GameOfLife(Set<Cell> currentAliveCells) {
        this.currentAliveCells = currentAliveCells;
    }

    /**
     * Given number of iterations, performs the next step of the simulation
     * Creates an output/ directory to add files to
     *
     * @param iterations
     * @throws IOException
     */
    public void simulate(int iterations) throws IOException {
        String dirName = "output";
        if (!createDirectory(dirName)) {
            System.out.printf("Failed to create directory: %s. Please try again. Exiting...%n", dirName);
        }
        for (int i = 0; i < iterations; i++) {
            System.out.printf("Iteration: %2d%n", i + 1);
            simulateNext();
            System.out.printf("Found %d alive cells%n", currentAliveCells.size());
            printCenterBoard();
            writeToFile(dirName, i + 1);
        }
    }

    /**
     * Prints just the center of the board where 0 is a dead cell and 1 is an alive cell
     *   opted to print a 21x21 board, mostly used for manual testing
     */
    public void printCenterBoard() {
        System.out.println("Printing center 21x21 board");
        StringBuilder sb;
        int numSquares = Math.min(CENTER, 10); // how far left and right from the center should we print --> this produces a 13 x 13 board
        for (int row = CENTER - numSquares; row < CENTER + numSquares + 1; row++) {
            sb = new StringBuilder();
            sb.append("|");
            for (int column = CENTER - numSquares; column < CENTER + numSquares + 1; column++) {
                Cell cell = new Cell(column, row);
                int alive = currentAliveCells.contains(cell) ? 1 : 0;
                sb.append(String.format("%2d", alive)); // print the values of the board
            }
            sb.append(" |");
            if (row == CENTER) {
                sb.append(" CENTER ROW");
            }
            System.out.println(sb);
        }
    }

    /**
     * Simulates the next iteration
     * Loops through current alive cells
     *  new dead cells are found by getting a count of alive neighbors for each cell
     *  new alive cells are found by getting a count of each cell around each alive cell
     *      (see comment for getCellToAliveNeighborMap for better explanation)
     */
    private void simulateNext() {
        Map<Cell, Integer> cellToAliveNeighborMap = new HashMap<>();
        Set<Cell> newDeadCells = new HashSet<>();
        for (Cell cell : currentAliveCells) {
            Set<Cell> pointsToCheck = getPointsToCheck(cell);
            if (pointsToCheck.size() == 3) {
                // pointsToCheck size will only ever equal 3 if the cell is at the corner of the board
                newDeadCells.add(cell);
            }
            int aliveNeighbors = getNumAliveNeighbors(cell, pointsToCheck);
            updateCellToAliveNeighborMap(cellToAliveNeighborMap, cell, pointsToCheck);
            if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                newDeadCells.add(cell);
            }
        }
        Set<Cell> newAliveCells = cellToAliveNeighborMap.entrySet().stream()
                .filter( entry -> entry.getValue() == 3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        currentAliveCells.removeAll(newDeadCells);
        currentAliveCells.addAll(newAliveCells);
    }

    /**
     * Get a map of Cell -> count of times this cell is a neighbor of an alive cell
     *    for example:
     *      X  X  X  X  X   This would check all the points around 1, 2, 3, and 4
     *      X  1  Z2 X  X   and each time it hits a cell, we increment the count
     *      Z1 2  3  X  X   Z1 is a neighbor of (1, 2, and 4) so it would have a count of 3
     *      4  Z3 X  X  X   We are interested in cells with 3 neighbors. Z2 and Z3 also fit the criteria
     *
     * @param cell
     * @param pointsToCheck
     * @return
     */
    private Map<Cell, Integer> updateCellToAliveNeighborMap(Map<Cell, Integer> cellToAliveNeighborMap, Cell cell, Set<Cell> pointsToCheck) {

        for (Cell point : pointsToCheck) {
            Cell neighbor = new Cell(cell.getColumn() + point.getColumn(), cell.getRow() + point.getRow());
            if (!currentAliveCells.contains(neighbor)) {
                // we only want the counts for dead cells
                cellToAliveNeighborMap.merge(neighbor, 1, (current, increment) -> current + increment);
            }
        }

        return cellToAliveNeighborMap;
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
    private int getNumAliveNeighbors(Cell cell, Set<Cell> pointsToCheck) {
        int count = 0;

        int column = cell.getColumn();
        int row = cell.getRow();
        for (Cell points : pointsToCheck) {
            Cell checkCell = new Cell(column + points.getColumn(), row + points.getRow());
            if (currentAliveCells.contains(checkCell)) {
                count+=1;
            }
        }

        return count;
    }

    /**
     * Return a set of Cells where the column and rows are values to add to another cell that signifies its neighbors
     * this covers all 8 surrounding sides of a point by default
     *  and removes points to check from the set if:
     *      column cell is the left or right most
     *      row cell is the top or bottom most
     *
     * @param cell
     * @return
     */
    private Set<Cell> getPointsToCheck(Cell cell) {
        int column = cell.getColumn();
        int row = cell.getRow();

        Set<Cell> pointsToCheck = new HashSet<>();
        pointsToCheck.add(new Cell(0, 1)); // one up
        pointsToCheck.add(new Cell(0, -1)); // one down
        pointsToCheck.add(new Cell(-1, 0)); // one left
        pointsToCheck.add(new Cell(1, 0)); // one right

        pointsToCheck.add(new Cell(1, 1)); // up + right
        pointsToCheck.add(new Cell(-1, 1)); // up + left
        pointsToCheck.add(new Cell(1, -1)); // down + right
        pointsToCheck.add(new Cell(-1, -1)); // down + left

        if (column == 0) {
            // we are at the left most column, remove all the points that subtract 1 from column
            pointsToCheck.removeIf( point -> point.getColumn()==-1 );
        } else if (column == DIMENSION - 1) {
            // we are at the left most column, remove all points that add 1 to column
            pointsToCheck.removeIf( point -> point.getColumn()==1 );
        }

        if (row == 0) {
            // we are at the top most row, remove all points that subtract 1 from row
            pointsToCheck.removeIf( point -> point.getRow()==-1 );
        } else if (row == DIMENSION - 1) {
            // we are at the bottom most row, remove all points that add 1 to row
            pointsToCheck.removeIf( point -> point.getRow()==1 );
        }

        return pointsToCheck;
    }

    /**
     * Create a directory and override if it already exists (this is used to write output files to)
     * @param name
     * @return
     * @throws IOException
     */
    private boolean createDirectory(String name) throws IOException {
        File outputDir = new File(name);
        if (outputDir.exists()) {
            FileUtils.deleteDirectory(outputDir);
        }
        return outputDir.mkdir();
    }

    /**
     * Writes the output to a file
     *   Example:
     *     Iteration#97
     *     Found 2 alive cells
     *       X: 0, Y: 2
     *       X: -1000, Y: -1001010
     * @param dirName
     * @param iteration
     * @throws IOException
     */
    private void writeToFile(String dirName, int iteration) throws IOException {
        File newFile = new File(String.format("%s/iteration-%d.txt", dirName, iteration));
        List<Cell> sortedCells = new ArrayList<>(currentAliveCells);
        Collections.sort(sortedCells);
        if (newFile.createNewFile()) {
            FileWriter writer = new FileWriter(newFile.getAbsolutePath(), true);
            writer.append(String.format("Iteration #%d%n", iteration));
            writer.append(String.format("Found %d alive cells: printing coordinates%n", currentAliveCells.size()));
            for (Cell cell : sortedCells) {
                Cell converted = CoordinateToCellConverter.indecesToCoordinates(cell, CENTER);
                writer.append(String.format("  X: %d, Y: %d%n", converted.getColumn(), converted.getRow()));
            }
            writer.flush();
            writer.close();
        }
    }

}
